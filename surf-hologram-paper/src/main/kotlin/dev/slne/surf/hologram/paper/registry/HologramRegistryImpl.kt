package dev.slne.surf.hologram.paper.registry

import com.google.auto.service.AutoService
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObjectSetOf
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.core.registry.HologramRegistry
import net.kyori.adventure.util.Services

@AutoService(HologramRegistry::class)
class HologramRegistryImpl : HologramRegistry, Services.Fallback {
    val registeredHolograms = mutableObjectSetOf<Hologram>()

    override fun getHologram(name: String) =
        registeredHolograms.firstOrNull { it.metaData.name == name }

    override fun registerHologram(hologram: Hologram) {
        registeredHolograms.add(hologram)
    }

    override fun unregisterHologram(hologram: Hologram) {
        registeredHolograms.remove(hologram)
    }

    override fun holograms() = registeredHolograms.freeze()
}