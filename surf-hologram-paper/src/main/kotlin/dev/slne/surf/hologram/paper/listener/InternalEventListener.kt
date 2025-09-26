package dev.slne.surf.hologram.paper.listener

import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.core.registry.hologramRegistry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object InternalEventListener : Listener {
    @EventHandler
    fun onClick(event: HologramClickEvent) {
        hologramRegistry.holograms().forEach { it.callHandlers(event) }
    }
}