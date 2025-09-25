package dev.slne.surf.hologram.paper.util

import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.surfapi.bukkit.api.util.forEachPlayer
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import io.github.retrooper.packetevents.util.SpigotConversionUtil

fun HologramLocation.toPacketLocation() =
    SpigotConversionUtil.fromBukkitLocation(this.toBukkitLocation())

fun debug(message: String) = forEachPlayer {
    it.sendText {
        spacer("[")
        info("DEBUG")
        spacer("] ")
        text(message)
    }
}