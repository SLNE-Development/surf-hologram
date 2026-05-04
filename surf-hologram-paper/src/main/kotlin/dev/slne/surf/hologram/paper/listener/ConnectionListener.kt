package dev.slne.surf.hologram.paper.listener

import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.paper.hologram.TooltipHologramImpl
import dev.slne.surf.hologram.paper.util.holoPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

object ConnectionListener : Listener {
    @EventHandler
    fun onConnect(event: PlayerJoinEvent) {
        hologramRegistry.holograms().filter {
            it.viewers == null || it.viewers!!.any { a -> a.uuid == event.player.uniqueId }
        }.forEach {
            it.show(event.player.holoPlayer)
        }
    }

    @EventHandler
    fun onDisconnect(event: PlayerQuitEvent) {
        TooltipHologramImpl.removePlayer(event.player.uniqueId)
    }

    /**
     * Tracks every player's current eye position and look direction so the async tooltip
     * scheduler can determine whether the player is aiming at a registered target.
     *
     * We use [EventPriority.MONITOR] with `ignoreCancelled = true` so we only track
     * positions for moves that actually take effect.
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onMove(event: PlayerMoveEvent) {
        val loc = event.to ?: return
        val player = event.player
        TooltipHologramImpl.updatePlayerLook(
            player.uniqueId,
            TooltipHologramImpl.PlayerLookData(
                footX = loc.x,
                footY = loc.y,
                footZ = loc.z,
                yaw = loc.yaw,
                pitch = loc.pitch,
                worldName = loc.world?.name ?: return
            )
        )
    }
}