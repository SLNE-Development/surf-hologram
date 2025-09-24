package dev.slne.surf.hologram.api.player

import dev.slne.surf.hologram.api.hologram.Hologram
import org.bukkit.Bukkit

interface HoloPlayer : HoloOfflinePlayer {
    val name: String
    val bukkitPlayer get() = Bukkit.getPlayer(this.uuid)

    fun showHologram(hologram: Hologram)
    fun hideHologram(hologram: Hologram)
}