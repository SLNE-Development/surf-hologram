package dev.slne.surf.hologram.paper.listener

import com.github.retrooper.packetevents.event.PacketListenerAbstract
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.protocol.player.InteractionHand
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPosition
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPositionAndRotation
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerRotation
import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.hologramPlayerService
import dev.slne.surf.hologram.paper.hologram.TooltipHologramImpl
import dev.slne.surf.hologram.paper.plugin
import org.bukkit.entity.Player

class InternalEventPacketListener : PacketListenerAbstract() {
    override fun onPacketReceive(event: PacketReceiveEvent) {
        val player = runCatching { event.getPlayer<Player>() }.getOrNull() ?: return

        when (event.packetType) {
            PacketType.Play.Client.INTERACT_ENTITY -> {
                val packet = WrapperPlayClientInteractEntity(event)
                val hologram =
                    hologramRegistry.getHologramByInteractionId(packet.entityId) ?: return
                val holoPlayer = hologramPlayerService.getPlayer(player.uniqueId) ?: return

                when (packet.action) {
                    WrapperPlayClientInteractEntity.InteractAction.ATTACK -> {
                        plugin.launch(plugin.entityDispatcher(player)) {
                            HologramClickEvent(holoPlayer, hologram).callEvent()
                        }
                    }

                    WrapperPlayClientInteractEntity.InteractAction.INTERACT -> {
                        if (packet.hand != InteractionHand.MAIN_HAND) return

                        plugin.launch(plugin.entityDispatcher(player)) {
                            HologramClickEvent(holoPlayer, hologram).callEvent()
                        }
                    }

                    WrapperPlayClientInteractEntity.InteractAction.INTERACT_AT -> {
                        // Already handled by INTERACT, ignore.
                    }
                }
            }

            PacketType.Play.Client.PLAYER_POSITION -> {
                val packet = WrapperPlayClientPlayerPosition(event)
                val cached = TooltipHologramImpl.playerLookCache[player.uniqueId]
                TooltipHologramImpl.updatePlayerLook(
                    player.uniqueId,
                    TooltipHologramImpl.Companion.PlayerLookData(
                        footX = packet.position.x,
                        footY = packet.position.y,
                        footZ = packet.position.z,
                        yaw = cached?.yaw ?: player.location.yaw,
                        pitch = cached?.pitch ?: player.location.pitch,
                        worldName = player.world.name
                    )
                )
            }

            PacketType.Play.Client.PLAYER_ROTATION -> {
                val packet = WrapperPlayClientPlayerRotation(event)
                val cached = TooltipHologramImpl.playerLookCache[player.uniqueId]
                TooltipHologramImpl.updatePlayerLook(
                    player.uniqueId,
                    TooltipHologramImpl.Companion.PlayerLookData(
                        footX = cached?.footX ?: player.location.x,
                        footY = cached?.footY ?: player.location.y,
                        footZ = cached?.footZ ?: player.location.z,
                        yaw = packet.yaw,
                        pitch = packet.pitch,
                        worldName = player.world.name
                    )
                )
            }

            PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION -> {
                val packet = WrapperPlayClientPlayerPositionAndRotation(event)
                TooltipHologramImpl.updatePlayerLook(
                    player.uniqueId,
                    TooltipHologramImpl.Companion.PlayerLookData(
                        footX = packet.position.x,
                        footY = packet.position.y,
                        footZ = packet.position.z,
                        yaw = packet.yaw,
                        pitch = packet.pitch,
                        worldName = player.world.name
                    )
                )
            }
        }
    }
}
