package dev.slne.surf.hologram.api

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramOptions
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import java.util.*

interface SurfHologramApi {
    fun <H : Hologram, O : HologramOptions> createHologram(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        hologramClazz: Class<H>,
        options: O
    ): H

    fun deleteHologram(hologram: Hologram)
    fun editHologramSaving(hologram: Hologram, block: Hologram.() -> Unit): Hologram
    fun refreshHologram(hologram: Hologram?)

    fun registerHologram(hologram: Hologram)
    fun getHologram(name: String): Hologram?

    fun getPlayer(uuid: UUID, name: String): HoloPlayer
    fun getPlayer(uuid: UUID): HoloPlayer?
    fun getPlayer(name: String): HoloPlayer?
    fun getOfflinePlayer(uuid: UUID): HoloOfflinePlayer?

    fun <H : Hologram, O : HologramOptions> registerHologramType(type: HologramType<H, O>)
    fun <H : Hologram, O : HologramOptions> getHologramType(hologramClazz: Class<H>): HologramType<H, O>?

    companion object {
        val INSTANCE = requiredService<SurfHologramApi>()
    }
}

val surfHologramApi get() = SurfHologramApi.INSTANCE