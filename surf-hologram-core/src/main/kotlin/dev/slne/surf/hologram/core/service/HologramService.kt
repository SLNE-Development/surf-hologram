package dev.slne.surf.hologram.core.service

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.surfapi.core.api.util.requiredService
import net.kyori.adventure.text.Component

interface HologramService {
    fun createHologram(
        name: String,
        type: HologramType,
        centerLocation: HologramLocation,
        displayedText: Component
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