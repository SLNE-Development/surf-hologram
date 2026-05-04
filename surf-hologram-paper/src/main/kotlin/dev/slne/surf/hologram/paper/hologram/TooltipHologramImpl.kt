package dev.slne.surf.hologram.paper.hologram

import dev.slne.surf.api.core.util.random
import dev.slne.surf.hologram.api.hologram.TooltipHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.hologramPlayerService
import dev.slne.surf.hologram.paper.plugin
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
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val hitbox: HologramHitbox?,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override val targetEntityId: Int?,
    override val targetLocation: HologramLocation?,
    override val lookAngleThreshold: Double,
    override val maxDistance: Double
) : BaseHologram(), TooltipHologram {
    override var hologramOrientationType = HologramOrientationType.ROTATING

    override fun duplicate(spawnable: Boolean) = TooltipHologramImpl(
        if (spawnable) metaData.duplicate().apply {
            name = "$name-DUPLICATE-${(1..1000).random()}"
            holoEntityId = random.nextInt()
            interactionEntityId = random.nextInt()
        } else metaData,
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
        private const val TICK_INTERVAL_MS = 100L
        private const val EYE_HEIGHT = 1.62
        private const val MIN_DISTANCE_THRESHOLD = 0.001
        private const val TOGGLE_COOLDOWN_MS = 200L

        internal val playerLookCache: ConcurrentHashMap<UUID, PlayerLookData> = ConcurrentHashMap()
        private val activeViewers: ConcurrentHashMap<String, MutableSet<UUID>> = ConcurrentHashMap()
        private val lastToggle: ConcurrentHashMap<String, Long> = ConcurrentHashMap()

        private fun TooltipHologramImpl.viewerKey(): String = metaData.name

        private fun toggleKey(uuid: UUID, tooltip: TooltipHologramImpl): String =
            "$uuid|${tooltip.viewerKey()}"

        data class PlayerLookData(
            val footX: Double,
            val footY: Double,
            val footZ: Double,
            val yaw: Float,
            val pitch: Float,
            val worldName: String
        ) {
            val eyeX: Double get() = footX
            val eyeY: Double get() = footY + EYE_HEIGHT
            val eyeZ: Double get() = footZ

            val dirX: Double get() = -sin(Math.toRadians(yaw.toDouble())) * cos(Math.toRadians(pitch.toDouble()))
            val dirY: Double get() = -sin(Math.toRadians(pitch.toDouble()))
            val dirZ: Double get() = cos(Math.toRadians(yaw.toDouble())) * cos(Math.toRadians(pitch.toDouble()))
        }

        fun updatePlayerLook(uuid: UUID, data: PlayerLookData) {
            playerLookCache[uuid] = data
        }

        fun removePlayer(uuid: UUID) {
            playerLookCache.remove(uuid)
            lastToggle.keys.removeIf { it.startsWith("$uuid|") }
        }

        fun startTicking() {
            if (::tickTask.isInitialized && !tickTask.isCancelled) return

            tickTask = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                val tooltips = hologramRegistry.holograms().filterIsInstance<TooltipHologramImpl>()
                if (tooltips.isEmpty()) return@runAtFixedRate

                playerLookCache.forEach { (uuid, lookData) ->
                    val holoPlayer = hologramPlayerService.getPlayer(uuid) ?: return@forEach

                    tooltips.forEach tooltip@{ tooltip ->
                        val isAllowedViewer = tooltip.viewers == null ||
                                tooltip.viewers.any { it.uuid == uuid }
                        if (!isAllowedViewer) return@tooltip

                        val checkPos = tooltip.resolveCheckPosition() ?: return@tooltip
                        if (lookData.worldName != checkPos.world.worldName) return@tooltip

                        val viewers = activeViewers.getOrPut(tooltip.viewerKey()) {
                            ConcurrentHashMap.newKeySet()
                        }

                        val wasShowing = uuid in viewers
                        val looking = isLookingAt(lookData, checkPos, tooltip, wasShowing)

                        val now = System.currentTimeMillis()
                        val tKey = toggleKey(uuid, tooltip)
                        val last = lastToggle[tKey] ?: 0L

                        if (now - last < TOGGLE_COOLDOWN_MS) return@tooltip

                        if (looking && !wasShowing) {
                            tooltip.show(holoPlayer)
                            viewers.add(uuid)
                            lastToggle[tKey] = now
                        } else if (!looking && wasShowing) {
                            tooltip.hide(holoPlayer)
                            viewers.remove(uuid)
                            lastToggle[tKey] = now
                        }
                    }
                }

                tooltips.forEach { tooltip ->
                    val viewers = activeViewers[tooltip.viewerKey()] ?: return@forEach
                    viewers.removeIf { uuid ->
                        if (playerLookCache.containsKey(uuid)) return@removeIf false
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
            tooltip: TooltipHologramImpl,
            wasShowing: Boolean
        ): Boolean {
            val dx = target.x - look.eyeX
            val dy = target.y - look.eyeY
            val dz = target.z - look.eyeZ
            val dist = sqrt(dx * dx + dy * dy + dz * dz)

            if (dist > tooltip.maxDistance) return false
            if (dist < MIN_DISTANCE_THRESHOLD) return true

            val dot = (dx * look.dirX + dy * look.dirY + dz * look.dirZ) / dist
            val angleDeg = Math.toDegrees(acos(dot.coerceIn(-1.0, 1.0)))

            val enter = tooltip.lookAngleThreshold
            val exit = enter + 3.0

            return if (wasShowing) angleDeg <= exit else angleDeg <= enter
        }
    }

    private fun resolveCheckPosition(): HologramLocation? =
        targetLocation ?: if (targetEntityId != null) centerLocation else null
}