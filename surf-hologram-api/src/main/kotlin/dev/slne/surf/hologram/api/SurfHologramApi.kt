package dev.slne.surf.hologram.api

import dev.slne.surf.surfapi.core.api.util.requiredService

interface SurfHologramApi {
    fun createHologram()
    fun deleteHologram()

    fun getHologram()
    fun getHolograms()

    fun updateHolograms()

    companion object {
        val INSTANCE = requiredService<SurfHologramApi>()
    }
}

val surfHologramApi = SurfHologramApi.INSTANCE