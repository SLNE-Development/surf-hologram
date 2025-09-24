package dev.slne.surf.hologram.paper.listener

import com.github.retrooper.packetevents.event.PacketListenerAbstract
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.protocol.player.InteractionHand
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity
import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.hologramPlayerService
import dev.slne.surf.hologram.paper.plugin
import org.bukkit.entity.Player

class InternalEventPacketListener : PacketListenerAbstract() {
    override fun onPacketReceive(event: PacketReceiveEvent) {
        when (event.packetType) {
            PacketType.Play.Client.INTERACT_ENTITY -> {
                val packet = WrapperPlayClientInteractEntity(event)
                val hologram =
                    hologramRegistry.getHologramByInteractionId(packet.entityId) ?: return
                val player = event.getPlayer<Player>()
                val holoPlayer = hologramPlayerService.getPlayer(player.uniqueId) ?: return

                when (packet.action) {
                    WrapperPlayClientInteractEntity.InteractAction.ATTACK -> {
                        plugin.launch(plugin.entityDispatcher(player)) {
                            HologramClickEvent(
                                holoPlayer,
                                hologram
                            ).callEvent()
                        }
                    }

                    WrapperPlayClientInteractEntity.InteractAction.INTERACT -> {
                        if (packet.hand != InteractionHand.MAIN_HAND) {
                            return
                        }

                        plugin.launch(plugin.entityDispatcher(player)) {
                            HologramClickEvent(
                                holoPlayer,
                                hologram
                            ).callEvent()
                        }
                    }

                    WrapperPlayClientInteractEntity.InteractAction.INTERACT_AT -> {
                        // This is already handled by INTERACT action, so we can ignore it.
                    }
                }
            }
        }
    }
}