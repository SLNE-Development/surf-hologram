package dev.slne.surf.hologram.paper.hologram

import dev.slne.surf.hologram.api.hologram.FadingHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.plugin
import dev.slne.surf.surfapi.core.api.util.random
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

class FadingHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramOrientationType: HologramOrientationType,
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val hitbox: HologramHitbox?,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override val fadeSpeed: Double,
    override val fadeMaxOffset: Double
) : BaseHologram(), FadingHologram {
    /** Current Y-offset from the spawn position, increasing each tick. */
    private var currentOffset: Double = 0.0

    override fun tick() {
        currentOffset += fadeSpeed
        if (currentOffset >= fadeMaxOffset) {
            hologramService.deleteHologram(this)
            return
        }

        this.teleportTo(centerLocation.duplicate().apply {
            this.y += currentOffset
        }, false)
    }

    override fun duplicate(spawnable: Boolean) = FadingHologramImpl(
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
        fadeSpeed,
        fadeMaxOffset
    )

    companion object {
        private lateinit var tickTask: ScheduledTask

        /** Interval between animation ticks: 100 ms (≈2 Minecraft ticks at 20 TPS). */
        private const val TICK_INTERVAL_MS = 100L

        fun startTicking() {
            if (::tickTask.isInitialized && !tickTask.isCancelled) {
                return
            }

            tickTask = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                hologramRegistry.holograms().forEach { (it as? FadingHologram)?.tick() }
            }, 0L, TICK_INTERVAL_MS, TimeUnit.MILLISECONDS)
        }

        fun stopTicking() {
            if (::tickTask.isInitialized && !tickTask.isCancelled) {
                tickTask.cancel()
            }
        }
    }
}
