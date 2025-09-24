package dev.slne.surf.hologram.paper.hologram

import dev.slne.surf.hologram.api.hologram.HologramMetaData

data class HologramMetaDataImpl(
    override val name: String,
    override val holoEntityId: Int,
    override val interactionEntityId: Int,
    override val interactionWidth: Float,
    override val interactionHeight: Float
) : HologramMetaData