package dev.slne.surf.hologram.paper.util

import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import io.github.retrooper.packetevents.util.SpigotConversionUtil

fun HologramLocation.toPacketLocation() =
    SpigotConversionUtil.fromBukkitLocation(this.toBukkitLocation())