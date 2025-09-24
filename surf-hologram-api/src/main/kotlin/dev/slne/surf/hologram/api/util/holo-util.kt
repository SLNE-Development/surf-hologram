package dev.slne.surf.hologram.api.util

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.player.HoloPlayer
import org.bukkit.entity.Player

fun Hologram.forEachBukkitViewer(action: (Player) -> Unit) =
    retrieveViewers().mapNotNull { it.bukkitPlayer }.forEach { action(it) }

fun Hologram.forEachViewer(action: (HoloPlayer) -> Unit) =
    retrieveViewers().forEach { action(it) }