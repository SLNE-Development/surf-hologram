package dev.slne.surf.hologram.api

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramOptions
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import dev.slne.surf.surfapi.core.api.util.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import java.util.*

/**
 * SurfHologramApi is an interface providing operations for creating, managing, retrieving,
 * and registering holograms and associated components within an application.
 */
interface SurfHologramApi {
    /**
     * Creates a new hologram with the specified parameters, initializes it, and returns the instance.
     *
     * @param metaData metadata associated with the hologram, containing relevant information for its configuration and properties
     * @param hologramOrientationType the orientation type of the hologram, specifying its alignment or positioning logic
     * @param centerLocation the central location where the hologram will be displayed
     * @param displayedText the text component to be displayed in the hologram
     * @param hitbox an optional hitbox associated with the hologram for interaction or collision purposes
     * @param viewers an optional set of viewers who can see the hologram, defining player visibility restrictions
     * @param hologramClazz the class type of the hologram being created
     * @param options the options or configuration specific to the hologram
     * @return the created hologram instance of the specified class type
     */
    fun <H : Hologram, O : HologramOptions> createHologram(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        hologramClazz: Class<H>,
        options: O
    ): H

    /**
     * Deletes the specified hologram and removes it from the system.
     *
     * @param hologram The hologram instance to be deleted.
     */
    fun deleteHologram(hologram: Hologram)

    /**
     * Refreshes the state of the specified hologram. This method can be used to update the visual
     * representation of the hologram or reapply its configuration.
     *
     * @param hologram The hologram to be refreshed. If null, the method will perform no action.
     */
    fun refreshHologram(hologram: Hologram?)


    /**
     * Refreshes the content of the specified hologram. This method can be used to update the
     * hologram's displayed information or visuals dynamically.
     *
     * @param hologram The hologram whose content needs to be refreshed.
     * If null, every hologram's content will be refreshed.
     */
    fun refreshContent(hologram: Hologram?)

    /**
     * Registers a hologram in the system. This method adds the specified hologram
     * to the internal registry, enabling its management and interaction through the API.
     *
     * @param hologram The hologram instance to be registered.
     */
    fun registerHologram(hologram: Hologram)

    /**
     * Retrieves a hologram by its name.
     *
     * @param name the name of the hologram to retrieve
     * @return the hologram associated with the given name, or null if no hologram is found
     */
    fun getHologram(name: String): Hologram?

    /**
     * Retrieves a `HoloPlayer` instance that corresponds to the provided UUID and name.
     *
     * This method is used to fetch the online representation of a player involved
     * in hologram-related operations within the system.
     *
     * @param uuid The unique identifier of the player.
     * @param name The name of the player.
     * @return The `HoloPlayer` associated with the given UUID and name.
     */
    fun getPlayer(uuid: UUID, name: String): HoloPlayer

    /**
     * Retrieves a `HoloPlayer` instance associated with the given player's UUID.
     *
     * This method is used to fetch the online representation of a player involved
     * in hologram-related operations within the system.
     *
     * @param uuid The unique identifier of the player.
     * @return The `HoloPlayer` associated with the given UUID, or null if the player is not found or offline.
     */
    fun getPlayer(uuid: UUID): HoloPlayer?

    /**
     * Retrieves a `HoloPlayer` instance associated with the given player's name.
     *
     * This method is used to fetch the online representation of a player involved
     * in hologram-related operations within the system.
     *
     * @param name The name of the player.
     * @return The `HoloPlayer` associated with the given name, or null if the player is not found or offline.
     */
    fun getPlayer(name: String): HoloPlayer?

    /**
     * Retrieves a `HoloOfflinePlayer` representation for the specified UUID.
     *
     * This method allows accessing offline player information, which can be utilized
     * for hologram-related functionality or other offline player-specific operations.
     *
     * @param uuid The unique identifier of the offline player.
     * @return The `HoloOfflinePlayer` associated with the given UUID, or null if no such player exists.
     */
    fun getOfflinePlayer(uuid: UUID): HoloOfflinePlayer?

    /**
     * Registers a new hologram type, allowing the system to manage and create holograms of the specified type.
     *
     * @param type The hologram type to be registered, defining the hologram class and its associated options.
     */
    fun <H : Hologram, O : HologramOptions> registerHologramType(type: HologramType<H, O>)

    /**
     * Retrieves the hologram type associated with the given class of hologram.
     *
     * @param H The type of the hologram.
     * @param O The type of options associated with the hologram.
     * @param hologramClazz The class of the hologram to look up the type for.
     * @return The matching HologramType for the provided hologram class, or null if no such type is registered.
     */
    fun <H : Hologram, O : HologramOptions> getHologramType(hologramClazz: Class<H>): HologramType<H, O>?


    /**
     * Retrieves a set of all registered holograms in the system.
     *
     * @return An ObjectSet containing all holograms currently registered.
     */
    fun all(): ObjectSet<Hologram>

    /**
     * Provides a singleton instance of the SurfHologramApi service.
     * The object is used for accessing and managing holograms and their metadata,
     * as well as player-related hologram operations and utilities.
     */
    companion object {
        /**
         * Singleton instance of the `SurfHologramApi` service.
         *
         * This variable represents the main entry point for accessing hologram-related functionality
         * provided by the `SurfHologramApi`. It allows for operations such as creating, editing,
         * and managing holograms, retrieving players, and interacting with hologram types.
         */
        val INSTANCE = requiredService<SurfHologramApi>()
    }
}

/**
 * Provides access to the singleton instance of the SurfHologramApi.
 * This variable allows interaction with the hologram API functionalities.
 */
val surfHologramApi get() = SurfHologramApi.INSTANCE