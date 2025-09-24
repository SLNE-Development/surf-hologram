package dev.slne.surf.hologram.api.hologram

import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import it.unimi.dsi.fastutil.objects.ObjectSet

interface Hologram {
    val metaData: HologramMetaData
    val hologramType: HologramType
    val centerLocation: HologramLocation

    val viewers: ObjectSet<HoloOfflinePlayer>
}