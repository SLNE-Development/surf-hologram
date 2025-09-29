package dev.slne.surf.hologram.paper.registry

import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.core.registry.HologramTypeRegistry
import dev.slne.surf.surfapi.core.api.util.freeze
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import dev.slne.surf.surfapi.core.api.util.toObjectSet
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

    @Suppress("UNCHECKED_CAST")
    override fun <H : Hologram, O : Any> getHologramType(
        name: String
    ) = _types.values.firstOrNull { it.id == name } as? HologramType<H, O>

    @Suppress("UNCHECKED_CAST")
    override fun <H : Hologram, O : Any> getTypes() =
        _types.map { it as HologramType<H, O> }.toObjectSet().freeze()
}