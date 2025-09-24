package dev.slne.surf.hologram.core.service

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.surfapi.core.api.util.requiredService

interface HologramService {
    fun createHologram(hologram: Hologram): Hologram?
    fun deleteHologram(hologram: Hologram): Boolean
    fun deleteHologram(name: String) = hologramRegistry.getHologram(name)?.let {
        deleteHologram(it)
    }

    companion object {
        val INSTANCE = requiredService<HologramService>()
    }
}

val hologramService get() = HologramService.INSTANCE