package dev.slne.surf.hologram.api.hologram.type

import dev.slne.surf.hologram.api.hologram.BounceSpeed
import org.jetbrains.annotations.Range

/**
 * Represents a set of configurable options for holograms.
 *
 * This interface is intended to be implemented by various classes that define specific configuration
 * options for different types of holograms. Each implementation provides a unique set of properties
 * and behaviors required by the hologram type it supports.
 *
 * Implementing classes may include default or customizable parameters such as animation settings,
 * layout configuration, or interaction behavior, enabling broader flexibility in hologram creation and display.
 *
 * Constructor must be empty or all parameters must have default values.
 */
interface HologramOptions

/**
 * BouncingHologramOptions is a data class that represents configuration options for
 * a "bouncing" hologram. These options determine the behavior and appearance of the
 * hologram's bounce animation.
 *
 * @property bounceHeight The height of the hologram's bounce. Determines how high
 * the hologram will move vertically during the animation. Defaults to 1.0.
 *
 * @property bounceSpeed The speed of the hologram's bounce. Represents the rate at
 * which the bounce animation is performed. Defaults to 0.1.
 */
data class BouncingHologramOptions(
    var bounceHeight: Double = 1.0,
    var bounceSpeed: BounceSpeed = 0.1
) : HologramOptions

/**
 * Represents the configuration options for a simple hologram.
 *
 * This class outlines the properties or settings that can be used to influence
 * the behavior, appearance, or other attributes of a simple hologram when it
 * is being created or manipulated.
 *
 * Implemented by types that require minimal configuration compared to other
 * more specialized holograms, such as scoreboard or bouncing holograms.
 *
 * Typically used in conjunction with the `SimpleHologramType` to define
 * parameters for hologram creation.
 */
class SimpleHologramOptions : HologramOptions

/**
 * Represents configuration options for a ScoreboardHologram.
 *
 * This class is primarily used to define properties associated with the behavior
 * and appearance of a scoreboard hologram, such as placement range and score units.
 *
 * @property placementRange The range of positions where the hologram can be displayed.
 *                          Defaults to values between 1 and 10.
 * @property scoreUnit The unit representation for scores displayed in the hologram.
 *                     Defaults to "P." (points).
 */
data class ScoreboardHologramOptions(
    var placementRange: IntRange = 1..10,
    var scoreUnit: String = "P."
) : HologramOptions

/**
 * Represents configuration options for a hologram that supports periodic updating.
 *
 * This class allows the customization of the update interval for holograms that require
 * scheduled updates. It specifies how frequently the hologram's state or visual representation
 * should be refreshed.
 *
 * @property updateInterval The interval, in milliseconds, at which the hologram updates.
 * Must be at least 50 milliseconds.
 * Defaults to 60,000 milliseconds (1 minute).
 */
data class UpdatingHologramOptions(
    var updateInterval: @Range(from = 50, to = Long.MAX_VALUE) Long = 60_000L
) : HologramOptions

/**
 * Represents configuration options for a [dev.slne.surf.hologram.api.hologram.FadingHologram].
 *
 * @property fadeSpeed The amount of Y-units the hologram moves upward per tick. Defaults to 0.05.
 * @property fadeMaxOffset The maximum Y-offset before the hologram is removed. Defaults to 2.0.
 */
data class FadingHologramOptions(
    var fadeSpeed: Double = 0.05,
    var fadeMaxOffset: Double = 2.0
) : HologramOptions

/**
 * Represents configuration options for a [dev.slne.surf.hologram.api.hologram.TooltipHologram].
 *
 * Exactly one of [targetEntityId] or [targetLocation] should be set.
 *
 * @property targetEntityId The raw entity ID of the target entity. Use for packet-based/fake entities.
 * @property targetLocation The block position of the target block.
 * @property lookAngleThreshold Half-angle (degrees) of the aiming cone. Defaults to 5.0.
 * @property maxDistance Maximum trigger distance in blocks. Defaults to 3.0.
 */
data class TooltipHologramOptions(
    var targetEntityId: Int? = null,
    var targetLocation: dev.slne.surf.hologram.api.hologram.location.HologramLocation? = null,
    var lookAngleThreshold: Double = 5.0,
    var maxDistance: Double = 3.0
) : HologramOptions
