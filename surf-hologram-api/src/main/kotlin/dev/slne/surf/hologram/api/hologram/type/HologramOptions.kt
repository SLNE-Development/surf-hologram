package dev.slne.surf.hologram.api.hologram.type

import dev.slne.surf.hologram.api.hologram.BounceSpeed

interface HologramOptions

data class BouncingHologramOptions(
    var bounceHeight: Double,
    var bounceSpeed: BounceSpeed
) : HologramOptions

class SimpleHologramOptions : HologramOptions

data class ScoreboardHologramOptions(
    var placementRange: IntRange,
    var scoreUnit: String
) : HologramOptions

