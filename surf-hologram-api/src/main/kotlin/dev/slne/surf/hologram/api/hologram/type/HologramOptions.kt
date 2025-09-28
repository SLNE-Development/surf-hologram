package dev.slne.surf.hologram.api.hologram.type

import dev.slne.surf.hologram.api.hologram.BounceSpeed

interface HologramOptions

data class BouncingHologramOptions(
    val bounceHeight: Double,
    val bounceSpeed: BounceSpeed
) : HologramOptions

class SimpleHologramOptions : HologramOptions

data class ScoreboardHologramOptions(
    val placementRange: IntRange,
    val scoreUnit: String
) : HologramOptions

