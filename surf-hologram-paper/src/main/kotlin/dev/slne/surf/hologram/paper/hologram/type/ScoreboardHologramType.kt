package dev.slne.surf.hologram.paper.hologram.type

import dev.slne.surf.hologram.api.hologram.ScoreboardHologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.type.HologramType
import dev.slne.surf.hologram.api.hologram.type.ScoreboardHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.ScoreboardHologramImpl
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class ScoreboardHologramType : HologramType<ScoreboardHologram, ScoreboardHologramOptions> {
    override val id: String = "scoreboard"
    override val hologramClazz: Class<ScoreboardHologram> = ScoreboardHologram::class.java
    override val optionsClazz: Class<ScoreboardHologramOptions> =
        ScoreboardHologramOptions::class.java

    override fun create(
        metaData: HologramMetaData,
        hologramOrientationType: HologramOrientationType,
        centerLocation: HologramLocation,
        displayedText: Component,
        hitbox: HologramHitbox?,
        viewers: ObjectSet<HoloOfflinePlayer>?,
        options: ScoreboardHologramOptions
    ) = ScoreboardHologramImpl(
        metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        hitbox,
        viewers,
        options.placementRange,
        options.scoreUnit
    )
}