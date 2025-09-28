package dev.slne.surf.hologram.api.hologram.types

import dev.slne.surf.hologram.api.hologram.Hologram
import net.kyori.adventure.text.Component

/**
 * Represents a hologram specifically designed for displaying a scoreboard.
 *
 * A `ScoreboardHologram` is an extension of the `Hologram` interface that introduces the concept of
 * a scoreboard. It allows managing entries with placements, names, and scores, and provides
 * utilities to update, add, and clear these entries.
 *
 * Each entry in the scoreboard is associated with a placement index, which determines its position,
 * and is constrained by a defined placement range. Additionally, scores in the scoreboard are
 * formatted based on a specific unit, allowing custom representations for displayed scores.
 */
interface ScoreboardHologram : Hologram {
    /**
     * Defines the valid range of placements available for entries within the scoreboard hologram.
     *
     * This range is utilized to determine permissible indices for adding, removing, or updating
     * entries in the hologram. The start of the range typically represents the highest placement,
     * while the end represents the lowest placement.
     */
    val placementRange: IntRange

    /**
     * Represents the unit of measurement or representation used for scores
     * displayed within a scoreboard hologram.
     *
     * This property defines how scores are contextualized or formatted when
     * rendered on the hologram. Typical values might represent points, levels,
     * or other scoring metrics specific to the application's domain.
     *
     * It is utilized in methods and features of the `ScoreboardHologram` interface,
     * such as displaying and managing score entries within the hologram.
     */
    val scoreUnit: String

    /**
     * Updates the current state of the scoreboard hologram.
     *
     * This method recalculates and refreshes the displayed contents of the scoreboard,
     * ensuring that all entries and scores are accurately represented based on the current
     * data. It may also involve repositioning or re-rendering elements within the hologram's
     * configuration, maintaining consistency with the placement range and score unit.
     */
    fun update()

    /**
     * Adds a new entry to the scoreboard at the specified placement with the given name and score.
     *
     * @param placement The position in the scoreboard where the entry should be added. Must fall within the valid placement range.
     * @param name The name of the entry, represented as a Component.
     * @param score The score associated with the entry.
     * @return True if the entry is successfully added, or false if the placement is invalid or already occupied.
     */
    fun addEntry(placement: Int, name: Component, score: Int): Boolean

    /**
     * Removes the entry at the specified placement in the scoreboard.
     *
     * @param placement The placement index of the entry to be removed. This must
     * be within the valid `placementRange` of the scoreboard.
     * @return `true` if the entry was successfully removed, `false` if no entry
     * existed at the specified placement.
     */
    fun removeEntry(placement: Int): Boolean

    /**
     * Clears all entries currently displayed in the scoreboard hologram.
     *
     * This method removes every entry, both names and their associated scores,
     * from within the scoreboard hologram's defined placement range.
     *
     * It effectively resets the scoreboard hologram to its default state,
     * erasing all previous scores and names without modifying other hologram properties.
     *
     * Commonly used before repopulating the scoreboard with new entries
     * or when resetting the hologram display entirely.
     */
    fun clearEntries()
}

/**
 * Represents an entry in a scoreboard hologram, providing details about the placement,
 * name, and score associated with that entry. This is typically used to define
 * individual lines in a scoreboard hologram display.
 */
interface ScoreboardHologramEntry {
    /**
     * Represents the specific position or rank of an entry on the scoreboard hologram.
     * The value determines the placement of an entry in the displayed list.
     */
    val placement: Int

    /**
     * Represents the name of the entry in the scoreboard hologram.
     * This is a textual component that typically displays the entity's name
     * or a label corresponding to the scoreboard entry.
     */
    val name: Component

    /**
     * Represents the score value associated with a specific entry in the scoreboard hologram.
     * This value typically indicates the performance or ranking of the entry relative to others.
     */
    val score: Int
}