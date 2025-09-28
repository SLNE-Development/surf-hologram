package dev.slne.surf.hologram.api.event

import dev.slne.surf.hologram.api.player.HoloPlayer

/**
 * Represents an event involving a hologram and a player.
 *
 * This interface extends [HologramEvent] and introduces a player-specific
 * property, allowing the event to be associated with a particular [HoloPlayer].
 * It is designed for events where player interaction or involvement with a hologram
 * is a primary factor.
 */
interface HologramPlayerEvent : HologramEvent {
    /**
     * Represents a player associated with the hologram player event.
     *
     * This property provides access to the player who is interacting
     * with the hologram in the context of an event. The `player` may
     * represent an online entity capable of showing or hiding holograms.
     */
    val player: HoloPlayer
}