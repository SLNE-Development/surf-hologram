package dev.slne.surf.hologram.api.util

import dev.slne.surf.hologram.api.event.HologramEvent
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramEventHandler
import dev.slne.surf.hologram.api.player.HoloPlayer
import org.bukkit.entity.Player

fun Hologram.forEachBukkitViewer(action: (Player) -> Unit) =
    retrieveViewers().mapNotNull { it.bukkitPlayer }.forEach { action(it) }

fun Hologram.forEachViewer(action: (HoloPlayer) -> Unit) =
    retrieveViewers().forEach { action(it) }

fun Hologram.show() = forEachViewer { it.showHologram(this) }
fun Hologram.hide() = forEachViewer { it.hideHologram(this) }

inline fun <reified T : HologramEvent> Hologram.addEventHandler(noinline handler: HologramEventHandler<T>) {
    this.addEventHandler(T::class, handler)
}