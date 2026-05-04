package dev.slne.surf.hologram.paper.api

import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.SurfHologramApi
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramOptions
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.registry.hologramTypeRegistry
import dev.slne.surf.hologram.core.service.hologramPlayerService
import dev.slne.surf.hologram.core.service.hologramService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(SurfHologramApi::class)
class SurfHologramApiImpl : SurfHologramApi, Services.Fallback {
    override fun <H : Hologram, O : HologramOptions> createHologram(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        hologramClazz: Class<H>,
        options: O
    ) = hologramService.createHologram(
        metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        hitbox,
        viewers,
        hologramClazz,
        options
    )

    override fun deleteHologram(hologram: Hologram) = hologramService.deleteHologram(hologram)

    override fun refreshHologram(hologram: Hologram?) = hologramService.refresh(hologram)
    override fun refreshContent(hologram: Hologram?) = hologramService.refreshContent(hologram)

    override fun getHologram(name: String) = hologramRegistry.getHologram(name)

    override fun getPlayer(uuid: UUID, name: String) = hologramPlayerService.getPlayer(uuid, name)
    override fun getPlayer(uuid: UUID) = hologramPlayerService.getPlayer(uuid)
    override fun getPlayer(name: String) = hologramPlayerService.getPlayer(name)
    override fun getOfflinePlayer(uuid: UUID) = hologramPlayerService.getOfflinePlayer(uuid)

    override fun registerHologram(hologram: Hologram) = hologramRegistry.registerHologram(hologram)

    override fun <H : Hologram, O : HologramOptions> registerHologramType(
        type: HologramType<H, O>
    ) = hologramTypeRegistry.register(type)

    override fun <H : Hologram, O : HologramOptions> getHologramType(
        hologramClazz: Class<H>
    ) = hologramTypeRegistry.getHologramType<H, O>(hologramClazz)

    override fun all() = hologramRegistry.holograms()
}