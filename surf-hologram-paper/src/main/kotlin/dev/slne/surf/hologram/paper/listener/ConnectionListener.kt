package dev.slne.surf.hologram.paper.listener

import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.paper.util.holoPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object ConnectionListener : Listener {
    @EventHandler
    fun onConnect(event: PlayerJoinEvent) {
        hologramRegistry.holograms().filter {
            it.viewers == null || it.viewers!!.any { a -> a.uuid == event.player.uniqueId }
        }.forEach {
            it.show(event.player.holoPlayer)
        }
    }
}