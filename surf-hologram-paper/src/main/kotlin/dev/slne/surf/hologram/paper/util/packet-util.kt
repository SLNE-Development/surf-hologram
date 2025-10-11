package dev.slne.surf.hologram.paper.util

import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType
import com.github.retrooper.packetevents.protocol.player.User
import com.github.retrooper.packetevents.util.Vector3d
import com.github.retrooper.packetevents.util.Vector3f
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.surfapi.bukkit.api.util.forEachPlayer
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import org.bukkit.Bukkit

fun HologramLocation.toPacketLocation() =
    SpigotConversionUtil.fromBukkitLocation(this.toBukkitLocation())

fun debug(message: String) = forEachPlayer {
    it.sendText {
        spacer("[")
        info("DEBUG")
        spacer("] ")
        text(message)
    }
}

@DslMarker
annotation class EntityDataDsl

@EntityDataDsl
class EntityDataBuilder {
    private val entries = mutableListOf<EntityData<*>>()

    fun <T> entry(id: Int, type: EntityDataType<T>, value: T) {
        entries += EntityData(id, type, value)
    }

    fun build(): MutableList<EntityData<*>> = entries
}

fun buildEntityData(block: EntityDataBuilder.() -> Unit): List<EntityData<*>> {
    return EntityDataBuilder().apply(block).build()
}

fun Vector3d.toVector3f() = Vector3f(this.x.toFloat(), this.y.toFloat(), this.z.toFloat())

val User.player get() = Bukkit.getPlayer(this.uuid) ?: error("Packet User is not online!")

