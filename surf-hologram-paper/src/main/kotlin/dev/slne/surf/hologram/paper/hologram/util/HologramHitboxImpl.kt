package dev.slne.surf.hologram.paper.hologram.util

import com.github.retrooper.packetevents.util.Vector3d
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox

data class HologramHitboxImpl(
    override val width: Float,
    override val height: Float,
    override val hologramCenterOffset: Vector3d
) : HologramHitbox {
    companion object {
        fun default() = HologramHitboxImpl(1.0f, 1.0f, Vector3d(0.0, 0.0, 0.0))
    }
}