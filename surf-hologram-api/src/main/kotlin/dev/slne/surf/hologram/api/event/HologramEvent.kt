package dev.slne.surf.hologram.api.event

import dev.slne.surf.hologram.api.hologram.Hologram

/**
 * Represents a base event related to a hologram.
 *
 * This interface serves as a foundational contract for all events that involve hologram interactions.
 * Extensions of this interface may introduce additional properties or behaviors specific to the event type.
 */
interface HologramEvent {
    /**
     * Represents the hologram associated with the event. The hologram provides access
     * to its metadata, type, location, displayed text, creation reason, and viewers.
     * It also allows various operations such as showing, hiding, teleporting, refreshing,
     * and duplicating the hologram.
     *
     * This property is immutable and provides interaction with the associated hologram.
     */
    val hologram: Hologram
}