package dev.slne.surf.hologram.paper.command.sub.surfhologram

import com.github.retrooper.packetevents.util.Vector3d
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.util.random
import dev.slne.surf.hologram.api.hologram.TooltipHologram
import dev.slne.surf.hologram.api.hologram.type.TooltipHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.hologram.util.HologramTextAlignment
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.hologram.util.HologramMetaDataImpl
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.hologram.paper.util.toHologramLocation
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.FluidCollisionMode

fun CommandAPICommand.surfHologramDebugTooltipCommand() = subcommand("tooltip") {
    withPermission(HoloPermissionRegistry.COMMAND_SURFHOLOGRAM_DEBUG_TOOLTIP)

    playerExecutor { player, _ ->
        // Ray-cast up to 10 blocks; detect both blocks and entities.
        val rayResult = player.rayTraceEntities(10)
        val hitEntity = rayResult?.hitEntity

        val name = "debug-tooltip-${random.nextInt(1, 999999)}"
        val metaData = HologramMetaDataImpl(
            name,
            random.nextInt(),
            random.nextInt(),
            Vector3d(1.0, 1.0, 1.0),
            32f,
            200,
            HologramTextAlignment.CENTER,
            NamedTextColor.YELLOW
        )

        if (hitEntity != null) {
            val tooltipLocation = hitEntity.location.clone().add(0.0, hitEntity.height + 0.3, 0.0)
                .toHologramLocation()

            val options = TooltipHologramOptions(
                targetEntityId = hitEntity.entityId,
                lookAngleThreshold = 15.0,
                maxDistance = 10.0
            )

            val holo = hologramService.createHologram(
                metaData,
                HologramOrientationType.FIXED,
                tooltipLocation,
                Component.text("[Entity] ID: ${hitEntity.entityId} | ${hitEntity.type.name}"),
                null,
                null,
                TooltipHologram::class.java,
                options
            )

            hologramRegistry.registerHologram(holo)

            player.sendText {
                appendSuccessPrefix()
                success("Tooltip-Hologramm für Entity ")
                variableValue("${hitEntity.type.name} (ID: ${hitEntity.entityId})")
                success(" erstellt.")
            }
        } else {
            // Block target: ray-cast blocks
            val blockResult =
                player.rayTraceBlocks(10.0, FluidCollisionMode.NEVER) ?: run {
                    player.sendText {
                        appendErrorPrefix()
                        error("Du schaust auf keinen Block und keine Entity.")
                    }
                    return@playerExecutor
                }

            val block = blockResult.hitBlock ?: run {
                player.sendText {
                    appendErrorPrefix()
                    error("Du schaust auf keinen Block und keine Entity.")
                }
                return@playerExecutor
            }

            val blockCenter = block.location.clone().add(0.5, 1.3, 0.5)
            val tooltipLocation = blockCenter.toHologramLocation()
            val targetLocation = block.location.clone().add(0.5, 0.5, 0.5).toHologramLocation()

            val options = TooltipHologramOptions(
                targetLocation = targetLocation,
                lookAngleThreshold = 15.0,
                maxDistance = 10.0
            )

            val holo = hologramService.createHologram(
                metaData,
                HologramOrientationType.FIXED,
                tooltipLocation,
                Component.text("[Block] ${block.type.name} @ ${block.x}, ${block.y}, ${block.z}"),
                null,
                null,
                TooltipHologram::class.java,
                options
            )

            hologramRegistry.registerHologram(holo)

            player.sendText {
                appendSuccessPrefix()
                success("Tooltip-Hologramm für Block ")
                variableValue("${block.type.name} @ ${block.x}, ${block.y}, ${block.z}")
                success(" erstellt.")
            }
        }
    }
}
