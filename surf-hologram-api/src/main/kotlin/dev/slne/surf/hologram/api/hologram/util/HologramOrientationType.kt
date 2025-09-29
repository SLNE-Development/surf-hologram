package dev.slne.surf.hologram.api.hologram.util

/**
 * Defines the possible orientation types for a hologram.
 *
 * This enum is used to specify the behavior of the hologram in terms of its orientation
 * or movement within the 3D space. Different values represent distinct styles of
 * hologram orientation, providing flexible options for animation and presentation.
 */
enum class HologramOrientationType {
    /**
     * The FIXED orientation type represents a hologram that remains stationary and does not move
     * or rotate. This orientation is used when the hologram is required to maintain a constant
     * position and orientation in the game world.
     */
    FIXED,

    /**
     * Represents the bouncing orientation for a hologram.
     *
     * This orientation type allows the hologram to perform a dynamic up-and-down movement,
     * creating a bouncing effect. It is commonly used to attract attention to the hologram
     * or provide a visually engaging experience. This type of orientation influences the
     * animation style of the hologram in a predefined manner.
     */
    BOUNCING,

    /**
     * Represents the rotating orientation type for holograms.
     *
     * This orientation type defines behavior where the hologram rotates continuously,
     * typically for dynamic or attention-grabbing displays. It is used in configurations
     * where rotational motion enhances the visual appeal or interactive properties of
     * the hologram.
     *
     * For example, when the hologram orientation type is set to `ROTATING`, associated
     * metadata or rendering logic will apply continuous rotation to the hologram entity.
     */
    ROTATING
}