package dev.slne.surf.hologram.paper

import com.github.retrooper.packetevents.util.Vector3d
import com.google.auto.service.AutoService
import dev.slne.surf.hologram.api.HologramConversationUtil
import dev.slne.surf.hologram.api.hologram.location.HologramWorld
import dev.slne.surf.hologram.api.hologram.util.HologramTextAlignment
import dev.slne.surf.hologram.paper.hologram.util.HologramHitboxImpl
import dev.slne.surf.hologram.paper.hologram.util.HologramMetaDataImpl
import dev.slne.surf.hologram.paper.hologram.util.location.HologramLocationImpl
import dev.slne.surf.hologram.paper.hologram.util.location.HologramWorldImpl
import dev.slne.surf.surfapi.core.api.util.random
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(HologramConversationUtil::class)
class PaperHologramConversationUtil : HologramConversationUtil, Services.Fallback {
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
        z: Double,
        yaw: Float,
        pitch: Float,
    ) = HologramLocationImpl(
        x,
        y,
        z,
        world,
        yaw,
        pitch
    )

    override fun createWorld(
        worldName: String,
        worldId: UUID
    ) = HologramWorldImpl(
        worldName,
        worldId
    )

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
}