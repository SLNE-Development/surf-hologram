package dev.slne.surf.hologram.api.hologram

import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

typealias HologramEventHandler<T> = (T) -> Unit

interface Hologram {
    val metaData: HologramMetaData
    var hologramType: HologramType
    var centerLocation: HologramLocation
    var displayedText: Component
    val creationReason: HologramCreationReason

    val viewers: ObjectSet<HoloOfflinePlayer>?

    fun retrieveViewers(): ObjectSet<HoloPlayer>

    fun show(player: HoloPlayer)
    fun hide(player: HoloPlayer)

    fun teleportHere(player: HoloPlayer): Boolean
    fun teleportTo(newLocation: HologramLocation)
}