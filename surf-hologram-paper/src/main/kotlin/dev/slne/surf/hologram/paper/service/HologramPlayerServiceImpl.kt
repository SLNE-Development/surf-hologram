package dev.slne.surf.hologram.paper.service

import com.google.auto.service.AutoService
import dev.slne.surf.hologram.core.service.HologramPlayerService
import dev.slne.surf.hologram.paper.player.HoloOfflinePlayerImpl
import dev.slne.surf.hologram.paper.player.HoloPlayerImpl
import net.kyori.adventure.util.Services
import org.bukkit.Bukkit
import java.util.*

@AutoService(HologramPlayerService::class)
class HologramPlayerServiceImpl : HologramPlayerService, Services.Fallback {
    override fun getPlayer(uuid: UUID) = Bukkit.getPlayer(uuid)?.let {
        HoloPlayerImpl(it.name, it.uniqueId)
    }

    override fun getPlayer(name: String) = Bukkit.getPlayer(name)?.let {
        HoloPlayerImpl(it.name, it.uniqueId)
    }

    override fun getOfflinePlayer(uuid: UUID) = HoloOfflinePlayerImpl(uuid)
}