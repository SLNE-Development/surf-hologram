package dev.slne.surf.hologram.paper.hologram.type

import dev.slne.surf.hologram.api.hologram.TooltipHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.type.TooltipHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.TooltipHologramImpl
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class TooltipHologramType : HologramType<TooltipHologram, TooltipHologramOptions> {
    override val id: String = "tooltip"
    override val hologramClazz: Class<TooltipHologram> = TooltipHologram::class.java
    override val optionsClazz: Class<TooltipHologramOptions> = TooltipHologramOptions::class.java

    override fun create(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        options: TooltipHologramOptions
    ) = TooltipHologramImpl(
        metaData,
        centerLocation,
        displayedText,
        hitbox,
        viewers,
        options.targetEntityId,
        options.targetLocation,
        options.lookAngleThreshold,
        options.maxDistance
    )
}
