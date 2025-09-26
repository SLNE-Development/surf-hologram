package dev.slne.surf.hologram.paper.command.sub

import com.github.retrooper.packetevents.util.Vector3d
import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.integerArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.hologram.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.HologramTextAlignment
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.command.argument.hologramTypeArgument
import dev.slne.surf.hologram.paper.hologram.HologramMetaDataImpl
import dev.slne.surf.hologram.paper.plugin
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.hologram.paper.util.toHologramLocation
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.random
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import kotlin.system.measureTimeMillis

fun CommandAPICommand.hologramCreateDebugCommand() = subcommand("create-debug") {
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM_DEBUG_CREATE)
    hologramTypeArgument("type")
    integerArgument("amount")

    playerExecutor { player, arguments ->
        val type: HologramType by arguments
        val amount: Int by arguments

        plugin.launch {
            val ms = measureTimeMillis {
                repeat(amount) {
                    val name = "debug-holo-${random.nextInt(1, 999999)}"
                    val offsetX = random.nextDouble(-7.0, 7.0)
                    val offsetZ = random.nextDouble(-7.0, 7.0)

                    val holoLocation =
                        player.location.clone().add(offsetX, 1.0, offsetZ).toHologramLocation()

                    val holo = hologramService.createHologram(
                        type,
                        HologramMetaDataImpl(
                            name,
                            random.nextInt(),
                            random.nextInt(),
                            1f,
                            1f,
                            Vector3d(1.0, 1.0, 1.0),
                            32f,
                            200,
                            HologramTextAlignment.CENTER,
                            NamedTextColor.RED,
                            true
                        ),
                        holoLocation,
                        Component.text("Debug #$name"),
                        HologramCreationReason.Client(player.name),
                        bouncingStep = 0.1,
                        bouncingHeight = 1.0
                    )

                    holo.show()
                }
            }


            player.sendText {
                appendPrefix()
                success("Es wurden ")
                variableValue(amount)
                success(" Hologramme des Typs ")
                variableValue(type.name)
                success(" erstellt. ")
                spacer("(${ms}ms, ${"%.2f".format(amount / (ms / 1000.0))}/s)")
            }
        }
    }
}