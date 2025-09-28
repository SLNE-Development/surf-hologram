package dev.slne.surf.hologram.paper.hologram.type

import dev.slne.surf.hologram.api.hologram.SimpleHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.type.SimpleHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.SimpleHologramImpl
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class SimpleHologramType : HologramType<SimpleHologram, SimpleHologramOptions> {
    override val id: String = "simple"
    override val hologramClazz: Class<SimpleHologram> = SimpleHologram::class.java
    override val optionsClazz: Class<SimpleHologramOptions> = SimpleHologramOptions::class.java
    override fun create(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        creationReason: HologramCreationReason,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        options: SimpleHologramOptions
    ) = SimpleHologramImpl(
        metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        creationReason,
        hitbox,
        viewers
    )
}