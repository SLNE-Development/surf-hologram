package dev.slne.surf.hologram.paper.hologram

import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramTextAlignment
import net.kyori.adventure.text.format.TextColor

data class HologramMetaDataImpl(
    override val name: String,
    override val holoEntityId: Int,
    override val interactionEntityId: Int,
    override val interactionWidth: Float,
    override val interactionHeight: Float,
    override val viewRange: Float,
    override val lineWidth: Int,
    override val textAlignment: HologramTextAlignment,
    override val backgroundColor: TextColor? = null
) : HologramMetaData