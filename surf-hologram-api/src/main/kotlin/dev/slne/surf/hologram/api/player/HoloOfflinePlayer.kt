package dev.slne.surf.hologram.api.player

import java.util.*

/**
 * Represents a player in an offline state for handling hologram-related functionality.
 *
 * HoloOfflinePlayer provides the base definition for players that may not be
 * actively connected to the server but still maintain player-specific information
 * such as a unique identifier (UUID) and an optional associated active player instance.
 */
interface HoloOfflinePlayer {
    /**
     * Represents the unique identifier (UUID) associated with the offline player.
     * This UUID is used to identify the player within the system, including various
     * functionalities such as hologram displaying or player-related interactions.
     */
    val uuid: UUID

    /**
     * Represents an optional active `HoloPlayer` instance corresponding to this `HoloOfflinePlayer`.
     *
     * This property allows the retrieval of an associated `HoloPlayer` if the offline player is currently online.
     * It can be null if the player is not active or not currently online.
     */
    val player: HoloPlayer?
}