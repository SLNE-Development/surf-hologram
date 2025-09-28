package dev.slne.surf.hologram.core.service

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

interface HologramService {
    fun refresh(hologram: Hologram?)
    fun refreshClean(hologram: Hologram)

    fun createHologram(
        type: HologramOrientationType,
        hitbox: HologramHitbox?,
        metaData: HologramMetaData,
        centerLocation: HologramLocation,
        displayedText: Component,
        creationReason: HologramCreationReason,
        viewers: ObjectSet<HoloOfflinePlayer>? = null,
        bouncingHeight: Double? = null,
        bouncingStep: Double? = null
    ): Hologram

    fun createHologram(hologram: Hologram): Hologram

    fun deleteHologram(hologram: Hologram)
    fun deleteHologram(name: String) = hologramRegistry.getHologram(name)?.let {
        deleteHologram(it)
    }

    fun editHologramSaving(hologram: Hologram, block: Hologram.() -> Unit): Hologram

    companion object {
        val INSTANCE = requiredService<HologramService>()
    }
}

val hologramService get() = HologramService.INSTANCE