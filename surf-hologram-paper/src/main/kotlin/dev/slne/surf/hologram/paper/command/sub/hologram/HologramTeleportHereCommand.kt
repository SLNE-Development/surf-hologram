package dev.slne.surf.hologram.paper.command.sub.hologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.paper.command.argument.hologramArgument
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.hologram.paper.util.toHologramLocation
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun CommandAPICommand.hologramTeleportHereCommand() = subcommand("teleport-here") {
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM_TELEPORT_HERE)
    hologramArgument("hologram")

    playerExecutor { player, arguments ->
        val hologram: Hologram by arguments
        hologram.teleportTo(player.location.toHologramLocation())

        player.sendText {
            appendPrefix()
            success("Das Hologram ")
            variableValue(hologram.metaData.name)
            success(" wurde zu dir teleportiert.")
        }
    }
}