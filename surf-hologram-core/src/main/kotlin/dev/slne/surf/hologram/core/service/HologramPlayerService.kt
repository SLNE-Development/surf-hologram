package dev.slne.surf.hologram.core.service

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import java.util.*

interface HologramPlayerService {
    fun getPlayer(uuid: UUID, name: String): HoloPlayer
    fun getPlayer(uuid: UUID): HoloPlayer?
    fun getPlayer(name: String): HoloPlayer?
    fun getOfflinePlayer(uuid: UUID): HoloOfflinePlayer?

    companion object {
        val INSTANCE = requiredService<HologramPlayerService>()
    }
}

val hologramPlayerService get() = HologramPlayerService.INSTANCE