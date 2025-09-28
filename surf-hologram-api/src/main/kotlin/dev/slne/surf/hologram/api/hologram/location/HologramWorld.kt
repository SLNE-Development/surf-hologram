package dev.slne.surf.hologram.api.hologram.location

import org.bukkit.World
import java.util.*

/**
 * Represents a world associated with holograms in the Minecraft environment.
 *
 * The `HologramWorld` interface provides essential properties that define
 * the world context for holograms. These properties include the world's
 * name, unique identifier, and corresponding Bukkit world object.
 *
 * This interface enables integration with Minecraft's world representation
 * and helps manage holograms within specific worlds.
 */
interface HologramWorld {
    /**
     * The name of the world where a hologram is located.
     *
     * This property represents the human-readable name of the associated world
     * within the `HologramWorld` implementation. It is used as an identifier
     * to describe the world in contexts like logging or displaying
     * location-related information.
     *
     * Typically corresponds to the name of the world in a Minecraft environment
     * and is integral in identifying the world associated with a hologram's position.
     */
    val worldName: String

    /**
     * Represents the unique identifier for a `HologramWorld`.
     *
     * This property is used to distinguish different hologram worlds. Each world
     * is associated with a distinct `UUID` for identification purposes. The `UUID`
     * ensures that even if two worlds have the same name, they can still be uniquely
     * identified based on this unique ID.
     */
    val worldId: UUID

    /**
     * Represents the corresponding Bukkit `World` instance for a hologram in a specific world.
     *
     * This property provides the direct connection to the Minecraft world associated
     * with the `HologramWorld`. It allows access to various world-related functionality,
     * such as retrieving locations, entities, or performing operations within the world.
     */
    val bukkitWorld: World
}