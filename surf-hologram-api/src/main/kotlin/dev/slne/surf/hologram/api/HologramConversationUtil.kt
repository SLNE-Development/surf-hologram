package dev.slne.surf.hologram.api

import com.github.retrooper.packetevents.util.Vector3d
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.location.HologramWorld
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramTextAlignment
import dev.slne.surf.surfapi.core.api.util.requiredService
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.util.*

interface HologramConversationUtil {
    fun createHitbox(width: Float, height: Float, centerLocationOffset: Vector3d): HologramHitbox
    fun createLocation(world: HologramWorld, x: Double, y: Double, z: Double): HologramLocation
    fun createWorld(worldName: String, worldId: UUID): HologramWorld
    fun createHologramMeta(
        name: String,
        scale: Vector3d = Vector3d(1.0, 1.0, 1.0),
        viewRange: Float = 32f,
        lineWidth: Int = 200,
        textAlignment: HologramTextAlignment = HologramTextAlignment.CENTER,
        backgroundColor: TextColor? = null
    ): HologramMetaData

    companion object {
        val INSTANCE = requiredService<HologramConversationUtil>()
    }
}

val hologramConversationUtil get() = HologramConversationUtil.INSTANCE

fun World.toHologramWorld() = hologramConversationUtil.createWorld(this.name, this.uid)
fun Location.toHologramLocation() =
    hologramConversationUtil.createLocation(this.world.toHologramWorld(), this.x, this.y, this.z)

fun HologramWorld.toBukkitWorld(): World =
    Bukkit.getWorld(this.worldId) ?: error("World with id $worldId not found")

fun HologramLocation.toBukkitLocation() =
    Location(this.world.toBukkitWorld(), this.x, this.y, this.z)

val World.hologramWorld get() = this.toHologramWorld()
val Location.hologramLocation get() = this.toHologramLocation()
val HologramLocation.bukkitLocation get() = this.toBukkitLocation()