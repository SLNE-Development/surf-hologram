package dev.slne.surf.hologram.paper.hologram

import dev.slne.surf.hologram.api.hologram.UpdatingHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.paper.plugin
import dev.slne.surf.surfapi.core.api.util.random
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.jetbrains.annotations.Range
import java.util.concurrent.TimeUnit

class UpdatingHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramOrientationType: HologramOrientationType,
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val hitbox: HologramHitbox?,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override val interval: @Range(from = 50, to = Long.MAX_VALUE) Long,
) : UpdatingHologram, BaseHologram() {
    private var lastUpdateTime: Long = 0L
    override fun update() {
        refreshContent()
    }

    override fun duplicate(spawnable: Boolean) = UpdatingHologramImpl(
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
        interval
    )

    fun tryUpdate() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastUpdateTime >= interval) {
            lastUpdateTime = currentTime
            update()
        }
    }


    companion object {
        private lateinit var updateTask: ScheduledTask

        fun startUpdating() {
            if (::updateTask.isInitialized && !updateTask.isCancelled) {
                return
            }

            updateTask = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                hologramRegistry.holograms().forEach { (it as? UpdatingHologramImpl)?.tryUpdate() }
            }, 0L, 50, TimeUnit.MILLISECONDS)
        }

        fun stopUpdating() {
            if (::updateTask.isInitialized && !updateTask.isCancelled) {
                updateTask.cancel()
            }
        }
    }
}