@file:Suppress("UNCHECKED_CAST")

package dev.slne.surf.hologram.paper.hologram

import com.github.retrooper.packetevents.PacketEvents
import dev.slne.surf.hologram.api.event.HologramEvent
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.hologram.HologramEventHandler
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.player.HoloPlayer
import dev.slne.surf.hologram.api.util.forEachPacketViewer
import dev.slne.surf.hologram.api.util.hide
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.core.service.hologramPlayerService
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.PaperPackets
import dev.slne.surf.hologram.paper.util.debug
import dev.slne.surf.hologram.paper.util.toBukkitLocation
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import it.unimi.dsi.fastutil.objects.ObjectList
import org.bukkit.Bukkit
import kotlin.reflect.KClass

abstract class BaseHologram : Hologram {
    private val eventHandlers =
        mutableObject2ObjectMapOf<KClass<out HologramEvent>, ObjectList<HologramEventHandler<*>>>()

    override fun retrieveViewers() =
        viewers?.mapNotNull { it.player }?.toObjectSet() ?: Bukkit.getOnlinePlayers()
            .mapNotNull { hologramPlayerService.getPlayer(it.uniqueId) }.toObjectSet()

    override fun show(player: HoloPlayer) {
        val bukkitPlayer = player.bukkitPlayer ?: return
        val packetPlayer = PacketEvents.getAPI().playerManager.getUser(bukkitPlayer)

        debug("Showing hologram '${metaData.name}' to player '${bukkitPlayer.name}'")

        packetPlayer.sendPacket(PaperPackets.buildHoloSpawnPacket(this))
        packetPlayer.sendPacket(PaperPackets.buildHoloMetaPacket(this))

        hitbox?.let {
            packetPlayer.sendPacket(PaperPackets.buildHoloInteractionSpawnPacket(this, it))
            packetPlayer.sendPacket(PaperPackets.buildHoloInteractionMetaPacket(this, it))
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

    override fun refreshClean() {
        val actualHologram = this
        val fakeHologram = this.duplicate(true)

        fakeHologram.show()
        actualHologram.refresh()
        fakeHologram.hide()
    }

    override fun teleportHere(player: HoloPlayer) =
        player.bukkitPlayer?.teleport(centerLocation.toBukkitLocation()) == true

    override fun teleportTo(newLocation: HologramLocation, save: Boolean) {
        if (save) {
            hologramService.editHologramSaving(this) {
                centerLocation = newLocation
            }
        }

        forEachPacketViewer {
            it.sendPacket(PaperPackets.buildTeleportPacket(this, newLocation))
        }
    }

    override fun <T : HologramEvent> addEventHandler(
        eventClass: KClass<T>,
        handler: HologramEventHandler<T>
    ) {
        eventHandlers.computeIfAbsent(eventClass) { mutableObjectListOf() }
            .add(handler)
    }

    override fun <T : HologramEvent> removeEventHandler(
        eventClass: KClass<T>,
        handler: HologramEventHandler<T>
    ) {
        eventHandlers[eventClass]?.remove(handler)
    }

    override fun <T : HologramEvent> callHandlers(event: T) {
        val handlers = eventHandlers[event::class] ?: return
        for (handler in handlers) {
            (handler as HologramEventHandler<T>)(event)
        }
    }
}