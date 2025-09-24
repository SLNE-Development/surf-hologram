package dev.slne.surf.hologram.paper.service

import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.core.service.HologramService
import net.kyori.adventure.util.Services

@AutoService(HologramService::class)
class HologramServiceImpl : HologramService, Services.Fallback {
    override fun createHologram(hologram: Hologram): Hologram? {
        TODO("Not yet implemented")
    }

    override fun deleteHologram(hologram: Hologram): Boolean {
        TODO("Not yet implemented")
    }

    override fun editHologram(
        hologram: Hologram,
        block: Hologram.() -> Unit
    ): Hologram {
        TODO("Not yet implemented")
    }
}