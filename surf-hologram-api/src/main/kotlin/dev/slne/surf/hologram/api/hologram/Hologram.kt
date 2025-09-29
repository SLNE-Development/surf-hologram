package dev.slne.surf.hologram.api.hologram

import dev.slne.surf.hologram.api.event.HologramEvent
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import kotlin.reflect.KClass

typealias HologramEventHandler<T> = (T) -> Unit

interface Hologram {
    val metaData: HologramMetaData
    var hologramOrientationType: HologramOrientationType
    var centerLocation: HologramLocation
    var displayedText: Component
    val hitbox: HologramHitbox?

    val viewers: ObjectSet<HoloOfflinePlayer>?
    fun retrieveViewers(): ObjectSet<HoloPlayer>

    fun show(player: HoloPlayer)
    fun hide(player: HoloPlayer)

    fun refresh()
    fun refreshClean()

    fun duplicate(spawnable: Boolean = false): Hologram

    fun teleportHere(player: HoloPlayer): Boolean
    fun teleportTo(newLocation: HologramLocation, save: Boolean = true)

    fun <T : HologramEvent> addEventHandler(eventClass: KClass<T>, handler: HologramEventHandler<T>)
    fun <T : HologramEvent> removeEventHandler(
        eventClass: KClass<T>,
        handler: HologramEventHandler<T>
    )

    fun <T : HologramEvent> callHandlers(event: T)
}