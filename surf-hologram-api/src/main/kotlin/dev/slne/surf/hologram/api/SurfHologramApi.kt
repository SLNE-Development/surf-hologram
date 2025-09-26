package dev.slne.surf.hologram.api

import dev.slne.surf.hologram.api.hologram.*
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor

interface SurfHologramApi {
    fun createHologram(
        type: HologramType,
        metaData: HologramMetaData,
        centerLocation: HologramLocation,
        displayedText: Component,
        creationReason: HologramCreationReason,
        viewers: ObjectSet<HoloOfflinePlayer>? = null,
        bouncingHeight: Double? = null,
        bouncingStep: Double? = null
    ): Hologram

    fun getHologram(name: String): Hologram?
    fun deleteHologram(hologram: Hologram)
    fun editHologramSaving(hologram: Hologram, block: Hologram.() -> Unit): Hologram
    fun refreshHologram(hologram: Hologram?)

    fun buildMetaData(
        name: String,
        interactionWidth: Float,
        interactionHeight: Float,
        viewRange: Float,
        lineWidth: Int,
        textAlignment: HologramTextAlignment,
        backgroundColor: TextColor?,
        clickable: Boolean
    ): HologramMetaData

    companion object {
        val INSTANCE = requiredService<SurfHologramApi>()
    }
}

val surfHologramApi get() = SurfHologramApi.INSTANCE