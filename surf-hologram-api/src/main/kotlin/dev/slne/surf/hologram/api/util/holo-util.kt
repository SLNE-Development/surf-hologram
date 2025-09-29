package dev.slne.surf.hologram.api.util

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.player.User
import dev.slne.surf.hologram.api.event.HologramEvent
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramEventHandler
import dev.slne.surf.hologram.api.player.HoloPlayer
import org.bukkit.entity.Player

/**
 * Iterates over each Bukkit player viewer of the hologram and performs the given action on it.
 *
 * @param action The action to perform on each Bukkit player viewer.
 */
fun Hologram.forEachBukkitViewer(action: (Player) -> Unit) =
    retrieveViewers().mapNotNull { it.bukkitPlayer }.forEach { action(it) }

/**
 * Iterates over each packet viewer associated with the hologram and performs the specified action on it.
 *
 * @param action A lambda function to be invoked with each packet viewer as a User.
 */
fun Hologram.forEachPacketViewer(action: (User) -> Unit) =
    retrieveViewers().mapNotNull { it.bukkitPlayer }
        .mapNotNull { PacketEvents.getAPI().playerManager.getUser(it) }.forEach { action(it) }

/**
 * Iterates through each viewer of this hologram and performs the given action on them.
 *
 * @param action A lambda function that will be invoked for each viewer of the hologram.
 * The parameter of the lambda is a `HoloPlayer` representing each individual viewer.
 */
fun Hologram.forEachViewer(action: (HoloPlayer) -> Unit) =
    retrieveViewers().forEach { action(it) }

/**
 * Displays the hologram to all currently registered viewers.
 *
 * This method iterates through the list of viewers and ensures that each
 * of them can see the hologram.
 */
fun Hologram.show() = forEachViewer { it.showHologram(this) }

/**
 * Hides the hologram for all viewers who are currently observing it.
 *
 * This method iterates through all viewers of the hologram and calls the
 * `hideHologram` function for each viewer to ensure the hologram is
 * no longer visible to them.
 */
fun Hologram.hide() = forEachViewer { it.hideHologram(this) }

/**
 * Registers an event handler for a specific type of hologram event.
 *
 * This function allows the caller to specify a handler for events of type [T].
 * The registered handler will be invoked whenever an event of the specified type occurs
 * for the associated hologram.
 *
 * @param T The type of the hologram event to handle. This must be a subtype of [HologramEvent].
 * @param handler The event handler function to be invoked when the event of type [T] is triggered.
 */
inline fun <reified T : HologramEvent> Hologram.addEventHandler(noinline handler: HologramEventHandler<T>) {
    this.addEventHandler(T::class, handler)
}