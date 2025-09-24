package dev.slne.surf.hologram.paper.hologram.types

import com.github.retrooper.packetevents.PacketEvents
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.types.SimpleHologram
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import dev.slne.surf.hologram.api.util.forEachBukkitViewer
import dev.slne.surf.hologram.paper.PaperPackets
import dev.slne.surf.hologram.paper.util.toBukkitLocation
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component

class SimpleHologramImpl(
    override val metaData: HologramMetaData,
    override val hologramType: HologramType,
    override val centerLocation: HologramLocation,
    override val displayedText: Component,
    override val viewers: ObjectSet<HoloOfflinePlayer>?
) : SimpleHologram {
    override fun show(player: HoloPlayer) {
        val bukkitPlayer = player.bukkitPlayer ?: return
        val packetPlayer = PacketEvents.getAPI().playerManager.getUser(bukkitPlayer)

        packetPlayer.sendPacket(PaperPackets.buildHoloSpawnPacket(this))
        packetPlayer.sendPacket(PaperPackets.buildHoloMetaPacket(this))
        packetPlayer.sendPacket(PaperPackets.buildHoloInteractionSpawnPacket(this))
    }

    override fun hide(player: HoloPlayer) {
        val bukkitPlayer = player.bukkitPlayer ?: return
        val packetPlayer = PacketEvents.getAPI().playerManager.getUser(bukkitPlayer)

        packetPlayer.sendPacket(PaperPackets.buildDestroyPacket(this))
    }

    override fun teleportHere(player: HoloPlayer) {
        val bukkitPlayer = player.bukkitPlayer ?: return
        bukkitPlayer.teleport(centerLocation.toBukkitLocation())
    }

    override fun teleportTo(newLocation: HologramLocation) {
        forEachBukkitViewer {
            val packetPlayer = PacketEvents.getAPI().playerManager.getUser(it)

            packetPlayer.sendPacket(PaperPackets.buildTeleportPacket(this, newLocation))
        }
    }
}