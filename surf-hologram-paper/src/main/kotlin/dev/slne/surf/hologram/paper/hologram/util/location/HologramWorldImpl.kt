package dev.slne.surf.hologram.paper.hologram.util.location

import dev.slne.surf.hologram.api.hologram.location.HologramWorld
import org.bukkit.Bukkit
import org.bukkit.World
import java.util.*

class HologramWorldImpl(
    override val worldName: String,
    override val worldId: UUID
) : HologramWorld {
    override val bukkitWorld: World
        get() = Bukkit.getWorld(worldId) ?: error("World with id $worldId not found")
}