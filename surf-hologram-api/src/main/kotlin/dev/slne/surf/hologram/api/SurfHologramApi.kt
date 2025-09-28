package dev.slne.surf.hologram.api

import com.github.retrooper.packetevents.util.Vector3d
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.location.HologramWorld
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.hologram.util.HologramTextAlignment
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

interface SurfHologramApi {
    fun createHologram(
        plugin: JavaPlugin,
        type: HologramOrientationType,
        hitbox: HologramHitbox?,
        metaData: HologramMetaData,
        centerLocation: HologramLocation,
        displayedText: Component,
        viewers: ObjectSet<HoloOfflinePlayer>? = null,
        bouncingHeight: Double? = null,
        bouncingStep: Double? = null
    ): Hologram

    fun createHologram(hologram: Hologram): Hologram
    fun createHitbox(width: Float, height: Float, centerLocationOffset: Vector3d): HologramHitbox
    fun createLocation(world: HologramWorld, x: Double, y: Double, z: Double): HologramLocation
    fun createWorld(worldName: String, worldId: UUID): HologramWorld

    fun getHologram(name: String): Hologram?
    fun deleteHologram(hologram: Hologram)
    fun editHologramSaving(hologram: Hologram, block: Hologram.() -> Unit): Hologram
    fun refreshHologram(hologram: Hologram?)

    fun createHologramMeta(
        name: String,
        scale: Vector3d = Vector3d(1.0, 1.0, 1.0),
        viewRange: Float = 32f,
        lineWidth: Int = 200,
        textAlignment: HologramTextAlignment = HologramTextAlignment.CENTER,
        backgroundColor: TextColor? = null
    ): HologramMetaData

    companion object {
        val INSTANCE = requiredService<SurfHologramApi>()
    }
}

val surfHologramApi get() = SurfHologramApi.INSTANCE