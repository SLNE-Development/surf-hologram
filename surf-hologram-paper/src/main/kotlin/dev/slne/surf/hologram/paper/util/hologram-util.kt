package dev.slne.surf.hologram.paper.util


import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import org.bukkit.Location as BukkitLocation

fun HologramLocation.toBukkitLocation() =
    BukkitLocation(this.world.bukkitWorld, this.x, this.y, this.z)