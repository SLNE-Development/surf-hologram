package dev.slne.surf.hologram.core.service

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

interface HologramService {
    fun createHologram(
        type: HologramType,
        metaData: HologramMetaData,
        centerLocation: HologramLocation,
        displayedText: Component,
        creationReason: HologramCreationReason,
        viewers: ObjectSet<HoloOfflinePlayer>? = null
    ): Hologram

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