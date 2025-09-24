package dev.slne.surf.hologram.api.event

import dev.slne.surf.hologram.api.player.HoloPlayer

interface HologramPlayerEvent : HologramEvent {
    val player: HoloPlayer
}