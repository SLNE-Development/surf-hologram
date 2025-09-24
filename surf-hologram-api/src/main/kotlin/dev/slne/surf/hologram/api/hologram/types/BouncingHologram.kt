package dev.slne.surf.hologram.api.hologram.types

import dev.slne.surf.hologram.api.hologram.Hologram


/**
 * Represents the speed of a bouncing hologram.
 * The first value is the height the hologram will move in the time given by the second value.
 */
typealias BounceSpeed = Pair<Double, Long>

/**
 * Represents the direction of a bouncing hologram.
 * True means the hologram is moving up; false means it is moving down.
 */
typealias BounceDirection = Boolean

interface BouncingHologram : Hologram {
    val bounceState: Int
    val bounceHeight: Double
    val bounceSpeed: BounceSpeed
    val bounceDirection: BounceDirection
    fun tick()
}