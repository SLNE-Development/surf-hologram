package dev.slne.surf.hologram.api.hologram.location

interface HologramLocation {
    val x: Double
    val y: Double
    val z: Double
    val world: HologramWorld
}