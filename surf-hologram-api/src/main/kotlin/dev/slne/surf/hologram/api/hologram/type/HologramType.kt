package dev.slne.surf.hologram.api.hologram.type

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

/**
 * Represents a type of hologram, defining its structure, behavior, and creation process.
 *
 * This interface acts as a template for defining distinct hologram types, specifying their
 * metadata, associated classes, and construction method. It is parameterized to work with
 * specific implementations of `Hologram` and `HologramOptions`.
 *
 * @param H The class representing the specific type of `Hologram`.
 * @param O The class representing the configuration options for the hologram.
 */
interface HologramType<H : Hologram, O : HologramOptions> {
    /**
     * A unique identifier for the type of hologram.
     *
     * This property is typically used to distinguish between different types of holograms. It serves
     * as a key in various operations such as registering, retrieving, or managing hologram types.
     *
     * The `id` must be unique across all registered hologram types to avoid conflicts during lookup
     * and ensure accurate identification of specific hologram implementations.
     */
    val id: String

    /**
     * Represents the class type of the hologram associated with this hologram type.
     *
     * This property is used to distinguish and map specific hologram implementations
     * when registering or retrieving hologram types. It allows for dynamic association
     * of custom hologram classes with their respective type definitions.
     *
     * The value corresponds to the runtime `Class<H>` of the generic hologram type parameter `H`.
     * Primarily utilized in hologram registries or when performing hologram-related operations,
     * ensuring that the correct hologram type is identified and matched.
     */
    val hologramClazz: Class<H>

    /**
     * Represents the class type of hologram options associated with a specific hologram type.
     *
     * The `optionsClazz` property identifies the concrete implementation of the `HologramOptions`
     * interface used for configuration of hologram behavior and appearance. It enables dynamic
     * retrieval of the specific options type required during the hologram creation process.
     *
     * This property is primarily utilized in hologram management systems to ensure that the correct
     * options type is applied based on the hologram's classification, facilitating broader flexibility
     * and adaptability for varied hologram configurations.
     */
    val optionsClazz: Class<O>

    /**
     * Creates and returns a new hologram instance based on the provided parameters.
     *
     * @param metaData The metadata associated with the hologram, such as name, scale, and view range.
     * @param hologramOrientationType The orientation type of the hologram (e.g., fixed, bouncing, rotating).
     * @param centerLocation The location at which the hologram will be placed within the world.
     * @param displayedText The text component that will be displayed by the hologram.
     * @param hitbox The hitbox defining the interactive bounds of the hologram, or null for no hitbox.
     * @param viewers A set of offline players who can view the hologram, or null if visible to all.
     * @param options The specific configuration options for the hologram type.
     * @return A new instance of the hologram with the specified properties and behaviors.
     */
    fun create(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        options: O
    ): H
}