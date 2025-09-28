@file:Suppress("UNCHECKED_CAST")

package dev.slne.surf.hologram.api.dsl

import com.github.retrooper.packetevents.util.Vector3d
import dev.slne.surf.hologram.api.event.HologramEvent
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramCreationReason
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
 * @param hologramOrientationType The type of the hologram, determined by [HologramOrientationType].
 * @param centerLocation The central location of the hologram in the world, represented by [HologramLocation].
 */
class HologramDslBuilder<O : Any>(
    val name: String,
    val hologramOrientationType: HologramOrientationType,
    val centerLocation: HologramLocation,
    private val optionsClazz: Class<O>
) {
    lateinit var displayedText: SurfComponentBuilder.() -> Unit

    var viewers: ObjectSet<HoloOfflinePlayer>? = null
    var hitbox: HologramHitbox? = null
    var viewRange: Float = 50f
    var lineWidth: Int = 200
    var textAlignment: HologramTextAlignment = HologramTextAlignment.CENTER
    var backgroundColor: TextColor? = null
    var options: O? = null
        private set

    fun options(block: O.() -> Unit) {
        val instance = optionsClazz.getDeclaredConstructor().newInstance()
        instance.apply(block)
        options = instance
    }

    internal val eventHandlers =
        mutableMapOf<KClass<out HologramEvent>, ObjectList<(HologramEvent) -> Unit>>()

    fun <T : HologramEvent> withEventHandler(eventClass: KClass<T>, handler: (T) -> Unit) {
        eventHandlers.computeIfAbsent(eventClass) { mutableObjectListOf() }
            .add { ev -> handler(ev as T) }
    }

    inline fun <reified T : HologramEvent> withEventHandler(noinline handler: (T) -> Unit) {
        withEventHandler(T::class, handler)
    }

    fun displayedText(block: SurfComponentBuilder.() -> Unit) {
        displayedText = block
    }

    fun withViewer(viewer: HoloOfflinePlayer) {
        if (viewers == null) {
            viewers = mutableObjectSetOf()
        }

        viewers?.add(viewer)
    }

    fun withViewers(block: ViewersDsl.() -> Unit) {
        val dsl = ViewersDsl().apply(block)

        if (viewers == null) {
            viewers = mutableObjectSetOf()
        }
        viewers?.addAll(dsl.viewerSet)
    }

    class ViewersDsl {
        internal val viewerSet = mutableObjectSetOf<HoloOfflinePlayer>()

        fun viewer(v: HoloOfflinePlayer) {
            viewerSet.add(v)
        }

        fun viewers(vList: Collection<HoloOfflinePlayer>) {
            viewerSet.addAll(vList)
        }
    }

    class HitboxDsl {
        var width: Float = 1.0f
        var height: Float = 1.0f
        var hologramCenterOffset: Vector3d = Vector3d(0.0, 0.0, 0.0)

        fun build(): HologramHitbox {
            return object : HologramHitbox {
                override val width = this@HitboxDsl.width
                override val height = this@HitboxDsl.height
                override val hologramCenterOffset = this@HitboxDsl.hologramCenterOffset
            }
        }
    }

    fun hitbox(block: HitboxDsl.() -> Unit) {
        val dsl = HitboxDsl().apply(block)
        hitbox = dsl.build()
    }
}

fun <H : Hologram, O : Any> hologram(
    plugin: JavaPlugin,
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
        HologramCreationReason.Plugin(plugin.name),
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
