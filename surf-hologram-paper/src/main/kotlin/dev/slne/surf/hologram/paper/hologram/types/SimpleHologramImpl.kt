package dev.slne.surf.hologram.paper.hologram.types

import dev.slne.surf.hologram.api.hologram.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.types.SimpleHologram
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.BaseHologram
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class SimpleHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramType: HologramType,
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val creationReason: HologramCreationReason,
    override val viewers: ObjectSet<HoloOfflinePlayer>?
) : BaseHologram(), SimpleHologram