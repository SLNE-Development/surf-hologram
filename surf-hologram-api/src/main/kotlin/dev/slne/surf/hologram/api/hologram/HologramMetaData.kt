package dev.slne.surf.hologram.api.hologram

import com.github.retrooper.packetevents.util.Vector3d
import net.kyori.adventure.text.format.TextColor

interface HologramMetaData {
    var name: String
    var holoEntityId: Int
    var interactionEntityId: Int
    var interactionWidth: Float
    var interactionHeight: Float
    var scale: Vector3d
    var viewRange: Float
    var lineWidth: Int
    var textAlignment: HologramTextAlignment
    var backgroundColor: TextColor?
    var clickable: Boolean

    fun duplicate(): HologramMetaData
}