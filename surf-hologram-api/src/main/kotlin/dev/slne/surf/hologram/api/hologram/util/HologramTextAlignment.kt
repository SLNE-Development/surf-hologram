package dev.slne.surf.hologram.api.hologram.util

/**
 * Represents the text alignment options for a hologram's text content.
 *
 * This enum defines the available alignment modes that determine how the text
 * is displayed relative to its center in the hologram's visual space. The alignment
 * directly influences the positioning of the text within the hologram, providing
 * flexibility for different presentation styles.
 */
enum class HologramTextAlignment(val value: Int) {
    /**
     * Aligns text content to the center within the hologram display.
     *
     * This alignment option ensures that text is centrally positioned relative to the hologram's
     * designated text area. It is commonly used when symmetrical or balanced text placement
     * is desired for the hologram's appearance.
     */
    CENTER(0),

    /**
     * Aligns the text of the hologram to the left.
     *
     * This alignment setting positions the text content starting
     * from the left side of the hologram's defined text area.
     * It is used for cases where non-centered text representation
     * is desired within the hologram system.
     *
     * LEFT alignment ensures that all text elements are primarily
     * visually aligned to the left edge of the hologram.
     */
    LEFT(1),

    /**
     * Represents the right-aligned text position within the hologram display.
     *
     * This alignment is used to position the hologram's text content to the far
     * right relative to its defined center. It ensures that the text layout aligns
     * correctly based on the specified alignment configuration, particularly in
     * contexts where right-side alignment is required.
     */
    RIGHT(2)
}
