@file:Suppress("UNCHECKED_CAST")

package dev.slne.surf.hologram.api.dsl

import com.github.retrooper.packetevents.util.Vector3d
import dev.slne.surf.hologram.api.event.HologramEvent
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.hologram.util.HologramTextAlignment
import dev.slne.surf.hologram.api.hologramConversationUtil
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.surfHologramApi
import dev.slne.surf.surfapi.core.api.messages.builder.SurfComponentBuilder
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import it.unimi.dsi.fastutil.objects.ObjectList
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.format.TextColor
import kotlin.reflect.KClass

/**
 * A builder class to create and configure holograms using a DSL (Domain-Specific Language).
 * It provides configuration options for text alignment, view range, viewers, hitboxes, and event handling.
 *
 * @param O The type of `HologramOptions` associated with this builder.
 * @param name The unique name of the hologram to be created.
 * @param hologramOrientationType The orientation behavior of the hologram.
 * @param centerLocation The location in the world where the hologram will be centered.
 * @param optionsClazz A `Class` object representing the type of hologram options used in configuration.
 */
class HologramDslBuilder<O : HologramOptions>(
    val name: String,
    val hologramOrientationType: HologramOrientationType,
    val centerLocation: HologramLocation,
    private val optionsClazz: Class<O>
) {
    /**
     * Lambda function used to define and customize the displayed text for a hologram.
     *
     * This property allows for the configuration of multiple lines and styles of text
     * using a `SurfComponentBuilder` DSL (Domain-Specific Language) block. It enables
     * customization of the text content and its structure, such as adding styled text,
     * new lines, and various formatting options.
     *
     * The configured text will be displayed in the hologram when rendered.
     */
    lateinit var displayedText: SurfComponentBuilder.() -> Unit

    /**
     * Represents a set of players who are permitted to view the hologram.
     *
     * This property holds a collection of `HoloOfflinePlayer` instances that define the viewers
     * of the hologram. It may be `null` if no viewers have been added or configured.
     *
     * Viewers can be managed through methods like `withViewer` and `withViewers`, which facilitate
     * the addition of individual players or multiple players, respectively. When initializing
     * or modifying the viewer set, the collection will be instantiated if it is null.
     */
    var viewers: ObjectSet<HoloOfflinePlayer>? = null

    /**
     * Represents the hitbox configuration for the hologram.
     *
     * This variable holds an instance of `HologramHitbox`, which defines the spatial boundaries
     * and interaction zone for the hologram. The hitbox encompasses properties such as width,
     * height, and an offset from the hologram's center, providing the dimensions for collision
     * detection or interaction handling in 3D space.
     *
     * By default, this value is null, meaning no hitbox is assigned until explicitly configured.
     * Use the `hitbox` DSL function to define the dimensions and center offset for the hologram,
     * allowing precise customization of the interaction region.
     */
    var hitbox: HologramHitbox? = null

    /**
     * Defines the maximum viewing distance for the hologram.
     *
     * This property specifies the range, in units, within which the hologram
     * will be visible to players. Adjusting this value allows customization
     * of the hologram's visibility radius, affecting its performance and
     * rendering behavior. A larger value increases visibility but may also
     * impact performance, while a smaller value restricts visibility
     * to a closer range.
     */
    var viewRange: Float = 50f

    /**
     * Represents the maximum allowable width for a single line of text in the hologram display.
     *
     * This property defines the horizontal constraint for text rendering within the hologram. If the text
     * exceeds this width, it will either wrap to the next line or be truncated, depending on the configuration
     * or implementation of the display mechanism.
     *
     * The default value is set to 200, which acts as a reasonable constraint for most use cases. However,
     * it can be adjusted to suit specific layout or design requirements.
     */
    var lineWidth: Int = 200

    /**
     * The `textAlignment` property determines the alignment of the text displayed in the hologram.
     *
     * This property specifies how the text content is aligned within its display area, providing options to
     * align it to the center, left, or right. The default value is `HologramTextAlignment.CENTER`, ensuring that
     * the text is centered by default. However, this can be adjusted to better suit specific hologram designs or
     * use cases.
     *
     * Value options are defined by the `HologramTextAlignment` enumeration:
     * - `CENTER`: Aligns the text to the center.
     * - `LEFT`: Aligns the text to the left.
     * - `RIGHT`: Aligns the text to the right.
     */
    var textAlignment: HologramTextAlignment = HologramTextAlignment.CENTER

    /**
     * Represents the background color of the hologram text.
     *
     * This property allows setting a color for the background of the displayed text in the hologram.
     * If set to null, the text will have no background color, leaving it transparent or default depending
     * on the implementation.
     *
     * The color, if specified, should be of type `TextColor`, which provides customization for the desired
     * appearance of the background.
     */
    var backgroundColor: TextColor? = null

    /**
     * Represents the configurable options for a hologram. These options are specific to the type of
     * hologram being created or modified.
     *
     * This property is nullable and initially set to `null`. It is privately set and can only be
     * modified through the DSL `options(block: O.() -> Unit)` function. The configured options must
     * implement the `HologramOptions` interface.
     *
     * The options play a crucial role in defining the behavior and additional properties of the hologram.
     * They are required during the final creation of the hologram, and an error is thrown if the options
     * are not configured before finalizing the hologram instance.
     *
     * @see HologramOptions
     * @see HologramDslBuilder.options
     */
    var options: O? = null
        private set

    /**
     * Configures the options for the hologram using a DSL (Domain-Specific Language) block.
     *
     * This method allows customization of the specific behavior and properties of the hologram
     * by setting options that are specific to its implementation. The provided DSL block
     * is applied to an instance of the options class, enabling in-depth configuration.
     *
     * @param block A lambda with receiver of type `O` (which extends `HologramOptions`) used to define
     *              and configure the unique options for the hologram.
     */
    fun options(block: O.() -> Unit) {
        val instance = optionsClazz.getDeclaredConstructor().newInstance()
        instance.apply(block)
        options = instance
    }

    /**
     * A map storing event handlers for hologram events.
     *
     * This property is used to associate specific event types, represented as subclasses of `HologramEvent`,
     * with their respective handlers. Each event type maps to a collection of lambda functions that process
     * the corresponding event when triggered.
     *
     * The map's keys are `KClass` objects representing the event type, and the values are `ObjectList` collections
     * containing functions that take a `HologramEvent` instance as input and execute the defined logic for
     * handling the event.
     *
     * This enables dynamic registration and lookup of event handlers for different hologram event types
     * within the DSL context of building a hologram.
     */
    internal val eventHandlers =
        mutableMapOf<KClass<out HologramEvent>, ObjectList<(HologramEvent) -> Unit>>()

    /**
     * Registers an event handler for a specific type of hologram event.
     *
     * This function allows the association of a handler function to a particular hologram event type.
     * When the specified event type occurs, the provided handler function will be invoked with
     * the event instance as its parameter.
     *
     * @param T The type of the hologram event this handler is designed to process. Must be a subclass of `HologramEvent`.
     * @param eventClass The runtime class of the hologram event to handle.
     * @param handler A lambda function that processes the event. The function receives the event instance of type `T` as an argument.
     */
    fun <T : HologramEvent> withEventHandler(eventClass: KClass<T>, handler: (T) -> Unit) {
        eventHandlers.computeIfAbsent(eventClass) { mutableObjectListOf() }
            .add { ev -> handler(ev as T) }
    }

    /**
     * Registers an event handler for a specific type of hologram event.
     *
     * This function allows setting up event listeners for hologram-related actions
     * using a type-safe approach. The handler is invoked whenever an event of the specified
     * type occurs, providing the event data through the lambda parameter.
     *
     * @param T The type of hologram event to handle. Must extend [HologramEvent].
     * @param handler A lambda function to be executed when the event of type [T] is triggered.
     *                The event instance is passed as the argument to the lambda.
     */
    inline fun <reified T : HologramEvent> withEventHandler(noinline handler: (T) -> Unit) {
        withEventHandler(T::class, handler)
    }

    /**
     * Sets the displayed text for a hologram using a DSL (Domain-Specific Language) builder.
     *
     * This function allows the definition of multiple lines and styles of text
     * that will be displayed in the hologram. The `SurfComponentBuilder` block
     * provides methods to customize the text content, such as adding styled text,
     * appending new lines, and more.
     *
     * @param block A lambda with receiver of type `SurfComponentBuilder` used to define
     *              the text content and structure of the hologram's display.
     */
    fun displayedText(block: SurfComponentBuilder.() -> Unit) {
        displayedText = block
    }

    /**
     * Adds a viewer to the hologram, allowing the given player to view it.
     *
     * This method ensures that the viewer list is initialized if it is not already,
     * and appends the specified `HoloOfflinePlayer` instance to the list of viewers.
     *
     * @param viewer An instance of `HoloOfflinePlayer` representing the player to be added as a viewer.
     */
    fun withViewer(viewer: HoloOfflinePlayer) {
        if (viewers == null) {
            viewers = mutableObjectSetOf()
        }

        viewers?.add(viewer)
    }

    /**
     * Configures a set of viewers for the hologram using the `ViewersDsl` block.
     *
     * This function allows the addition of multiple viewers, represented as `HoloOfflinePlayer` instances,
     * to the hologram's viewer list. The `ViewersDsl` block provides methods to add individual viewers
     * or a collection of viewers.
     *
     * @param block A lambda with receiver of type `ViewersDsl` used to define the set of viewers
     *              that can view the hologram. Within the block, viewers can be added using the DSL
     *              methods provided by `ViewersDsl`.
     */
    fun withViewers(block: ViewersDsl.() -> Unit) {
        val dsl = ViewersDsl().apply(block)

        if (viewers == null) {
            viewers = mutableObjectSetOf()
        }
        viewers?.addAll(dsl.viewerSet)
    }

    /**
     * Provides a Domain-Specific Language (DSL) for configuring viewers of a hologram.
     *
     * This class enables the definition and management of a set of viewers, where viewers
     * are represented by `HoloOfflinePlayer` instances. It allows adding individual or
     * multiple viewers to the set to control who can view a specific hologram.
     */
    class ViewersDsl {
        /**
         * A mutable set used to store instances of `HoloOfflinePlayer` representing viewers.
         *
         * This set is primarily utilized within the `ViewersDsl` class to manage a collection
         * of viewers that can interact with or observe a hologram. It provides operations
         * such as adding individual viewers or collections of viewers, which are later
         * applied to define the group of players associated with a hologram.
         *
         * The `viewerSet` is initialized as an empty set and can be modified using the
         * DSL methods provided in the `ViewersDsl` class.
         */
        internal val viewerSet = mutableObjectSetOf<HoloOfflinePlayer>()

        /**
         * Adds the specified HoloOfflinePlayer to the viewer set.
         *
         * @param v the HoloOfflinePlayer to be added to the viewer set
         */
        fun viewer(v: HoloOfflinePlayer) {
            viewerSet.add(v)
        }

        /**
         * Adds a collection of HoloOfflinePlayer instances to the current viewer set.
         *
         * @param vList the collection of HoloOfflinePlayer instances to be added as viewers
         */
        fun viewers(vList: Collection<HoloOfflinePlayer>) {
            viewerSet.addAll(vList)
        }
    }

    /**
     * A DSL (Domain-Specific Language) class used for configuring the hitbox of a hologram.
     *
     * This class allows the customization of the hologram's hitbox by defining its width,
     * height, and an optional offset for its center in 3D space. The `HitboxDsl` is typically used
     * in conjunction with other hologram configuration methods to fine-tune interactive boundaries.
     */
    class HitboxDsl {
        /**
         * Defines the width of the hologram's hitbox in the DSL context.
         *
         * This property specifies the horizontal dimension of the hitbox, which is
         * utilized when configuring the spatial boundaries for the hologram. It plays
         * a crucial role in determining the hologram's interaction and collision zone
         * within the hitbox system.
         */
        var width: Float = 1.0f

        /**
         * Represents the vertical dimension of the hologram's hitbox.
         *
         * This property is part of the hitbox configuration in the `HitboxDsl` context and defines
         * the height of the hologram's interaction boundary. It is used when constructing the hologram's
         * spatial attributes for collision detection or interaction handling.
         */
        var height: Float = 1.0f

        /**
         * Represents the positional offset from the center of a hologram's hitbox in 3D space.
         *
         * This property defines a `Vector3d` that adjusts the center of the hologram's hitbox,
         * allowing precise customization of its position within the hologram's spatial configuration.
         * It is particularly useful for aligning or modifying interaction boundaries relative to
         * the default center point defined by the hologram's dimensions.
         */
        var hologramCenterOffset: Vector3d = Vector3d(0.0, 0.0, 0.0)

        /**
         * Builds and returns a new instance of the HologramHitbox.
         *
         * This function utilizes the properties of the HitboxDsl configuration
         * context (width, height, and hologramCenterOffset) to construct the hitbox's
         * spatial attributes and interaction boundaries.
         *
         * @return A new HologramHitbox instance initialized with width, height,
         *         and hologramCenterOffset from the current HitboxDsl configuration.
         */
        fun build(): HologramHitbox {
            return object : HologramHitbox {
                /**
                 * Represents the horizontal dimension of the hologram's hitbox within the current implementation
                 * of the Hitbox DSL's configuration context.
                 *
                 * This property is derived from the HitboxDsl context and defines the width of the hitbox.
                 * It is used when constructing the hologram's spatial boundaries and interactions by providing
                 * the base width value for the hologram's hitbox.
                 */
                override val width = this@HitboxDsl.width

                /**
                 * Represents the height of the hologram's hitbox.
                 *
                 * This property defines the vertical dimension of the hologram's interaction boundary,
                 * inherited from the `HitboxDsl` context. It is used to configure and determine
                 * the spatial attributes utilized by the hologram for collisions or interaction handling.
                 */
                override val height = this@HitboxDsl.height

                /**
                 * Represents the offset from the center of the hologram's hitbox in 3D space.
                 *
                 * This property is a vector defining the positional adjustment applied to the
                 * center of the hologram's hitbox, allowing precise tuning of its position. It is used
                 * in hologram configuration to customize interaction boundaries or alignments
                 * relative to its central point.
                 */
                override val hologramCenterOffset = this@HitboxDsl.hologramCenterOffset
            }
        }
    }

    /**
     * Configures the hitbox for the hologram using a DSL (Domain-Specific Language).
     *
     * This method allows to define the dimensions and center offset of the hologram's interaction boundary
     * using the `HitboxDsl` configuration block. The hitbox defines the spatial region where the hologram
     * can be interacted with, such as through clicks or other interactions.
     *
     * @param block A lambda with receiver of type `HitboxDsl` used to configure the width, height,
     *              and center offset of the hologram's hitbox.
     */
    fun hitbox(block: HitboxDsl.() -> Unit) {
        val dsl = HitboxDsl().apply(block)
        hitbox = dsl.build()
    }
}

/**
 * Creates and registers a hologram in the system based on the specified parameters and configuration block.
 *
 * The hologram can have varying properties such as orientation, location, displayed text, and additional options
 * specific to the hologram type. Event handlers can also be added for different hologram events.
 *
 * @param name The unique name of the hologram.
 * @param hologramClazz The class type of the hologram being created.
 * @param orientationType The orientation type of the hologram, such as FIXED, BOUNCING, or ROTATING.
 * @param location The location of the hologram in the world.
 * @param block A DSL block used to configure the hologram's additional settings and display properties.
 * @return The created hologram object.
 */
fun <H : Hologram, O : HologramOptions> hologram(
    name: String,
    hologramClazz: Class<H>,
    orientationType: HologramOrientationType,
    location: HologramLocation,
    block: HologramDslBuilder<O>.() -> Unit
): H {
    val type = surfHologramApi.getHologramType<H, O>(hologramClazz)
        ?: error("Hologram type for class ${hologramClazz.name} is not registered!")
    val builder =
        HologramDslBuilder(name, orientationType, location, type.optionsClazz).apply(block)
    val metaData = hologramConversationUtil.createHologramMeta(
        name = builder.name,
        viewRange = builder.viewRange,
        lineWidth = builder.lineWidth,
        textAlignment = builder.textAlignment,
        backgroundColor = builder.backgroundColor
    )

    val options = builder.options ?: error("Options must be provided for hologram type ${type.id}")
    val hologram = type.create(
        metaData,
        orientationType,
        builder.centerLocation,
        SurfComponentBuilder.builder().apply(builder.displayedText).build(),
        builder.hitbox,
        builder.viewers,
        options
    )

    surfHologramApi.registerHologram(hologram)

    builder.eventHandlers.forEach { (eventClass, handlersList) ->
        handlersList.forEach { handler ->
            hologram.addEventHandler(eventClass as KClass<HologramEvent>) { ev -> handler(ev) }
        }
    }
    return hologram
}
