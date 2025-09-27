package dev.slne.surf.hologram.paper

import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes
import com.github.retrooper.packetevents.util.Vector3d
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramHitbox
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.paper.util.buildEntityData
import dev.slne.surf.hologram.paper.util.toPacketLocation
import dev.slne.surf.hologram.paper.util.toVector3f
import java.util.*

object PaperPackets {
    fun buildHoloSpawnPacket(hologram: Hologram) = WrapperPlayServerSpawnEntity(
        hologram.metaData.holoEntityId,
        UUID.randomUUID(),
        EntityTypes.TEXT_DISPLAY,
        hologram.centerLocation.toPacketLocation(),
        0f,
        0,
        Vector3d.zero()
    )

    fun buildHoloMetaPacket(hologram: Hologram) = WrapperPlayServerEntityMetadata(
        hologram.metaData.holoEntityId,
        buildEntityData {
            entry(23, EntityDataTypes.ADV_COMPONENT, hologram.displayedText)
            entry(12, EntityDataTypes.VECTOR3F, hologram.metaData.scale.toVector3f())

            if (hologram.hologramType == HologramType.ROTATING) {
                entry(15, EntityDataTypes.BYTE, 3.toByte())
            }

            entry(17, EntityDataTypes.FLOAT, hologram.metaData.viewRange)
            entry(24, EntityDataTypes.INT, hologram.metaData.lineWidth)
            entry(27, EntityDataTypes.BYTE, (hologram.metaData.textAlignment.value shl 3).toByte())

            entry(25, EntityDataTypes.INT, hologram.metaData.backgroundColor?.let {
                val color = hologram.metaData.backgroundColor ?: return@buildEntityData
                (0xFF shl 24) or (color.red() shl 16) or (color.green() shl 8) or color.blue()
            } ?: 0)
        }
    )

    fun buildHoloInteractionSpawnPacket(hologram: Hologram, hitbox: HologramHitbox) =
        WrapperPlayServerSpawnEntity(
            hologram.metaData.interactionEntityId,
            UUID.randomUUID(),
            EntityTypes.INTERACTION,
            hologram.centerLocation.duplicate().apply {
                x += hitbox.hologramCenterOffset.x
                y += hitbox.hologramCenterOffset.y
                z += hitbox.hologramCenterOffset.z
            }.toPacketLocation(),
            0f,
            0,
            Vector3d.zero()
        )

    fun buildHoloInteractionMetaPacket(hologram: Hologram, hitbox: HologramHitbox) =
        WrapperPlayServerEntityMetadata(
            hologram.metaData.interactionEntityId,
            listOf(
                EntityData(8, EntityDataTypes.FLOAT, hitbox.width),
                EntityData(9, EntityDataTypes.FLOAT, hitbox.height),
                EntityData(10, EntityDataTypes.BOOLEAN, true)
            )
        )

    fun buildDestroyPacket(hologram: Hologram) = WrapperPlayServerDestroyEntities(
        hologram.metaData.holoEntityId,
        hologram.metaData.interactionEntityId
    )

    fun buildTeleportPacket(hologram: Hologram, location: HologramLocation) =
        WrapperPlayServerEntityTeleport(
            hologram.metaData.holoEntityId,
            location.toPacketLocation(),
            false
        )
}