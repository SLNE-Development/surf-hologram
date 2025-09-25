package dev.slne.surf.hologram.api.hologram

import net.kyori.adventure.text.format.TextColor

interface HologramMetaData {
    val name: String
    val holoEntityId: Int
    val interactionEntityId: Int
    val interactionWidth: Float
    val interactionHeight: Float
    val viewRange: Float
    val lineWidth: Int
    val textAlignment: HologramTextAlignment
    val backgroundColor: TextColor?
}