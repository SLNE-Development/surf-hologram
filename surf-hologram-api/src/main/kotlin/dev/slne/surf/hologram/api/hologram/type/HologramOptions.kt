package dev.slne.surf.hologram.api.hologram.type

import dev.slne.surf.hologram.api.hologram.BounceSpeed

interface HologramOptions

data class BouncingHologramOptions(
    var bounceHeight: Double = 1.0,
    var bounceSpeed: BounceSpeed = 0.1
) : HologramOptions

class SimpleHologramOptions : HologramOptions

data class ScoreboardHologramOptions(
    var placementRange: IntRange = 1..10,
    var scoreUnit: String = "P."
) : HologramOptions

