package dev.slne.surf.hologram.paper.command.sub.hologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.command.argument.hologramArgument
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun CommandAPICommand.hologramRefreshContentCommand() = subcommand("refreshContent") {
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM_REFRESH_CONTENT)
    hologramArgument("hologram", true)

    playerExecutor { player, arguments ->
        val hologram: Hologram? by arguments

        hologramService.refreshContent(hologram)

        if (hologram == null) {
            player.sendText {
                appendSuccessPrefix()
                success("Alle Hologramme wurden erneuert.")
            }
        } else {
            player.sendText {
                appendSuccessPrefix()
                success("Das Hologram ")
                variableValue(hologram?.metaData?.name ?: "*")
                success(" wurde erneuert.")
            }
        }
    }
}