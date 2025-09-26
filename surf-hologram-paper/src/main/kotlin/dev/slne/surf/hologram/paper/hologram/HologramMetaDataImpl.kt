package dev.slne.surf.hologram.paper.hologram

import com.github.retrooper.packetevents.util.Vector3d
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramTextAlignment
import net.kyori.adventure.text.format.TextColor

data class HologramMetaDataImpl(
    override val name: String,
    override val holoEntityId: Int,
    override val interactionEntityId: Int,
    override var interactionWidth: Float,
    override var interactionHeight: Float,
    override var scale: Vector3d,
    override var viewRange: Float,
    override var lineWidth: Int,
    override var textAlignment: HologramTextAlignment,
    override var backgroundColor: TextColor? = null,
    override var clickable: Boolean = true
) : HologramMetaData