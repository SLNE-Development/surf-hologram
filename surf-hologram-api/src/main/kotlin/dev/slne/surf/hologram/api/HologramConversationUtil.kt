package dev.slne.surf.hologram.api

import com.github.retrooper.packetevents.util.Vector3d
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.location.HologramWorld
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramTextAlignment
import dev.slne.surf.api.core.util.requiredService
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.util.*

/**
 * Provides utility methods for creating and managing hologram elements.
 *
 * The `HologramConversationUtil` interface contains factory methods to create various
 * components related to holograms, such as hitboxes, locations, worlds, and metadata.
 * These utilities are designed to assist in defining and manipulating holograms
 * for visualization, interaction, or other purposes.
 */
interface HologramConversationUtil {
    /**
     * Creates a new hologram hitbox with the specified dimensions and center location offset.
     *
     * @param width The horizontal dimension of the hitbox.
     * @param height The vertical dimension of the hitbox.
     * @param centerLocationOffset A 3D vector representing the offset from the center of the hitbox.
     * @return A new instance of a hologram hitbox configured with the given dimensions and offset.
     */
    fun createHitbox(width: Float, height: Float, centerLocationOffset: Vector3d): HologramHitbox

    /**
     * Creates a new HologramLocation in the specified world and coordinates.
     *
     * This method constructs an instance of HologramLocation using the given world and 3D coordinates
     * (x, y, z). The resulting location can be used to position holograms within the specified
     * world context.
     *
     * @param world The HologramWorld where the location will be created.
     * @param x The X-coordinate of the location.
     * @param y The Y-coordinate of the location.
     * @param z The Z-coordinate of the location.
     * @param yaw The yaw (horizontal rotation) of the location. Default is 0f.
     * @param pitch The pitch (vertical rotation) of the location. Default is 0
     * @return A new HologramLocation instance representing the specified coordinates in the given world.
     */
    fun createLocation(
        world: HologramWorld,
        x: Double,
        y: Double,
        z: Double,
        yaw: Float = 0f,
        pitch: Float = 0f
    ): HologramLocation

    /**
     * Creates a new instance of `HologramWorld` with the specified name and unique identifier.
     *
     * This method generates a hologram world representation that can be used to integrate
     * and interact with holograms within the specified world.
     *
     * @param worldName The name of the world to be associated with the `HologramWorld`.
     * @param worldId The unique identifier (`UUID`) of the world to be associated with the `HologramWorld`.
     * @return A new `HologramWorld` instance representing the specified world.
     */
    fun createWorld(worldName: String, worldId: UUID): HologramWorld

    /**
     * Creates hologram metadata with specified attributes.
     *
     * This method generates an instance of `HologramMetaData` representing
     * the configuration for a hologram, including its name, scale, visibility range,
     * text properties, and optional visual elements such as background color.
     *
     * @param name The name of the hologram. Used as a unique identifier within the system.
     * @param scale The scale of the hologram in 3D space, represented by a `Vector3d` object. Default is (1.0, 1.0, 1.0).
     * @param viewRange The maximum distance within which the hologram is visible. Default is 32f.
     * @param lineWidth The width of the text outline or border in the hologram. Default is 200.
     * @param textAlignment The alignment of the text content within the hologram. Default is `HologramTextAlignment.CENTER`.
     * @param backgroundColor The optional background color for the hologram text. If null, no background color will be applied. Default is null.
     * @param ttl Optional time-to-live in milliseconds. The hologram will be removed automatically after this duration. Default is null (no expiry).
     * @return An instance of `HologramMetaData` containing the specified hologram configuration.
     */
    fun createHologramMeta(
        name: String,
        scale: Vector3d = Vector3d(1.0, 1.0, 1.0),
        viewRange: Float = 32f,
        lineWidth: Int = 200,
        textAlignment: HologramTextAlignment = HologramTextAlignment.CENTER,
        backgroundColor: TextColor? = null,
        ttl: Long? = null
    ): HologramMetaData

    /**
     * Companion object for the `HologramConversationUtil` class.
     *
     * Provides a singleton instance of the `HologramConversationUtil` service, which includes
     * utility methods for managing various hologram-related operations such as creating
     * hitboxes, locations, metadata, and worlds. The service enables streamlined interaction
     * with holograms in the API, centralizing essential functionality for hologram management.
     */
    companion object {
        /**
         * Singleton instance of the `HologramConversationUtil` service.
         *
         * This instance provides utility methods for creating and managing various
         * components related to hologram functionalities, such as hologram hitboxes,
         * locations, metadata, and associated worlds. It acts as a centralized service
         * to enable interaction with hologram-related operations in the API.
         *
         * The `INSTANCE` is automatically resolved and initialized using the `requiredService`
         * mechanism, ensuring that the necessary implementation of `HologramConversationUtil`
         * is always available.
         */
        val INSTANCE = requiredService<HologramConversationUtil>()
    }
}

/**
 * Provides access to the `HologramConversationUtil` instance, which facilitates
 * the creation and management of hologram-related objects and metadata.
 *
 * The utility exposes various methods to perform operations such as creating
 * hologram worlds, locations, and metadata used for customizing holograms
 * and their associated properties. It serves as a core component for working
 * with holograms in the system and integrating them with Minecraft world-specific
 * data, locations, or events.
 *
 * This property references the singleton instance of `SurfHologramApi`'s
 * `HologramConversationUtil`, ensuring consistent behavior and access throughout the application.
 */
val hologramConversationUtil get() = HologramConversationUtil.INSTANCE

/**
 * Converts the current `World` instance into a `HologramWorld` representation.
 *
 * This extension function utilizes the `hologramConversationUtil` to create a new
 * `HologramWorld` based on the properties of the current `World`. The `HologramWorld`
 * serves as the hologram-specific equivalent for managing and interacting with holograms
 * in the context of the Minecraft world.
 *
 * @receiver The `World` instance representing the Minecraft world.
 * @return A `HologramWorld` instance created from the current `World`.
 */
fun World.toHologramWorld() = hologramConversationUtil.createWorld(this.name, this.uid)

/**
 * Converts a `Location` object into a `HologramLocation`.
 *
 * This extension function transforms a standard `Location` instance
 * into a `HologramLocation`, which represents the position of a hologram
 * within a designated hologram world. This is achieved by mapping the
 * `Location`'s world to a `HologramWorld` using the `toHologramWorld()`
 * function, and then constructing a `HologramLocation` with the respective
 * coordinates and hologram-specific world context through the
 * `hologramConversationUtil.createLocation` method.
 *
 * @receiver The `Location` instance to be converted.
 * @return A new `HologramLocation` representing the same coordinates within the corresponding hologram world.
 */
fun Location.toHologramLocation() =
    hologramConversationUtil.createLocation(this.world.toHologramWorld(), this.x, this.y, this.z)

/**
 * Converts the `HologramWorld` instance to its corresponding Bukkit `World` instance.
 *
 * This method retrieves the `World` object from Bukkit using the unique identifier (`worldId`)
 * of the current `HologramWorld`. If the `World` with the given `worldId` cannot be found,
 * an error is thrown.
 *
 * @return The Bukkit `World` instance associated with the `HologramWorld`.
 * @throws IllegalStateException if no Bukkit `World` is found for the given `worldId`.
 */
fun HologramWorld.toBukkitWorld(): World =
    Bukkit.getWorld(this.worldId) ?: error("World with id $worldId not found")

/**
 * Converts a `HologramLocation` into a Bukkit `Location`.
 *
 * This extension function transforms a `HologramLocation` that represents
 * the position of a hologram in the hologram API into a Bukkit-compatible
 * `Location` instance, which can then be used in Bukkit-based operations.
 *
 * The resultant `Location` uses the x, y, z coordinates and world specified
 * in the `HologramLocation`. The `world` property is converted into a Bukkit
 * `World` using the `toBukkitWorld` extension function.
 *
 * @receiver The `HologramLocation` instance to be converted.
 * @return A `Location` in Bukkit that corresponds to the coordinates and world
 *         of the original `HologramLocation`.
 * @throws IllegalStateException if the conversion of the `world` to a Bukkit
 *         `World` fails.
 */
fun HologramLocation.toBukkitLocation() =
    Location(this.world.toBukkitWorld(), this.x, this.y, this.z)

/**
 * Extension property for transforming a `World` instance into its hologram-specific counterpart, `HologramWorld`.
 *
 * This property provides a simplified and intuitive way to convert a standard
 * Minecraft `World` object into a `HologramWorld` representation. The resulting
 * `HologramWorld` is tailored for managing and interacting with holograms within
 * the context of the current world.
 *
 * The conversion leverages the `toHologramWorld` extension function, ensuring
 * seamless integration between the world and hologram management utilities.
 *
 * @receiver The original `World` instance being transformed.
 * @return A `HologramWorld` representation of the current `World`.
 */
val World.hologramWorld get() = this.toHologramWorld()

/**
 * Extension property for converting a standard Minecraft `Location` to a `HologramLocation`.
 * Utilizes the `toHologramLocation` function to translate the current `Location` instance
 * into the format suitable for hologram manipulation within the SurfHologram API.
 *
 * This transformation generally involves adapting the world and positional data
 * (x, y, z) to their hologram-specific counterparts.
 */
val Location.hologramLocation get() = this.toHologramLocation()

/**
 * Provides a convenient accessor for converting a `HologramLocation` into a Bukkit `Location`.
 *
 * This property retrieves the corresponding Bukkit `Location` representation for the current
 * `HologramLocation` instance. The conversion utilizes the encapsulated world and coordinate data
 * from `HologramLocation` and maps it to the Bukkit API's `Location` format.
 *
 * The resulting `Location` can be used for operations involving the Bukkit API, such as spawning entities,
 * executing commands, or other functionalities that require precise positioning within a Minecraft world.
 *
 * @return A `Location` object derived from the current `HologramLocation` instance.
 */
val HologramLocation.bukkitLocation get() = this.toBukkitLocation()