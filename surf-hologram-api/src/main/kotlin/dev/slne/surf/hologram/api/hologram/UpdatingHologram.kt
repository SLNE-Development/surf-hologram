package dev.slne.surf.hologram.api.hologram

/**
 * Represents a hologram that updates its content over time.
 * It extends the basic functionality of a hologram by allowing periodic updates.
 */
interface UpdatingHologram : Hologram {
    /**
     * The time interval, in milliseconds, that determines how often the hologram updates.
     * Used to specify the frequency of update calls for the hologram.
     */
    val interval: Long

    /**
     * Updates the state or representation of the hologram. The implementation
     * of this method should handle changes or animations that occur at regular
     * intervals defined by the `interval` property.
     *
     * This method is intended to be invoked periodically to ensure the hologram
     * remains synchronized or visually accurate based on its intended behavior.
     */
    fun update()
}