package dev.slne.surf.hologram.paper.registry

import com.google.auto.service.AutoService
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObject2ObjectMapOf
import dev.slne.surf.api.core.util.toObjectSet
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.type.HologramOptions
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.core.registry.HologramTypeRegistry
import net.kyori.adventure.util.Services

@AutoService(HologramTypeRegistry::class)
class HologramTypeRegistryImpl : HologramTypeRegistry, Services.Fallback {
    private val _types = mutableObject2ObjectMapOf<Class<out Hologram>, HologramType<*, *>>()

    override fun <H : Hologram, O : HologramOptions> register(type: HologramType<H, O>) {
        _types[type.hologramClazz] = type
    }

    @Suppress("UNCHECKED_CAST")
    override fun <H : Hologram, O : HologramOptions> getHologramType(
        hologramClazz: Class<H>
    ) = _types[hologramClazz] as? HologramType<H, O>

    @Suppress("UNCHECKED_CAST")
    override fun <H : Hologram, O : HologramOptions> getHologramType(
        name: String
    ) = _types.values.firstOrNull { it.id == name } as? HologramType<H, O>

    @Suppress("UNCHECKED_CAST")
    override fun <H : Hologram, O : HologramOptions> getTypes() =
        _types.map { it as HologramType<H, O> }.toObjectSet().freeze()
}