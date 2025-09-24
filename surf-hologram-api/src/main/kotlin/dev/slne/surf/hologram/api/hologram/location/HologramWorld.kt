package dev.slne.surf.hologram.api.hologram.location

import org.bukkit.World
import java.util.*

interface HologramWorld {
    val worldName: String
    val worldId: UUID
    val bukkitWorld: World
}