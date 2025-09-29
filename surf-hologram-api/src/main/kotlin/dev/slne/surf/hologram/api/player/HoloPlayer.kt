package dev.slne.surf.hologram.api.player

import dev.slne.surf.hologram.api.hologram.Hologram
import org.bukkit.Bukkit

/**
 * Represents an online player in the context of hologram-related functionality.
 *
 * HoloPlayer extends the `HoloOfflinePlayer` interface and provides additional
 * methods and properties to interact with players that are currently online.
 * This interface allows for managing holograms visible to the player and includes
 * utility to access the underlying Bukkit player object.
 */
interface HoloPlayer : HoloOfflinePlayer {
    /**
     * The name of the player.
     *
     * Represents the display name or identifier of the player within the context
     * of the hologram system. This name is typically intended to be a human-readable
     * representation and may be used for identification or display purposes in
     * player-related functionalities.
     */
    val name: String

    /**
     * Retrieves the associated Bukkit player instance for this `HoloPlayer` using its unique UUID.
     *
     * This property provides a convenient way to access the corresponding
     * online `org.bukkit.entity.Player` object from the Bukkit API. It returns
     * the player object if the `HoloPlayer` is currently online, or null if the player
     * is offline or the UUID does not map to any online player.
     */
    val bukkitPlayer get() = Bukkit.getPlayer(this.uuid)

    /**
     * Displays the specified hologram to the player.
     *
     * @param hologram The hologram instance to be shown.
     */
    fun showHologram(hologram: Hologram)

    /**
     * Hides the specified hologram for the player.
     *
     * @param hologram The hologram instance that should be hidden from the player.
     */
    fun hideHologram(hologram: Hologram)
}