package dev.slne.surf.hologram.paper.hologram.types

import dev.slne.surf.hologram.api.hologram.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.HologramHitbox
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.types.SimpleHologram
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.paper.hologram.BaseHologram
import dev.slne.surf.surfapi.core.api.util.random
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class SimpleHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramType: HologramType,
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
        hologramType,
        centerLocation,
        displayedText,
        creationReason,
        hitbox,
        viewers
    )
}