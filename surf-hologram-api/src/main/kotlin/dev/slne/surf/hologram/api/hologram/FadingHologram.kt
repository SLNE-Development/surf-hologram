package dev.slne.surf.hologram.api.hologram

/**
 * Represents a hologram with a fade-out animation.
 *
 * A fading hologram slowly moves upward over time until it reaches [fadeMaxOffset],
 * at which point it is automatically removed from the world. This is useful for
 * short-lived informational or effect holograms (e.g. damage numbers, rewards).
 */
interface FadingHologram : Hologram {
    /**
     * The amount of Y-units the hologram moves upward per tick during the fade animation.
     */
    val fadeSpeed: Double

    /**
     * The maximum Y-offset (relative to the spawn position) before the hologram
     * removes itself from the world.
     */
    val fadeMaxOffset: Double

    /**
     * Advances the fade animation by one tick.
     *
     * Moves the hologram upward by [fadeSpeed] and removes it once [fadeMaxOffset] is reached.
     */
    fun tick()
}
