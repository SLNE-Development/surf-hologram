package dev.slne.surf.hologram.api.hologram

interface HologramMetaData {
    val name: String
    val holoEntityId: Int
    val interactionEntityId: Int
    val interactionWidth: Float
    val interactionHeight: Float
}