package dev.slne.surf.hologram.paper.hologram.type

import dev.slne.surf.hologram.api.hologram.BouncingHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.BouncingHologramOptions
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.BouncingHologramImpl
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class BouncingHologramType : HologramType<BouncingHologram, BouncingHologramOptions> {
    override val id: String = "bouncing"
    override val hologramClazz: Class<BouncingHologram> = BouncingHologram::class.java
    override val optionsClazz: Class<BouncingHologramOptions> = BouncingHologramOptions::class.java
    override fun create(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        options: BouncingHologramOptions
    ) = BouncingHologramImpl(
        metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        hitbox,
        viewers,
        0.0,
        options.bounceHeight,
        options.bounceSpeed,
        true
    )
}