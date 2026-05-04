package dev.slne.surf.hologram.paper.hologram.util

import com.github.retrooper.packetevents.util.Vector3d
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramTextAlignment
import net.kyori.adventure.text.format.TextColor

data class HologramMetaDataImpl(
    override var name: String,
    override var holoEntityId: Int,
    override var interactionEntityId: Int,
    override var scale: Vector3d,
    override var viewRange: Float,
    override var lineWidth: Int,
    override var textAlignment: HologramTextAlignment,
    override var backgroundColor: TextColor? = null,
    override var ttl: Long? = null
) : HologramMetaData {
    override fun duplicate() = HologramMetaDataImpl(
        name,
        holoEntityId,
        interactionEntityId,
        scale,
        viewRange,
        lineWidth,
        textAlignment,
        backgroundColor,
        ttl
    )
}