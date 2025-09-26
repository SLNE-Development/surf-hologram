package dev.slne.surf.hologram.paper.util

import dev.slne.surf.hologram.core.service.hologramPlayerService
import org.bukkit.Location
import org.bukkit.entity.Player

fun Location.string(): String {
    return "World: ${this.world?.name}, X: ${this.x}, Y: ${this.y}, Z: ${this.z}, Yaw: ${this.yaw}, Pitch: ${this.pitch}"
}

val Player.holoPlayer get() = hologramPlayerService.getPlayer(this.uniqueId, this.name)