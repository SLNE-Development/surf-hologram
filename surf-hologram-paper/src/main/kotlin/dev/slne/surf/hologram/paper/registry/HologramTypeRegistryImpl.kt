package dev.slne.surf.hologram.paper.registry

import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.core.registry.HologramTypeRegistry
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import net.kyori.adventure.util.Services

@AutoService(HologramTypeRegistry::class)
class HologramTypeRegistryImpl : HologramTypeRegistry, Services.Fallback {
    private val _types = mutableObject2ObjectMapOf<Class<out Hologram>, HologramType<*, *>>()

    override fun <H : Hologram, O : Any> register(type: HologramType<H, O>) {
        _types[type.hologramClazz] = type
    }

    @Suppress("UNCHECKED_CAST")
    override fun <H : Hologram, O : Any> getHologramType(
        hologramClazz: Class<H>
    ) = _types[hologramClazz] as? HologramType<H, O>
}