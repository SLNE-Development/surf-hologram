package dev.slne.surf.hologram.api.hologram

import dev.slne.surf.hologram.api.event.HologramEvent
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import kotlin.reflect.KClass

/**
 * Defines a functional type alias for handling hologram-related events.
 *
 * The `HologramEventHandler` type represents a callback function that processes
 * hologram events of a specified type `T`. This handler can be utilized in
 * various contexts, such as responding to hologram updates, state changes,
 * or user interactions with holograms.
 *
 * @param T The type of the event associated with the hologram.
 */
typealias HologramEventHandler<T> = (T) -> Unit

/**
 * Represents a hologram entity in a Minecraft-like world.
 *
 * A hologram is a visual construct displayed in the game, capable of rendering
 * textual or component-based content. It supports various interactions, including
 * player visibility, event handling, and spatial manipulation.
 * Implementations of this interface allow for the creation, management, and
 * customization of holographic displays in the game world.
 */
interface Hologram {
    /**
     * Provides metadata associated with the hologram.
     *
     * This property contains additional information about the hologram, including data
     * necessary for its configuration or behavior. The metadata may include details such
     * as identifiers, state, or other attributes that define or supplement the hologram's
     * runtime characteristics.
     */
    val metaData: HologramMetaData

    /**
     * Defines the orientation type of the hologram.
     *
     * This property determines how the hologram behaves or moves within the 3D space.
     * It utilizes the `HologramOrientationType` enum to specify the desired orientation style,
     * such as fixed, bouncing, or rotating. The selected orientation affects the visual
     * presentation and dynamic behavior of the hologram in the game world.
     */
    var hologramOrientationType: HologramOrientationType

    /**
     * Specifies the central location of the hologram in the Minecraft world.
     *
     * This property determines the primary position where the hologram is displayed.
     * It is represented as a `HologramLocation` object, encapsulating the X, Y, Z
     * coordinates and the world associated with the hologram.
     *
     * The `centerLocation` is critical for placing the hologram in 3D space and serves
     * as a reference point for calculations involving hologram movement, display,
     * or interaction areas.
     *
     * Modifying this variable dynamically adjusts the hologram's position in the world.
     */
    var centerLocation: HologramLocation

    /**
     * Represents the textual or visual content displayed by the hologram.
     *
     * This variable defines the main component that is rendered and visible as part
     * of the hologram's display. It can represent text or other structured visual
     * elements, depending on the implementation of the `Component` used.
     *
     * Modifying this property dynamically updates the appearance of the hologram
     * for viewers, reflecting the changes immediately.
     */
    var displayedText: Component

    /**
     * Represents the hitbox associated with the hologram.
     *
     * The `hitbox` defines the spatial boundaries and center offset for interactions
     * or collisions with the hologram. It can be used to detect interaction events
     * or manage spatial properties relative to the hologram's defined location.
     *
     * This property may be null, indicating that the hologram does not have a defined
     * hitbox (e.g., it may not be interactive or require collision detection).
     */
    val hitbox: HologramHitbox?

    /**
     * A collection of offline players currently viewing the hologram.
     *
     * This property tracks all `HoloOfflinePlayer` instances that have the hologram
     * rendered within their client view. The `viewers` set is dynamically updated
     * as players enter or leave the hologram's viewing range. It can be used to
     * manage player-specific interactions or visibility related to the hologram.
     *
     * If the value is `null`, no viewers are currently associated with the hologram.
     */
    val viewers: ObjectSet<HoloOfflinePlayer>?

    /**
     * Retrieves the set of players currently viewing the hologram.
     *
     * This method returns all `HoloPlayer` instances who are actively viewing
     * the hologram. The viewers are dynamically tracked and updated as players
     * interact with the hologram, making this method useful for managing
     * hologram visibility or interaction logic.
     *
     * @return A set of `HoloPlayer` objects representing the current viewers of the hologram.
     */
    fun retrieveViewers(): ObjectSet<HoloPlayer>

    /**
     * Displays the hologram to the specified player.
     *
     * This method makes the hologram visible to the given `HoloPlayer`, allowing them to see it within their game environment.
     * It is typically used to dynamically adjust which players can view the hologram.
     *
     * @param player The `HoloPlayer` to whom the hologram will be displayed.
     */
    fun show(player: HoloPlayer)

    /**
     * Hides the hologram from the specified player.
     *
     * This method removes the hologram from the player's view, making it invisible
     * to the player while keeping it intact for others who can still view it. Use
     * this method when specific players should no longer interact with or see the hologram.
     *
     * @param player The player from whom the hologram should be hidden.
     */
    fun hide(player: HoloPlayer)

    /**
     * Refreshes the current state of the hologram.
     *
     * This method updates the hologram to reflect any recent changes to its
     * properties, such as text, orientation, or metadata. It ensures that all
     * viewers receive the latest version of the hologram as it is intended
     * to be displayed.
     *
     * Note that the refresh process does not involve creating a new hologram
     * instance or modifying individual player interactions directly, but rather
     * recalibrates the hologram's state for consistency across all viewers.
     */
    fun refresh()

    /**
     * Updates the content of the hologram to reflect changes.
     *
     * This method ensures that the hologram's displayed content, such as text or relevant
     * metadata, is up-to-date and synchronized with any recent modifications. It is typically
     * used when the internal state of the hologram has changed and those updates need
     * to be visually or interactively applied for the viewers.
     *
     * Unlike a complete refresh or reinitialization, `refreshContent` focuses specifically on
     * updating the hologram's content rather than recalibrating its entire state or properties.
     */
    fun refreshContent()

    /**
     * Performs a clean refresh of the hologram's state.
     *
     * This method resets and reinitializes the hologram while ensuring its data
     * and components are entirely reloaded. Unlike a standard refresh operation,
     * `refreshClean` guarantees the removal of residual or outdated states,
     * providing a fully reinitialized hologram instance for improved consistency
     * and performance.
     */
    fun refreshClean()

    /**
     * Creates a duplicate of the hologram with an option to instantly spawn it.
     *
     * @param spawnable Determines whether the duplicated hologram should be spawnable
     *                  immediately upon creation. Defaults to `false`, indicating
     *                  that the duplicate will not spawn automatically.
     * @return A new `Hologram` instance representing the duplicated hologram.
     */
    fun duplicate(spawnable: Boolean = false): Hologram

    /**
     * Teleports the specified player to this hologram's location.
     *
     * This method is useful for moving a `HoloPlayer` entity directly to the position
     * associated with the hologram, ensuring they are effectively aligned or interacting
     * with it. The teleportation process may respect restrictions or conditions imposed
     * by the implementation.
     *
     * @param player The `HoloPlayer` to be teleported to the hologram's location.
     * @return `true` if the teleportation was successful, otherwise `false` if it failed (e.g., due to restrictions).
     */
    fun teleportHere(player: HoloPlayer): Boolean

    /**
     * Teleports the hologram to a new location.
     *
     * Moves the hologram to the specified `HologramLocation`. If `save` is set to `true`,
     * the operation persists the new location for future reference. This method is typically
     * used for dynamically repositioning holograms within the game world.
     *
     * @param newLocation The target location to which the hologram should be teleported.
     * @param save Indicates whether the new location should be saved persistently.
     *             Defaults to `true`.
     */
    fun teleportTo(newLocation: HologramLocation, save: Boolean = true)

    /**
     * Registers an event handler for a specific hologram event type.
     *
     * This method allows you to specify a handler for a given hologram event class. The handler will be invoked
     * whenever the corresponding event occurs, enabling custom behavior or interaction logic for the hologram.
     *
     * @param T The type of the hologram event.
     * @param eventClass The class of the event type for which the handler should be registered.
     * @param handler The event handler to be invoked when the specified event type is triggered.
     */
    fun <T : HologramEvent> addEventHandler(eventClass: KClass<T>, handler: HologramEventHandler<T>)

    /**
     * Removes a specific event handler for a given hologram event type.
     *
     * This method unregisters the provided handler for the specified event class,
     * ensuring that the handler will no longer be invoked when events of that type
     * occur. It is useful for dynamically detaching behavior associated with
     * hologram events.
     *
     * @param T the type of the hologram event
     * @param eventClass the class of the event type from which the handler should be removed
     * @param handler the event handler to be removed for the specified event type
     */
    fun <T : HologramEvent> removeEventHandler(
        eventClass: KClass<T>,
        handler: HologramEventHandler<T>
    )

    /**
     * Invokes all handlers associated with the specified hologram event.
     *
     * This method triggers the execution of any event handlers that have been registered
     * for the type of the given event. It is typically used to propagate an event
     * to all interested handlers, allowing them to react accordingly.
     *
     * @param event The hologram event that should be processed by the associated handlers.
     * The event must extend the `HologramEvent` interface and represents the state or
     * action to be handled.
     */
    fun <T : HologramEvent> callHandlers(event: T)
}