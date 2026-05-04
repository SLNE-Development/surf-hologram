package dev.slne.surf.hologram.paper.hologram

import com.github.retrooper.packetevents.PacketEvents
import dev.slne.surf.hologram.api.hologram.TooltipHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.hologramPlayerService
import dev.slne.surf.hologram.paper.plugin
import dev.slne.surf.surfapi.core.api.util.random
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class TooltipHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramOrientationType: HologramOrientationType,
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val hitbox: HologramHitbox?,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override val targetEntityId: Int?,
    override val targetLocation: HologramLocation?,
    override val lookAngleThreshold: Double,
    override val maxDistance: Double
) : BaseHologram(), TooltipHologram {

    /** Players currently seeing this tooltip (so we can hide it when they stop looking). */
    private val activeViewers: MutableSet<UUID> = ConcurrentHashMap.newKeySet()

    override fun duplicate(spawnable: Boolean) = TooltipHologramImpl(
        if (spawnable) metaData.duplicate().apply {
            name = "$name-DUPLICATE-${(1..1000).random()}"
            holoEntityId = random.nextInt()
            interactionEntityId = random.nextInt()
        } else metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        hitbox,
        viewers,
        targetEntityId,
        targetLocation,
        lookAngleThreshold,
        maxDistance
    )

    companion object {
        private lateinit var tickTask: ScheduledTask

        /** Tick interval: 100 ms = 2 Minecraft ticks. */
        private const val TICK_INTERVAL_MS = 100L

        /** Eye height offset added to player foot position. */
        private const val EYE_HEIGHT = 1.62

        /**
         * Cache of player look data, updated by [updatePlayerLook] from the packet listener.
         * Key: player UUID. Value: [PlayerLookData].
         */
        private val playerLookCache: ConcurrentHashMap<UUID, PlayerLookData> = ConcurrentHashMap()

        /** Raw look data captured from movement/rotation packets. */
        data class PlayerLookData(
            val footX: Double,
            val footY: Double,
            val footZ: Double,
            /** Yaw in degrees (Minecraft convention). */
            val yaw: Float,
            /** Pitch in degrees (Minecraft convention). */
            val pitch: Float,
            /** World name – used to avoid cross-world false positives. */
            val worldName: String
        ) {
            val eyeX: Double get() = footX
            val eyeY: Double get() = footY + EYE_HEIGHT
            val eyeZ: Double get() = footZ

            /** Normalised look direction vector. */
            val dirX: Double get() = -sin(Math.toRadians(yaw.toDouble())) * cos(Math.toRadians(pitch.toDouble()))
            val dirY: Double get() = -sin(Math.toRadians(pitch.toDouble()))
            val dirZ: Double get() = cos(Math.toRadians(yaw.toDouble())) * cos(Math.toRadians(pitch.toDouble()))
        }

        fun updatePlayerLook(uuid: UUID, data: PlayerLookData) {
            playerLookCache[uuid] = data
        }

        fun removePlayer(uuid: UUID) {
            playerLookCache.remove(uuid)
        }

        fun startTicking() {
            if (::tickTask.isInitialized && !tickTask.isCancelled) {
                return
            }

            tickTask = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                val tooltips = hologramRegistry.holograms().filterIsInstance<TooltipHologramImpl>()
                if (tooltips.isEmpty()) return@runAtFixedRate

                playerLookCache.forEach { (uuid, lookData) ->
                    val holoPlayer = hologramPlayerService.getPlayer(uuid) ?: return@forEach

                    tooltips.forEach tooltip@{ tooltip ->
                        // Respect the viewer restriction on this tooltip
                        val isAllowedViewer = tooltip.viewers == null ||
                                tooltip.viewers!!.any { it.uuid == uuid }
                        if (!isAllowedViewer) return@tooltip

                        // Determine which position to check against
                        val checkPos = tooltip.resolveCheckPosition() ?: return@tooltip

                        // Cross-world guard
                        if (lookData.worldName != checkPos.world.worldName) return@tooltip

                        val looking = isLookingAt(lookData, checkPos, tooltip)
                        val wasShowing = uuid in tooltip.activeViewers

                        if (looking && !wasShowing) {
                            tooltip.show(holoPlayer)
                            tooltip.activeViewers.add(uuid)
                        } else if (!looking && wasShowing) {
                            tooltip.hide(holoPlayer)
                            tooltip.activeViewers.remove(uuid)
                        }
                    }
                }

                // Hide from players that have left the cache (disconnected)
                tooltips.forEach { tooltip ->
                    tooltip.activeViewers.removeIf { uuid ->
                        if (uuid in playerLookCache) return@removeIf false
                        val holoPlayer = hologramPlayerService.getPlayer(uuid)
                        if (holoPlayer != null) tooltip.hide(holoPlayer)
                        true
                    }
                }
            }, 0L, TICK_INTERVAL_MS, TimeUnit.MILLISECONDS)
        }

        fun stopTicking() {
            if (::tickTask.isInitialized && !tickTask.isCancelled) {
                tickTask.cancel()
            }
        }

        private fun isLookingAt(
            look: PlayerLookData,
            target: HologramLocation,
            tooltip: TooltipHologramImpl
        ): Boolean {
            val dx = target.x - look.eyeX
            val dy = target.y - look.eyeY
            val dz = target.z - look.eyeZ
            val dist = sqrt(dx * dx + dy * dy + dz * dz)

            if (dist > tooltip.maxDistance) return false
            if (dist < 0.001) return true   // player is inside the target

            val dot = (dx * look.dirX + dy * look.dirY + dz * look.dirZ) / dist
            val angleDeg = Math.toDegrees(acos(dot.coerceIn(-1.0, 1.0)))
            return angleDeg <= tooltip.lookAngleThreshold
        }
    }

    /**
     * Returns the position to check against for the look-at detection:
     * - For block-based tooltips: [targetLocation]
     * - For entity-based tooltips: [centerLocation] (the caller is expected to keep this
     *   at the entity's position)
     */
    private fun resolveCheckPosition(): HologramLocation? =
        targetLocation ?: if (targetEntityId != null) centerLocation else null
}
