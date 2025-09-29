package dev.slne.surf.hologram.api.event.impl

import dev.slne.surf.hologram.api.event.HologramPlayerEvent
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.player.HoloPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Represents an event where a hologram is clicked by a player.
 *
 * This event is triggered when a player interacts with a hologram,
 * such as through a click or interaction action. The event provides access
 * to the player performing the action and the hologram being interacted with.
 *
 * The event can be utilized to handle custom logic in response to a player's
 * interaction with a specific hologram instance.
 *
 * This event implements the [HologramPlayerEvent] interface, which associates
 * a hologram event with an interacting player.
 *
 * This class is also an implementation of [org.bukkit.event.Event].
 */
class HologramClickEvent(
    override val player: HoloPlayer,
    override val hologram: Hologram
) : Event(), HologramPlayerEvent {
    /**
     * Returns the handler list for this event.
     *
     * @return The handler list.
     */
    override fun getHandlers(): HandlerList = handlerList

    companion object {
        /**
         * The static handler list for this event.
         */
        @JvmStatic
        val handlerList = HandlerList()
    }
}