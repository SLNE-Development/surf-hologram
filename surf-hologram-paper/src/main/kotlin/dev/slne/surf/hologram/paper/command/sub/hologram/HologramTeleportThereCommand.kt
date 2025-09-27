package dev.slne.surf.hologram.paper.command.sub.hologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.paper.command.argument.hologramArgument
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.hologram.paper.util.holoPlayer
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun CommandAPICommand.hologramTeleportThereCommand() = subcommand("teleport-there") {
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM_TELEPORT_THERE)
    hologramArgument("hologram")

    playerExecutor { player, arguments ->
        val hologram: Hologram by arguments
        hologram.teleportHere(player.holoPlayer)

        player.sendText {
            appendPrefix()
            success("Du wurdest zum Hologramm ")
            variableValue(hologram.metaData.name)
            success(" teleportiert.")
        }
    }
}