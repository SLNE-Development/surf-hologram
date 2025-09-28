package dev.slne.surf.hologram.api.hologram.util

import com.github.retrooper.packetevents.util.Vector3d

/**
 * Represents the hitbox of a hologram. The hitbox defines the spatial dimensions and the
 * offset of the hologram's center, which can be utilized for collision detection or
 * interaction handling within the hologram system.
 */
interface HologramHitbox {
    /**
     * Represents the horizontal dimension of the hologram's hitbox.
     *
     * This property defines the width, typically used in calculations related
     * to the hologram's collision or interaction boundaries. It is integral
     * to configuring and representing the hologram's spatial properties.
     */
    val width: Float

    /**
     * Represents the height of a hologram's hitbox in a 3D space.
     *
     * This property defines the vertical measurement of the hologram's interaction boundary
     * and is utilized in various operations, such as interaction metadata creation.
     */
    val height: Float

    /**
     * Represents the offset from the center of a hologram's hitbox.
     *
     * This property defines a 3D vector that determines the positional adjustment
     * applied to the center of the hitbox for the hologram. It allows for fine-tuning
     * of where interaction or other calculations involving the hologram take place
     * relative to its defined center.
     */
    val hologramCenterOffset: Vector3d
}