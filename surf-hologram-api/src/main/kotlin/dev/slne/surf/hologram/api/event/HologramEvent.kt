package dev.slne.surf.hologram.api.event

import dev.slne.surf.hologram.api.hologram.Hologram

interface HologramEvent {
    val hologram: Hologram
}