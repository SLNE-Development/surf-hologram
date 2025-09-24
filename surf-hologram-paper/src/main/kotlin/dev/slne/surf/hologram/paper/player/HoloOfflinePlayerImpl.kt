package dev.slne.surf.hologram.paper.player

import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import dev.slne.surf.hologram.core.service.hologramPlayerService
import java.util.*

class HoloOfflinePlayerImpl(
    override val uuid: UUID
) : HoloOfflinePlayer {
    override val player: HoloPlayer? get() = hologramPlayerService.getPlayer(uuid)
}