package dev.slne.surf.hologram.api.util

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.player.HoloPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun Hologram.forEachBukkitViewer(action: (Player) -> Unit) =
    this.viewers.mapNotNull { Bukkit.getPlayer(it.uuid) }.forEach { action(it) }

fun Hologram.forEachViewer(action: (HoloPlayer) -> Unit) =
    this.viewers.mapNotNull { it.player }.forEach { action(it) }