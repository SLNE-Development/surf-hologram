package dev.slne.surf.hologram.paper.command

import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.hologram.paper.command.sub.*
import dev.slne.surf.hologram.paper.dialog.createHologramCreateDialog
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry

fun hologramCommand() = commandAPICommand("hologram") {
    withAliases("holo", "hg")
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM)

    hologramCreateCommand()
    hologramDeleteCommand()
    hologramListCommand()
    hologramInfoCommand()
    hologramRefreshCommand()

    hologramTeleportHereCommand()
    hologramTeleportThereCommand()

    playerExecutor { player, _ ->
        player.showDialog(createHologramCreateDialog())
    }
}