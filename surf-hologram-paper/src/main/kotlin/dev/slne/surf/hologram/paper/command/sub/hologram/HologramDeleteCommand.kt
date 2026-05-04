package dev.slne.surf.hologram.paper.command.sub.hologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.hologram.api.command.argument.hologramArgument
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import net.kyori.adventure.text.event.ClickEvent

fun CommandAPICommand.hologramDeleteCommand() = subcommand("delete") {
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM_DELETE)
    hologramArgument("hologram")

    playerExecutor { player, args ->
        val hologram: Hologram by args

        player.sendText {
            appendInfoPrefix()
            info("Möchtest du das Hologramm ")
            variableValue(hologram.metaData.name)
            info(" wirklich löschen? ")
            append {
                spacer("[")
                error("Löschen")
                spacer("]")
                clickEvent(ClickEvent.callback {
                    hologramService.deleteHologram(hologram)
                    player.sendText {
                        appendSuccessPrefix()
                        success("Das Hologramm ")
                        variableValue(hologram.metaData.name)
                        success(" wurde gelöscht.")
                    }
                })
            }
        }
    }
}