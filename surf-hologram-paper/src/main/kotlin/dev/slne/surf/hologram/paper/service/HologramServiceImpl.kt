package dev.slne.surf.hologram.paper.service

import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
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
    override fun refresh(hologram: Hologram?) {
        if (hologram != null) {
            hologram.refresh()
        } else {
            hologramRegistry.holograms().forEach { it.refresh() }
        }
    }

    override fun refreshClean(hologram: Hologram) {
        hologram.refreshClean()
    }

    override fun createHologram(
        type: HologramOrientationType,
        hitbox: HologramHitbox?,
        metaData: HologramMetaData,
        centerLocation: HologramLocation,
        displayedText: Component,
        creationReason: HologramCreationReason,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        bouncingHeight: Double?,
        bouncingStep: Double?
    ): Hologram {
        hologramRegistry.getHologram(metaData.name)?.let {
            return it
        }

        val hologram = when (type) {
            HologramOrientationType.FIXED, HologramOrientationType.ROTATING -> SimpleHologramImpl(
                metaData,
                type,
                centerLocation,
                displayedText,
                creationReason,
                hitbox,
                viewers
            )

            HologramOrientationType.BOUNCING -> BouncingHologramImpl(
                metaData,
                type,
                centerLocation,
                displayedText,
                creationReason,
                hitbox,
                viewers,
                0.0,
                bouncingHeight ?: 0.0,
                bouncingStep ?: 0.0,
                true
            )
        }

        hologramRegistry.registerHologram(hologram)
        return hologram
    }

    override fun createHologram(hologram: Hologram): Hologram {
        hologramRegistry.getHologram(hologram.metaData.name)?.let {
            return it
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