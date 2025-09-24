package dev.slne.surf.hologram.paper.hologram.types

import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.types.BounceDirection
import dev.slne.surf.hologram.api.hologram.types.BounceSpeed
import dev.slne.surf.hologram.api.hologram.types.BouncingHologram
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class BouncingHologramImpl(
    override val metaData: HologramMetaData,
    override val hologramType: HologramType,
    override val centerLocation: HologramLocation,
    override val displayedText: Component,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override val bounceState: Int,
    override val bounceHeight: Double,
    override val bounceSpeed: BounceSpeed,
    override val bounceDirection: BounceDirection
) : BouncingHologram {
    override fun show(player: HoloPlayer) {
        TODO("Not yet implemented")
    }

    override fun hide(player: HoloPlayer) {
        TODO("Not yet implemented")
    }

    override fun teleportHere(player: HoloPlayer) {
        TODO("Not yet implemented")
    }

    override fun teleportTo(newLocation: HologramLocation) {
        TODO("Not yet implemented")
    }

    override fun tick() {
        TODO("Not yet implemented")
    }
}