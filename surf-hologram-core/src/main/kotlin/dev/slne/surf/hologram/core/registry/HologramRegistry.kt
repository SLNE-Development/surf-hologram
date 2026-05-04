package dev.slne.surf.hologram.core.registry

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.api.core.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet

interface HologramRegistry {
    fun getHologram(name: String): Hologram?
    fun getHologram(entityId: Int): Hologram? =
        holograms().firstOrNull { it.metaData.holoEntityId == entityId }

    fun getHologramByInteractionId(entityId: Int): Hologram? =
        holograms().firstOrNull { it.metaData.interactionEntityId == entityId }

    fun registerHologram(hologram: Hologram)
    fun unregisterHologram(hologram: Hologram)

    fun holograms(): ObjectSet<Hologram>

    companion object {
        val INSTANCE = requiredService<HologramRegistry>()
    }
}

val hologramRegistry get() = HologramRegistry.INSTANCE