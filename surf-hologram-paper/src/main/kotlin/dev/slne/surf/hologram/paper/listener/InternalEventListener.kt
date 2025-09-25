package dev.slne.surf.hologram.paper.listener

import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.paper.util.debug
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object InternalEventListener : Listener {
    @EventHandler
    fun onClick(event: HologramClickEvent) {
        debug("HologramClickEvent: player=${event.player.uuid}, hologram=${event.hologram.metaData.name}")
    }
}