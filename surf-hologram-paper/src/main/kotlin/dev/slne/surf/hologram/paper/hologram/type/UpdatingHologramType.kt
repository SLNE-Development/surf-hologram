package dev.slne.surf.hologram.paper.hologram.type

import dev.slne.surf.hologram.api.hologram.UpdatingHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.type.UpdatingHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.UpdatingHologramImpl
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class UpdatingHologramType : HologramType<UpdatingHologram, UpdatingHologramOptions> {
    override val id: String = "updating"
    override val hologramClazz: Class<UpdatingHologram> = UpdatingHologram::class.java
    override val optionsClazz: Class<UpdatingHologramOptions> = UpdatingHologramOptions::class.java
    override fun create(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        options: UpdatingHologramOptions,
    ) = UpdatingHologramImpl(
        metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        hitbox,
        viewers,
        options.updateInterval
    )
}