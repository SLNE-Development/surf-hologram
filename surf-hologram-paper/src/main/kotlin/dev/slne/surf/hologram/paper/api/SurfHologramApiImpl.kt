package dev.slne.surf.hologram.paper.api

import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.SurfHologramApi
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.util.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.registry.hologramTypeRegistry
import dev.slne.surf.hologram.core.service.hologramService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import net.kyori.adventure.util.Services
import org.bukkit.plugin.java.JavaPlugin

@AutoService(SurfHologramApi::class)
class SurfHologramApiImpl : SurfHologramApi, Services.Fallback {
    override fun <H : Hologram, O : Any> createHologram(
        plugin: JavaPlugin,
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        hologramClazz: Class<H>,
        options: O
    ): H {
        val type = hologramTypeRegistry.getHologramType<H, O>(hologramClazz)
            ?: error("Hologram type for class ${hologramClazz.name} is not registered!")

        return type.create(
            metaData,
            hologramOrientationType,
            centerLocation,
            displayedText,
            HologramCreationReason.Plugin(plugin.name),
            hitbox,
            viewers,
            options
        )
    }

    override fun deleteHologram(hologram: Hologram) = hologramService.deleteHologram(hologram)
    override fun editHologramSaving(
        hologram: Hologram,
        block: Hologram.() -> Unit
    ) = hologramService.editHologramSaving(hologram, block)

    override fun refreshHologram(hologram: Hologram?) = hologramService.refresh(hologram)

    override fun getHologram(name: String) = hologramRegistry.getHologram(name)
    override fun registerHologram(hologram: Hologram) = hologramRegistry.registerHologram(hologram)

    override fun <H : Hologram, O : Any> registerHologramType(
        type: HologramType<H, O>
    ) = hologramTypeRegistry.register(type)

    override fun <H : Hologram, O : Any> getHologramType(
        hologramClazz: Class<H>
    ) = hologramTypeRegistry.getHologramType<H, O>(hologramClazz)
}