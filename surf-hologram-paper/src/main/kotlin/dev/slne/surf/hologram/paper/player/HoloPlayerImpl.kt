package dev.slne.surf.hologram.paper.player

import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.player.HoloPlayer
import java.util.*

class HoloPlayerImpl(
    override val name: String,
    override val uuid: UUID
) : HoloPlayer {
    override val player: HoloPlayer get() = this

    override fun showHologram(hologram: Hologram) {
        hologram.show(this)
    }

    override fun hideHologram(hologram: Hologram) {
        hologram.hide(this)
    }
}