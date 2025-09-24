package dev.slne.surf.hologram.api

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectList

interface SurfHologramApi {
    fun createHologram(hologram: Hologram): Hologram?
    fun deleteHologram(hologram: Hologram): Boolean
    fun deleteHologram(name: String) = getHologram(name)?.let {
        deleteHologram(it)
    }

    fun getHologram(name: String): Hologram?
    fun getHolograms(): ObjectList<Hologram>

    fun updateHolograms()

    companion object {
        val INSTANCE = requiredService<SurfHologramApi>()
    }
}

val surfHologramApi = SurfHologramApi.INSTANCE