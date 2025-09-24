package dev.slne.surf.hologram.api.event.impl

import dev.slne.surf.hologram.api.event.HologramPlayerEvent
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.player.HoloPlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class HologramCollisionEvent(
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