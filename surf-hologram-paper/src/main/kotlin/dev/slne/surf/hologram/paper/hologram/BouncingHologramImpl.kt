package dev.slne.surf.hologram.paper.hologram

import dev.slne.surf.api.core.util.random
import dev.slne.surf.hologram.api.hologram.BounceDirection
import dev.slne.surf.hologram.api.hologram.BounceSpeed
import dev.slne.surf.hologram.api.hologram.BouncingHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.paper.plugin
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

class BouncingHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramOrientationType: HologramOrientationType,
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val hitbox: HologramHitbox?,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override var bounceState: Double,
    override val bounceHeight: Double,
    override val bounceSpeed: BounceSpeed,
    override var bounceDirection: BounceDirection
) : BaseHologram(), BouncingHologram {
    override fun tick() {
        if (bounceDirection) {
            bounceState += bounceSpeed
            if (bounceState >= bounceHeight) {
                bounceState = bounceHeight
                bounceDirection = false
            }
        } else {
            bounceState -= bounceSpeed
            if (bounceState <= 0) {
                bounceState = 0.0
                bounceDirection = true
            }
        }

        this.teleportTo(centerLocation.duplicate().apply {
            this.y += bounceState
        }, false)
    }

    override fun duplicate(spawnable: Boolean) = BouncingHologramImpl(
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
        bounceState,
        bounceHeight,
        bounceSpeed,
        bounceDirection
    )

    companion object {
        private lateinit var tickTask: ScheduledTask

        /** Tick interval: 50 ms (one Minecraft tick at 20 TPS). */
        private const val TICK_INTERVAL_MS = 50L

        fun startTicking() {
            if (::tickTask.isInitialized && !tickTask.isCancelled) {
                return
            }

            tickTask = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                hologramRegistry.holograms().forEach { (it as? BouncingHologram)?.tick() }
            }, 0L, TICK_INTERVAL_MS, TimeUnit.MILLISECONDS)
        }

        fun stopTicking() {
            if (::tickTask.isInitialized && !tickTask.isCancelled) {
                tickTask.cancel()
            }
        }
    }
}
