package dev.slne.surf.hologram.api.hologram.util

import com.github.retrooper.packetevents.util.Vector3d
import net.kyori.adventure.text.format.TextColor

/**
 * Represents metadata related to a hologram instance.
 *
 * This interface defines the properties and methods necessary for describing
 * the attributes of a hologram, including its appearance, behavior, and
 * interaction capabilities. It is integral to managing hologram data and
 * providing a consistent structure for hologram-related configurations.
 */
interface HologramMetaData {
    /**
     * Represents the name of the hologram instance.
     *
     * This property is used to uniquely identify a hologram within the system and may also
     * be utilized for display or duplication purposes. Modifying this value can affect how
     * the hologram is referenced or displayed in various contexts.
     */
    var name: String

    /**
     * Represents the unique entity ID associated with the hologram's display entity.
     *
     * This ID is used to identify the hologram's entity within the game world, allowing
     * for management, interaction, and packet communication. It is essential for the
     * proper functioning of hologram-related operations and ensures that the hologram
     * is uniquely distinguishable among other entities.
     */
    var holoEntityId: Int

    /**
     * Represents the entity ID used for interaction purposes in the hologram system.
     *
     * This ID is associated with the interaction entity of a hologram. It facilitates
     * interaction handling, such as detecting and managing user actions performed on
     * the hologram. Each hologram has a unique interaction entity ID that can be modified
     * during operations like duplication or refreshing holograms.
     *
     * This property is integral to enabling and managing interactive elements within
     * holograms, ensuring proper event triggering and entity representation.
     */
    var interactionEntityId: Int

    /**
     * Represents the scale of the hologram in 3D space.
     *
     * This property is defined as a three-dimensional vector (`Vector3d`) and determines the
     * size of the hologram along the X, Y, and Z axes. It is used to adjust the hologram's
     * dimensions and proportions for rendering within the hologram system.
     */
    var scale: Vector3d

    /**
     * Represents the range within which a hologram is visible to entities or players.
     *
     * This property determines the maximum distance from the hologram at which it remains
     * visible. It directly influences how far an entity or player can be before the hologram
     * is no longer rendered or considered for interactions. A higher value increases the
     * visibility range, while a lower value limits it.
     */
    var viewRange: Float

    /**
     * Specifies the width of the line within the hologram's rendered appearance.
     *
     * This variable defines the thickness of the outline or border
     * around a hologram element. It is an integral property used to control
     * the visual styling and presentation of holograms.
     */
    var lineWidth: Int

    /**
     * Specifies the alignment of the hologram's text content.
     *
     * This property determines how the text is positioned relative to its center
     * within the hologram display. Possible values include:
     * - `CENTER`: Aligns the text in the center.
     * - `LEFT`: Aligns the text to the left.
     * - `RIGHT`: Aligns the text to the right.
     *
     * The alignment setting is essential for controlling the visual presentation
     * of the hologram's text, ensuring proper layout and readability.
     */
    var textAlignment: HologramTextAlignment

    /**
     * Represents the background color of the hologram text.
     *
     * This property defines the color applied to the background of the hologram's text
     * for visual customization. It is optional and may be null, in which case no background
     * color is rendered.
     */
    var backgroundColor: TextColor?

    /**
     * The time-to-live (TTL) of the hologram in milliseconds.
     *
     * When non-null the hologram will be automatically deleted once
     * `createdAt + ttl <= System.currentTimeMillis()`.  A value of `null` means
     * the hologram persists indefinitely.
     */
    var ttl: Long?

    /**
     * Creates and returns a duplicate of the current HologramMetaData instance.
     * The duplicated instance contains the same properties as the original,
     * allowing modifications to be made independently of the original instance.
     *
     * @return a new instance of HologramMetaData representing the duplicated data.
     */
    fun duplicate(): HologramMetaData
}