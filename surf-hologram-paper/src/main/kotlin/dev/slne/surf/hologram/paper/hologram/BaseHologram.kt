package dev.slne.surf.hologram.paper.hologram

import com.github.retrooper.packetevents.PacketEvents
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloPlayer
import dev.slne.surf.hologram.api.util.forEachBukkitViewer
import dev.slne.surf.hologram.api.util.hide
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.core.service.hologramPlayerService
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.PaperPackets
import dev.slne.surf.hologram.paper.util.debug
import dev.slne.surf.hologram.paper.util.toBukkitLocation
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import org.bukkit.Bukkit

abstract class BaseHologram : Hologram {
    override fun retrieveViewers() =
        viewers?.mapNotNull { it.player }?.toObjectSet() ?: Bukkit.getOnlinePlayers()
            .mapNotNull { hologramPlayerService.getPlayer(it.uniqueId) }.toObjectSet()

    override fun show(player: HoloPlayer) {
        val bukkitPlayer = player.bukkitPlayer ?: return
        val packetPlayer = PacketEvents.getAPI().playerManager.getUser(bukkitPlayer)

        debug("Showing hologram '${metaData.name}' to player '${bukkitPlayer.name}'")

        packetPlayer.sendPacket(PaperPackets.buildHoloSpawnPacket(this))
        packetPlayer.sendPacket(PaperPackets.buildHoloMetaPacket(this))

        if (metaData.clickable) {
            packetPlayer.sendPacket(PaperPackets.buildHoloInteractionSpawnPacket(this))
            packetPlayer.sendPacket(PaperPackets.buildHoloInteractionMetaPacket(this))
        }
    }

    override fun hide(player: HoloPlayer) {
        val bukkitPlayer = player.bukkitPlayer ?: return
        val packetPlayer = PacketEvents.getAPI().playerManager.getUser(bukkitPlayer)

        packetPlayer.sendPacket(PaperPackets.buildDestroyPacket(this))
    }

    override fun refresh() {
        hide()
        show()
    }

    override fun teleportHere(player: HoloPlayer) =
        player.bukkitPlayer?.teleport(centerLocation.toBukkitLocation()) == true

    override fun teleportTo(newLocation: HologramLocation, save: Boolean) {
        if (save) {
            hologramService.editHologramSaving(this) {
                centerLocation = newLocation
            }
        }

        forEachBukkitViewer {
            val packetPlayer = PacketEvents.getAPI().playerManager.getUser(it)

            packetPlayer.sendPacket(PaperPackets.buildTeleportPacket(this, newLocation))
        }
    }
}