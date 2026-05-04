package dev.slne.surf.hologram.paper.hologram.type

import dev.slne.surf.hologram.api.hologram.FadingHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.FadingHologramOptions
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.FadingHologramImpl
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class FadingHologramType : HologramType<FadingHologram, FadingHologramOptions> {
    override val id: String = "fading"
    override val hologramClazz: Class<FadingHologram> = FadingHologram::class.java
    override val optionsClazz: Class<FadingHologramOptions> = FadingHologramOptions::class.java

    override fun create(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        options: FadingHologramOptions
    ) = FadingHologramImpl(
        metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        hitbox,
        viewers,
        options.fadeSpeed,
        options.fadeMaxOffset
    )
}
