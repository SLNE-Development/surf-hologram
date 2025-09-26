package dev.slne.surf.hologram.api.hologram

import net.kyori.adventure.text.format.TextColor

interface HologramMetaData {
    val name: String
    val holoEntityId: Int
    val interactionEntityId: Int
    var interactionWidth: Float
    var interactionHeight: Float
    var viewRange: Float
    var lineWidth: Int
    var textAlignment: HologramTextAlignment
    var backgroundColor: TextColor?
    var clickable: Boolean
}