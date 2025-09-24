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
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.paper.util.toPacketLocation

object PaperPackets {
    fun buildHoloSpawnPacket(hologram: Hologram) = WrapperPlayServerSpawnEntity(
        hologram.metaData.holoEntityId,
        null,
        EntityTypes.TEXT_DISPLAY,
        hologram.centerLocation.toPacketLocation(),
        0f,
        0,
        Vector3d.zero()
    )

    fun buildHoloMetaPacket(hologram: Hologram) = WrapperPlayServerEntityMetadata(
        hologram.metaData.holoEntityId,
        listOf(
            EntityData(23, EntityDataTypes.ADV_COMPONENT, hologram.displayedText)
        )
    )

    fun buildHoloInteractionSpawnPacket(hologram: Hologram) = WrapperPlayServerSpawnEntity(
        hologram.metaData.interactionEntityId,
        null,
        EntityTypes.INTERACTION,
        hologram.centerLocation.toPacketLocation(),
        0f,
        0,
        Vector3d.zero()
    )

    fun buildHoloInteractionMetaPacket(hologram: Hologram) = WrapperPlayServerEntityMetadata(
        hologram.metaData.holoEntityId,
        listOf(
            EntityData(8, EntityDataTypes.FLOAT, hologram.metaData.interactionWidth),
            EntityData(9, EntityDataTypes.FLOAT, hologram.metaData.interactionHeight),
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