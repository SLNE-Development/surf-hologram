package dev.slne.surf.hologram.paper.hologram

import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.plugin
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

/**
 * Periodically checks all registered holograms and removes any that have exceeded their TTL.
 */
object TtlScheduler {
    private lateinit var task: ScheduledTask

    fun start() {
        if (::task.isInitialized && !task.isCancelled) {
            return
        }

        task = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
            val now = System.currentTimeMillis()
            hologramRegistry.holograms()
                .filter { hologram ->
                    val ttl = hologram.metaData.ttl ?: return@filter false
                    val base = (hologram as? BaseHologram)?.createdAt ?: return@filter false
                    now >= base + ttl
                }
                .forEach { hologramService.deleteHologram(it) }
        }, 0L, 1L, TimeUnit.SECONDS)
    }

    fun stop() {
        if (::task.isInitialized && !task.isCancelled) {
            task.cancel()
        }
    }
}
