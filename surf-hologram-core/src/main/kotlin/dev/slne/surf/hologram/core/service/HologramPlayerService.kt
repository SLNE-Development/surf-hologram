package dev.slne.surf.hologram.core.service

import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import dev.slne.surf.surfapi.core.api.util.requiredService
import java.util.*

interface HologramPlayerService {
    fun getPlayer(uuid: UUID): HoloPlayer?
    fun getPlayer(name: String): HoloPlayer?
    fun getOfflinePlayer(uuid: UUID): HoloOfflinePlayer?

    companion object {
        val INSTANCE = requiredService<HologramPlayerService>()
    }
}

val hologramPlayerService get() = HologramPlayerService.INSTANCE