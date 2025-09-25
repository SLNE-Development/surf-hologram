package dev.slne.surf.hologram.paper.hologram.types

import com.github.retrooper.packetevents.PacketEvents
import dev.slne.surf.hologram.api.hologram.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.HologramMetaData
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.types.BounceDirection
import dev.slne.surf.hologram.api.hologram.types.BounceSpeed
import dev.slne.surf.hologram.api.hologram.types.BouncingHologram
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.api.player.HoloPlayer
import dev.slne.surf.hologram.api.util.forEachBukkitViewer
import dev.slne.surf.hologram.core.service.hologramPlayerService
import dev.slne.surf.hologram.paper.PaperPackets
import dev.slne.surf.hologram.paper.util.toBukkitLocation
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

class BouncingHologramImpl(
    override val metaData: HologramMetaData,
    override val hologramType: HologramType,
    override val centerLocation: HologramLocation,
    override val displayedText: Component,
    override val creationReason: HologramCreationReason,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override val bounceState: Int,
    override val bounceHeight: Double,
    override val bounceSpeed: BounceSpeed,
    override val bounceDirection: BounceDirection
) : BouncingHologram {
    override fun retrieveViewers() =
        viewers?.mapNotNull { it.player }?.toObjectSet() ?: Bukkit.getOnlinePlayers()
            .mapNotNull { hologramPlayerService.getPlayer(it.uniqueId) }.toObjectSet()

    override fun show(player: HoloPlayer) {
        val bukkitPlayer = player.bukkitPlayer ?: return
        val packetPlayer = PacketEvents.getAPI().playerManager.getUser(bukkitPlayer)

        packetPlayer.sendPacket(PaperPackets.buildHoloSpawnPacket(this))
        packetPlayer.sendPacket(PaperPackets.buildHoloMetaPacket(this))
        packetPlayer.sendPacket(PaperPackets.buildHoloInteractionSpawnPacket(this))
        packetPlayer.sendPacket(PaperPackets.buildHoloInteractionMetaPacket(this))
    }

    override fun hide(player: HoloPlayer) {
        val bukkitPlayer = player.bukkitPlayer ?: return
        val packetPlayer = PacketEvents.getAPI().playerManager.getUser(bukkitPlayer)

        packetPlayer.sendPacket(PaperPackets.buildDestroyPacket(this))
    }

    override fun teleportHere(player: HoloPlayer) =
        player.bukkitPlayer?.teleport(centerLocation.toBukkitLocation()) == true

    override fun teleportTo(newLocation: HologramLocation) {
        forEachBukkitViewer {
            val packetPlayer = PacketEvents.getAPI().playerManager.getUser(it)

            packetPlayer.sendPacket(PaperPackets.buildTeleportPacket(this, newLocation))
        }
    }

    override fun tick() {
        TODO("Not yet implemented")
    }
}
