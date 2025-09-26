package dev.slne.surf.hologram.api

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramTextAlignment
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.location.HologramWorld
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import java.util.*

interface SurfHologramApi {
    fun createHologram(
        type: HologramType,
        metaData: HologramMetaData,
        centerLocation: HologramLocation,
        displayedText: Component,
        viewers: ObjectSet<HoloOfflinePlayer>? = null,
        bouncingHeight: Double? = null,
        bouncingStep: Double? = null
    ): Hologram

    fun createLocation(world: HologramWorld, x: Double, y: Double, z: Double): HologramLocation
    fun createWorld(worldName: String, worldId: UUID): HologramWorld

    fun getHologram(name: String): Hologram?
    fun deleteHologram(hologram: Hologram)
    fun editHologramSaving(hologram: Hologram, block: Hologram.() -> Unit): Hologram
    fun refreshHologram(hologram: Hologram?)

    fun buildMetaData(
        name: String,
        interactionWidth: Float = 1f,
        interactionHeight: Float = 1f,
        viewRange: Float = 32f,
        lineWidth: Int = 200,
        textAlignment: HologramTextAlignment = HologramTextAlignment.CENTER,
        backgroundColor: TextColor? = null,
        clickable: Boolean = true
    ): HologramMetaData

    companion object {
        val INSTANCE = requiredService<SurfHologramApi>()
    }
}

val surfHologramApi get() = SurfHologramApi.INSTANCE