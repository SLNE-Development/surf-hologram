package dev.slne.surf.hologram.paper.command

import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.hologram.paper.command.sub.hologramCreateCommand
import dev.slne.surf.hologram.paper.command.sub.hologramDeleteCommand
import dev.slne.surf.hologram.paper.dialog.createHologramCreateDialog
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry

fun hologramCommand() = commandAPICommand("hologram") {
    withAliases("holo", "hg")
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM)

    hologramCreateCommand()
    hologramDeleteCommand()

    playerExecutor { player, _ ->
        player.showDialog(createHologramCreateDialog())
    }
}