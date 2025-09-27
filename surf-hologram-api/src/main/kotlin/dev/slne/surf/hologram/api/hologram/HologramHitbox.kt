package dev.slne.surf.hologram.api.hologram

import com.github.retrooper.packetevents.util.Vector3d

interface HologramHitbox {
    val width: Float
    val height: Float
    val hologramCenterOffset: Vector3d
}