package dev.slne.surf.hologram.api.hologram.location

interface HologramLocation {
    var x: Double
    var y: Double
    var z: Double
    var world: HologramWorld

    fun duplicate(): HologramLocation
}