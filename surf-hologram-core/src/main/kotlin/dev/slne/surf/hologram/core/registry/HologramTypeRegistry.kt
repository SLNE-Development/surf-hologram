package dev.slne.surf.hologram.core.registry

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.type.HologramOptions
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet

interface HologramTypeRegistry {
    fun <H : Hologram, O : HologramOptions> register(type: HologramType<H, O>)
    fun <H : Hologram, O : HologramOptions> getHologramType(hologramClazz: Class<H>): HologramType<H, O>?
    fun <H : Hologram, O : HologramOptions> getHologramType(name: String): HologramType<H, O>?

    fun <H : Hologram, O : HologramOptions> getTypes(): ObjectSet<HologramType<H, O>>

    companion object {
        val INSTANCE = requiredService<HologramTypeRegistry>()
    }
}

val hologramTypeRegistry get() = HologramTypeRegistry.INSTANCE