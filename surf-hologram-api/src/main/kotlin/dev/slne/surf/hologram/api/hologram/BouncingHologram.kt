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

/**
 * Represents a hologram with bouncing behavior.
 *
 * This interface extends the `Hologram` interface, adding properties and methods
 * to control and manage the dynamic bouncing motion of the hologram. The bouncing
 * motion is determined by state, height, speed, and direction factors. This allows
 * for creating interactive, visually dynamic hologram experiences.
 */
interface BouncingHologram : Hologram {
    /**
     * Represents the current state of the bouncing motion for a `BouncingHologram`.
     *
     * The `bounceState` value defines the position or progression of the bounce
     * animation. Typically, it transitions over a range of values during
     * the animation cycle, influenced by factors such as bounce height, speed,
     * and direction.
     *
     * - A lower value may signify a stage closer to the starting position.
     * - A higher value may indicate progression towards the peak of the bounce.
     *
     * The behavior and interpretation of this property depend on the specific
     * implementation of the `tick` function and other properties like `bounceHeight`
     * and `bounceSpeed`.
     */
    var bounceState: Double

    /**
     * Represents the maximum vertical displacement of a bouncing hologram.
     *
     * The `bounceHeight` property defines the peak height that a hologram
     * can reach during its bounce animation. This value is fixed and provides
     * an upper bound for the oscillatory motion of the hologram.
     *
     * It is typically used in conjunction with other properties such as
     * `bounceState`, `bounceSpeed`, and `bounceDirection` to calculate
     * the hologram's position during its animation cycle.
     */
    val bounceHeight: Double

    /**
     * Represents the speed factor at which the hologram bounces.
     *
     * This property defines the velocity or rate of the hologram's
     * bouncing motion within the `BouncingHologram` implementation.
     * It directly influences how quickly the hologram oscillates between
     * its defined positions or heights during the bounce animation.
     */
    val bounceSpeed: BounceSpeed

    /**
     * Specifies the current direction of the bouncing motion of the hologram.
     *
     * This variable is used to determine the vertical movement pattern of the hologram
     * during its bouncing animation. The direction can represent upward or downward
     * motion, depending on the implementation of the `BouncingHologram` interface.
     *
     * The exact behavior and transitions between directions may depend on the associated
     * bounce state, height, and speed properties.
     */
    var bounceDirection: BounceDirection

    /**
     * Updates the state of the bouncing hologram.
     *
     * This method calculates and applies the changes to the hologram's position
     * by adjusting its bounce state based on the current bounce direction,
     * bounce speed, and bounce height.
     *
     * It is typically called regularly, such as within a game loop, to achieve
     * the dynamic bouncing effect. The bounce state is updated incrementally,
     * potentially reversing the bounce direction if the hologram reaches
     * its defined bounds.
     */
    fun tick()
}