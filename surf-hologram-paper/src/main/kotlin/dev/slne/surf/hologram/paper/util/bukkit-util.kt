package dev.slne.surf.hologram.paper.util

import org.bukkit.Location

fun Location.string(): String {
    return "World: ${this.world?.name}, X: ${this.x}, Y: ${this.y}, Z: ${this.z}, Yaw: ${this.yaw}, Pitch: ${this.pitch}"
}