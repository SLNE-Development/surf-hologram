@file:Suppress("UNCHECKED_CAST")

package dev.slne.surf.hologram.api.dsl

import dev.slne.surf.hologram.api.event.HologramEvent
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramTextAlignment
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.surfHologramApi
import dev.slne.surf.surfapi.core.api.messages.builder.SurfComponentBuilder
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import it.unimi.dsi.fastutil.objects.ObjectList
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.format.TextColor
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

/**
 * Builder for creating and configuring holograms using a declarative DSL approach.
 *
 * This class provides a set of properties and functions to configure hologram properties,
 * such as text, viewers, interactions, and event handlers. It supports multiple hologram
 * types and customizations, allowing for detailed and dynamic hologram creation.
 *
 * @param name The name of the hologram.
 * @param hologramType The type of the hologram, determined by [HologramType].
 * @param centerLocation The central location of the hologram in the world, represented by [HologramLocation].
 */
class HologramDslBuilder(
    val name: String,
    val hologramType: HologramType,
    val centerLocation: HologramLocation
) {
    /**
     * Configures the text content to be displayed on the hologram.
     *
     * This property is a lambda with a [SurfComponentBuilder] receiver that allows the user
     * to define and customize the text content of the hologram using a DSL-like structure.
     * The lambda is executed and applied during the hologram creation process.
     */
    lateinit var displayedText: SurfComponentBuilder.() -> Unit

    /**
     * Represents the collection of viewers associated with the hologram.
     *
     * This property holds a set of [HoloOfflinePlayer] objects who are able to view the hologram.
     * It is nullable and will be initialized when a viewer is added through the associated builder methods.
     * If no viewers are explicitly added, this property remains `null`.
     *
     * The viewers can be configured programmatically using `withViewer` and `withViewers` methods,
     * which allow adding individual viewers or configuring multiple viewers through a DSL block.
     */
    var viewers: ObjectSet<HoloOfflinePlayer>? = null

    /**
     * Specifies the height at which a hologram of type [HologramType.BOUNCING] will oscillate.
     *
     * This property defines the vertical displacement range for the bouncing animation of the hologram.
     * A value of `null` or `0.0` disables the bouncing effect, effectively making the hologram stationary vertically.
     * This property is only applicable to holograms configured with [HologramType.BOUNCING].
     *
     * Its value can be customized during the creation of a hologram using the DSL block or modified afterwards.
     *
     * Default value: `1.0`.
     */
    var bouncingHeight: Double? = 1.0

    /**
     * Defines the incremental step used to determine the movement of a bouncing hologram.
     *
     * This variable represents the amount by which the hologram's position changes vertically
     * during each update cycle in a "BOUNCING" hologram type. A higher value results in more
     * noticeable vertical movement, while a lower value creates smoother or subtler bounce effects.
     *
     * A value of `null` indicates no step is defined, and default behavior may apply, depending
     * on the implementation of the bouncing mechanism.
     *
     * This property is typically used to configure dynamic holograms to provide visual feedback
     * or aesthetic effects.
     */
    var bouncingStep: Double? = 0.1

    /**
     * Defines the interaction width of the hologram.
     *
     * This property specifies the horizontal range within which interactions
     * with the hologram can occur. It influences the distance at which
     * users can interact with the hologram in the virtual space.
     * The default value is `1f`.
     */
    var interactionWidth: Float = 1f

    /**
     * Specifies the height of the interaction area for the hologram.
     *
     * This variable determines the vertical range in which player interactions
     * with the hologram (e.g., clicks) will be detected. A higher value expands
     * the interaction zone upwards, while a lower value reduces it.
     *
     * The default value is `1f`, which represents a height of one block in-game.
     */
    var interactionHeight: Float = 1f

    /**
     * Defines the maximum distance, in blocks, from which the hologram will be visible.
     *
     * This property determines the visibility range of the hologram. Players outside this
     * range will not see the hologram displayed in the world. Adjusting this value allows
     * fine-tuning the hologram's visibility scope based on the desired experience.
     *
     * By default, the visibility range is set to 50 blocks.
     */
    var viewRange: Float = 50f

    /**
     * Defines the maximum width of a line in the hologram's text display.
     *
     * This property controls how text within the hologram is wrapped. When the text
     * exceeds the specified width, it will be automatically wrapped to the next line.
     * The value is measured in pixels and is primarily used for customizing the visual
     * appearance and alignment of hologram content.
     */
    var lineWidth: Int = 200

    /**
     * Defines the alignment of the text displayed in the hologram.
     *
     * This property allows customization of how the text is positioned
     * within the hologram's display area. The alignment can be set to
     * one of the predefined values in the [HologramTextAlignment] enum:
     * [HologramTextAlignment.CENTER], [HologramTextAlignment.LEFT], or
     * [HologramTextAlignment.RIGHT].
     *
     * The default alignment is [HologramTextAlignment.CENTER].
     */
    var textAlignment: HologramTextAlignment = HologramTextAlignment.CENTER

    /**
     * Specifies the background color of the hologram.
     *
     * This property allows setting an optional [TextColor] that determines the hologram's background
     * appearance. If `null`, the hologram will not display any background color. The [backgroundColor]
     * can be used to enhance visual clarity or match a specific design requirement.
     */
    var backgroundColor: TextColor? = null

    /**
     * Determines whether the hologram is clickable, enabling interaction handling.
     *
     * When set to `true`, the hologram can handle click-related events, allowing developers to define
     * event handlers using methods such as `withEventHandler` to process user interactions.
     * If set to `false`, the hologram will not respond to click interactions.
     *
     * This property is typically used when configuring the hologram through a DSL or builder
     * to customize its behavior according to specific requirements.
     */
    var clickable: Boolean = false

    /**
     * A mutable map used to store associations between hologram event classes and their respective handlers.
     *
     * This map is part of the DSL for configuring holograms, enabling the registration of handlers for
     * different types of events that can occur on a hologram. Each event class key is associated with a list
     * of handler functions that respond to events of the corresponding type.
     *
     * The keys of this map are Kotlin class types representing subtypes of [HologramEvent].
     * The values are lists of handler functions, where each function is designed to
     * process a specific event type. These handlers are invoked internally by the hologram system
     * whenever an event of the registered type is triggered.
     */
    internal val eventHandlers =
        mutableMapOf<KClass<out HologramEvent>, ObjectList<(HologramEvent) -> Unit>>()

    /**
     * Registers an event handler for a specific type of hologram event.
     *
     * This method allows you to define and attach a handler function to a specified event type.
     * When an event of the specified type is triggered, the provided handler will be executed.
     *
     * @param T The type of the event, which must extend [HologramEvent].
     * @param eventClass The class of the event to handle.
     * @param handler The lambda function to be executed when the event occurs,
     * where the input parameter is the event instance.
     */
    fun <T : HologramEvent> withEventHandler(eventClass: KClass<T>, handler: (T) -> Unit) {
        eventHandlers.computeIfAbsent(eventClass) { mutableObjectListOf() }
            .add { ev -> handler(ev as T) }
    }

    /**
     * Adds an event handler for a specific type of hologram event.
     *
     * This method simplifies the process of registering an event handler by inferring
     * the event type from the generic parameter. It internally delegates to the
     * implementation that requires specifying the event class explicitly.
     *
     * @param T The type of the hologram event to handle. Must be a subclass of [HologramEvent].
     * @param handler A lambda function that processes events of type `T`.
     */
    inline fun <reified T : HologramEvent> withEventHandler(noinline handler: (T) -> Unit) {
        withEventHandler(T::class, handler)
    }

    /**
     * Configures the text to be displayed on the hologram using a provided DSL block.
     *
     * This function allows defining the displayed text of the hologram through
     * the [SurfComponentBuilder] DSL, enabling customization of the visual content.
     *
     * @param block A lambda with a [SurfComponentBuilder] receiver used to define the displayed text.
     */
    fun displayedText(block: SurfComponentBuilder.() -> Unit) {
        displayedText = block
    }

    /**
     * Adds the specified viewer to the hologram's viewer set.
     *
     * This function ensures that if the viewer set is not initialized, it will be created before adding the viewer.
     *
     * @param viewer The [HoloOfflinePlayer] to be added to the hologram's viewer list.
     */
    fun withViewer(viewer: HoloOfflinePlayer) {
        if (viewers == null) {
            viewers = mutableObjectSetOf()
        }

        viewers?.add(viewer)
    }

    /**
     * Configures the viewers for the hologram using the provided DSL block.
     *
     * This function allows adding multiple viewers by utilizing the [ViewersDsl] class.
     * The viewers specified in the DSL block are added to the `viewers` collection of the hologram.
     *
     * @param block A lambda block with a [ViewersDsl] receiver used to define the viewers.
     */
    fun withViewers(block: ViewersDsl.() -> Unit) {
        val dsl = ViewersDsl().apply(block)

        if (viewers == null) {
            viewers = mutableObjectSetOf()
        }
        viewers?.addAll(dsl.viewerSet)
    }

    /**
     * A DSL class for configuring the viewers of a hologram.
     *
     * This class allows the addition of individual viewers or collections of viewers
     * to be associated with a hologram. The viewers are represented as `HoloOfflinePlayer`
     * instances.
     */
    class ViewersDsl {
        /**
         * A mutable set that holds instances of [HoloOfflinePlayer], representing the collection
         * of players that can view the associated hologram.
         *
         * This set is primarily used within the DSL context of `ViewersDsl` to add or manage
         * hologram viewers. It serves as an internal storage for any [HoloOfflinePlayer] added
         * via the `viewer` or `viewers` functions in the DSL.
         */
        internal val viewerSet = mutableObjectSetOf<HoloOfflinePlayer>()

        /**
         * Adds the specified offline player to the internal set of viewers.
         *
         * @param v The offline player to be added to the viewer set.
         */
        fun viewer(v: HoloOfflinePlayer) {
            viewerSet.add(v)
        }

        /**
         * Adds a collection of HoloOfflinePlayer instances to the viewer set.
         *
         * @param vList the collection of HoloOfflinePlayer instances to be added to the viewer set
         */
        fun viewers(vList: Collection<HoloOfflinePlayer>) {
            viewerSet.addAll(vList)
        }
    }
}

/**
 * Creates a new hologram in the world with the specified configuration.
 *
 * The hologram is configured using the provided DSL builder lambda and is created
 * with the specified type, location, and additional metadata derived from the DSL.
 * This method supports adding event handlers and customizing the hologram's appearance
 * and behavior.
 *
 * @param plugin The Java plugin instance responsible for managing the hologram.
 * @param name The unique name of the hologram.
 * @param type The type of the hologram, which may dictate its behavior (e.g., FIXED, BOUNCING, ROTATING).
 * @param location The location in the world where the hologram will be displayed.
 * @param block A lambda expression with a receiver of type [HologramDslBuilder] to configure the hologram.
 * @return An instance of the created hologram.
 */
fun hologram(
    plugin: JavaPlugin,
    name: String,
    type: HologramType,
    location: HologramLocation,
    block: HologramDslBuilder.() -> Unit
): Hologram {
    val builder = HologramDslBuilder(name, type, location).apply(block)
    val metaData = surfHologramApi.buildMetaData(
        name = builder.name,
        interactionWidth = builder.interactionWidth,
        interactionHeight = builder.interactionHeight,
        viewRange = builder.viewRange,
        lineWidth = builder.lineWidth,
        textAlignment = builder.textAlignment,
        backgroundColor = builder.backgroundColor,
        clickable = builder.clickable
    )

    val hologram = surfHologramApi.createHologram(
        type = builder.hologramType,
        metaData = metaData,
        centerLocation = builder.centerLocation,
        displayedText = SurfComponentBuilder.builder().apply(builder.displayedText).build(),
        viewers = builder.viewers,
        bouncingHeight = builder.bouncingHeight,
        bouncingStep = builder.bouncingStep
    )

    builder.eventHandlers.forEach { (eventClass, handlersList) ->
        handlersList.forEach { handler ->
            hologram.addEventHandler(eventClass as KClass<HologramEvent>) { ev -> handler(ev) }
        }
    }
    return hologram
}
