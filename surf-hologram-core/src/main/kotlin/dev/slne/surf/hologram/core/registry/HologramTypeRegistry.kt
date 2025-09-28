package dev.slne.surf.hologram.core.registry

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.surfapi.core.api.util.requiredService

interface HologramTypeRegistry {
    fun <H : Hologram, O : Any> register(type: HologramType<H, O>)
    fun <H : Hologram, O : Any> getHologramType(hologramClazz: Class<H>): HologramType<H, O>?

    companion object {
        val INSTANCE = requiredService<HologramTypeRegistry>()
    }
}

val hologramTypeRegistry get() = HologramTypeRegistry.INSTANCE