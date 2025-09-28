package dev.slne.surf.hologram.paper.api

import com.github.retrooper.packetevents.util.Vector3d
import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.SurfHologramApi
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.location.HologramWorld
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.util.*
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.registry.hologramTypeRegistry
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.hologram.util.HologramHitboxImpl
import dev.slne.surf.hologram.paper.hologram.util.HologramMetaDataImpl
import dev.slne.surf.hologram.paper.hologram.util.location.HologramLocationImpl
import dev.slne.surf.hologram.paper.hologram.util.location.HologramWorldImpl
import dev.slne.surf.surfapi.core.api.util.random
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.Services
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@AutoService(SurfHologramApi::class)
class SurfHologramApiImpl : SurfHologramApi, Services.Fallback {
    override fun createHologram(
        plugin: JavaPlugin,
        type: HologramOrientationType,
        hitbox: HologramHitbox?,
        metaData: HologramMetaData,
        centerLocation: HologramLocation,
        displayedText: Component,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        bouncingHeight: Double?,
        bouncingStep: Double?
    ): Hologram = hologramService.createHologram(
        type,
        hitbox,
        metaData,
        centerLocation,
        displayedText,
        HologramCreationReason.Plugin(plugin.name),
        viewers,
        bouncingHeight,
        bouncingStep
    )

    override fun createHologram(hologram: Hologram) = hologramService.createHologram(hologram)
    override fun createHitbox(
        width: Float,
        height: Float,
        centerLocationOffset: Vector3d
    ) = HologramHitboxImpl(
        width,
        height,
        centerLocationOffset
    )

    override fun createLocation(
        world: HologramWorld,
        x: Double,
        y: Double,
        z: Double
    ) = HologramLocationImpl(
        x,
        y,
        z,
        world
    )

    override fun createWorld(
        worldName: String,
        worldId: UUID
    ) = HologramWorldImpl(
        worldName,
        worldId
    )

    override fun getHologram(name: String) = hologramRegistry.getHologram(name)
    override fun deleteHologram(hologram: Hologram) = hologramService.deleteHologram(hologram)

    override fun editHologramSaving(
        hologram: Hologram,
        block: Hologram.() -> Unit
    ) = hologramService.editHologramSaving(hologram, block)

    override fun refreshHologram(hologram: Hologram?) = hologramService.refresh(hologram)
    override fun createHologramMeta(
        name: String,
        scale: Vector3d,
        viewRange: Float,
        lineWidth: Int,
        textAlignment: HologramTextAlignment,
        backgroundColor: TextColor?
    ) = HologramMetaDataImpl(
        name,
        random.nextInt(),
        random.nextInt(),
        scale,
        viewRange,
        lineWidth,
        textAlignment,
        backgroundColor
    )

    override fun <H : Hologram, O : Any> registerHologramType(
        type: HologramType<H, O>
    ) = hologramTypeRegistry.register(type)

    override fun <H : Hologram, O : Any> getHologramType(
        hologramClazz: Class<H>
    ) = hologramTypeRegistry.getHologramType<H, O>(hologramClazz)
}