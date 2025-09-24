package dev.slne.surf.hologram.paper.hologram.location

import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.location.HologramWorld

class HologramLocationImpl(
    override val x: Double,
    override val y: Double,
    override val z: Double,
    override val world: HologramWorld
) : HologramLocation