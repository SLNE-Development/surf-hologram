package dev.slne.surf.hologram.paper.util


import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.paper.hologram.util.location.HologramLocationImpl
import dev.slne.surf.hologram.paper.hologram.util.location.HologramWorldImpl
import org.bukkit.Location as BukkitLocation

fun HologramLocation.toBukkitLocation() =
    BukkitLocation(this.world.bukkitWorld, this.x, this.y, this.z)

fun BukkitLocation.toHologramLocation() = HologramLocationImpl(
    this.x,
    this.y,
    this.z,
    HologramWorldImpl(
        this.world.name,
        this.world.uid
    )
)