package dev.slne.surf.hologram.paper.hologram.location

import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.location.HologramWorld

class HologramLocationImpl(
    override var x: Double,
    override var y: Double,
    override var z: Double,
    override var world: HologramWorld
) : HologramLocation {
    override fun duplicate() = HologramLocationImpl(x, y, z, world)
    override fun toString(): String {
        return "World: ${world.worldName}, X: ${x.toInt()}, Y: ${y.toInt()}, Z: ${z.toInt()}"
    }
}