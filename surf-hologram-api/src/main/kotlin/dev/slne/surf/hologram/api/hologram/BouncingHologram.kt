package dev.slne.surf.hologram.api.hologram


/**
 * Represents the speed of a bouncing hologram.
 */
typealias BounceSpeed = Double

/**
 * Represents the direction of a bouncing hologram.
 * True means the hologram is moving up; false means it is moving down.
 */
typealias BounceDirection = Boolean

interface BouncingHologram : Hologram {
    var bounceState: Double
    val bounceHeight: Double
    val bounceSpeed: BounceSpeed
    var bounceDirection: BounceDirection
    fun tick()
}