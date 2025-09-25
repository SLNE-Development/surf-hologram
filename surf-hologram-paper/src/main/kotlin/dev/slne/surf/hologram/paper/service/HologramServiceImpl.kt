package dev.slne.surf.hologram.paper.service

import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.util.forEachViewer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.HologramService
import dev.slne.surf.hologram.paper.hologram.types.BouncingHologramImpl
import dev.slne.surf.hologram.paper.hologram.types.SimpleHologramImpl
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import net.kyori.adventure.util.Services

@AutoService(HologramService::class)
class HologramServiceImpl : HologramService, Services.Fallback {
    override fun createHologram(
        name: String,
        type: HologramType,
        metaData: HologramMetaData,
        centerLocation: HologramLocation,
        displayedText: Component,
        viewers: ObjectSet<HoloOfflinePlayer>?
    ): Hologram {
        hologramRegistry.getHologram(name)?.let {
            return it
        }

        val hologram = when (type) {
            HologramType.FIXED, HologramType.ROTATING -> SimpleHologramImpl(
                metaData,
                type,
                centerLocation,
                displayedText,
                viewers
            )

            HologramType.BOUNCING -> BouncingHologramImpl(
                metaData,
                type,
                centerLocation,
                displayedText,
                viewers,
                0,
                0.25,
                1.0 to 1,
                true
            )
        }

        hologramRegistry.registerHologram(hologram)
        return hologram
    }

    override fun deleteHologram(hologram: Hologram) {
        hologram.forEachViewer {
            it.hideHologram(hologram)
        }

        hologramRegistry.unregisterHologram(hologram)
    }

    override fun editHologramSaving(
        hologram: Hologram,
        block: Hologram.() -> Unit
    ): Hologram {
        hologram.forEachViewer {
            it.hideHologram(hologram)
        }
        hologramRegistry.unregisterHologram(hologram)

        hologram.block()

        hologramRegistry.registerHologram(hologram)
        hologram.forEachViewer {
            it.showHologram(hologram)
        }

        return hologram
    }
}