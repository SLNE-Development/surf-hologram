package dev.slne.surf.hologram.api.hologram

import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import it.unimi.dsi.fastutil.objects.ObjectSet

typealias HologramEventHandler<T> = (T) -> Unit

interface Hologram {
    val metaData: HologramMetaData
    val hologramType: HologramType
    val centerLocation: HologramLocation

    val viewers: ObjectSet<HoloOfflinePlayer>

    fun show(player: HoloPlayer)
    fun hide(player: HoloPlayer)
    
    fun teleportHere(player: HoloPlayer)
    fun teleportTo(newLocation: HologramLocation)
}