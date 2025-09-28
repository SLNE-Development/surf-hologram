package dev.slne.surf.hologram.api.hologram.location

/**
 * Represents the location of a hologram within a specific world.
 *
 * This interface defines the essential components required to pinpoint
 * a hologram's position in the 3D space of a specified world.
 */
interface HologramLocation {
    /**
     * Represents the X-coordinate of a hologram position in the Minecraft world.
     *
     * This property is part of the `HologramLocation` interface and defines the
     * horizontal position of the hologram within its associated world. The value
     * can be adjusted to change the hologram's location dynamically.
     *
     * Modifications to this property will be used in various calculations and representations,
     * such as converting this location to a Bukkit `Location` or updating packets
     * for client rendering.
     */
    var x: Double

    /**
     * Represents the Y-coordinate of the hologram's location in a 3D space.
     *
     * This property is used to determine and modify the vertical position of
     * a hologram within the respective world. It can be dynamically updated
     * to animate or change the hologram's position, such as in cases of bouncing
     * or teleportation.
     *
     * Associated with the `HologramLocation` interface, this coordinate combines
     * with `x`, `z`, and `world` to fully describe the hologram's location
     * in the Minecraft environment.
     */
    var y: Double

    /**
     * Represents the Z-coordinate of the hologram's location in a 3D space.
     * This property is used alongside `x` and `y` to define the position
     * of a hologram within a specific `HologramWorld`.
     *
     * It is primarily utilized in operations such as creating, duplicating,
     * or manipulating hologram locations, and in transforming the location
     * into formats compatible with systems like Bukkit or packet-based networking.
     */
    var z: Double

    /**
     * Represents the world component of a HologramLocation.
     *
     * Defines the specific HologramWorld instance associated with the hologram's location.
     * Provides access to properties such as the world's name, unique ID, and equivalent Bukkit world object.
     */
    var world: HologramWorld

    /**
     * Creates and returns a duplicate of the current `HologramLocation` instance.
     *
     * @return A new `HologramLocation` object with the same properties as the original.
     */
    fun duplicate(): HologramLocation
}