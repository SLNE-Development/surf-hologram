package dev.slne.surf.hologram.paper.hologram.types

import dev.slne.surf.hologram.api.hologram.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.types.BounceDirection
import dev.slne.surf.hologram.api.hologram.types.BounceSpeed
import dev.slne.surf.hologram.api.hologram.types.BouncingHologram
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.BaseHologram
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class BouncingHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramType: HologramType,
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val creationReason: HologramCreationReason,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override var bounceState: Double,
    override val bounceHeight: Double,
    override val bounceSpeed: BounceSpeed,
    override var bounceDirection: BounceDirection
) : BaseHologram(), BouncingHologram {
    override fun tick() {
        val (speed, _) = bounceSpeed

        if (bounceDirection) {
            bounceState += speed
            if (bounceState >= bounceHeight) {
                bounceState = bounceHeight
                bounceDirection = false
            }
        } else {
            bounceState -= speed
            if (bounceState <= 0) {
                bounceState = 0.0
                bounceDirection = true
            }
        }

        this.teleportTo(centerLocation.duplicate().apply {
            this.y += bounceState
        }, false)
    }
}
