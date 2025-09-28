package dev.slne.surf.hologram.paper.hologram.types

import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.types.SimpleHologram
import dev.slne.surf.hologram.api.hologram.util.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.BaseHologram
import dev.slne.surf.surfapi.core.api.util.random
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class SimpleHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramOrientationType: HologramOrientationType,
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val creationReason: HologramCreationReason,
    override val hitbox: HologramHitbox?,
    override val viewers: ObjectSet<HoloOfflinePlayer>?
) : BaseHologram(), SimpleHologram {
    override fun duplicate(spawnable: Boolean) = SimpleHologramImpl(
        if (spawnable) metaData.duplicate().apply {
            name = "$name-DUPLICATE-${(1..1000).random()}"
            holoEntityId = random.nextInt()
            interactionEntityId = random.nextInt()
        } else metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        creationReason,
        hitbox,
        viewers
    )
}