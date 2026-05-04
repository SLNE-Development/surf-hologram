package dev.slne.surf.hologram.core.service

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

interface HologramService {
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

    fun refresh(hologram: Hologram?)
    fun refreshClean(hologram: Hologram)
    fun refreshContent(hologram: Hologram?)

    fun deleteHologram(hologram: Hologram)
    fun deleteHologram(name: String) = hologramRegistry.getHologram(name)?.let {
        deleteHologram(it)
    }

    companion object {
        val INSTANCE = requiredService<HologramService>()
    }
}

val hologramService get() = HologramService.INSTANCE