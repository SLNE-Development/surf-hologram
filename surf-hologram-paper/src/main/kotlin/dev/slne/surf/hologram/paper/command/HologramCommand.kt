package dev.slne.surf.hologram.paper.command

import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.slne.surf.hologram.paper.command.sub.hologramCreateCommand
import dev.slne.surf.hologram.paper.command.sub.hologramDeleteCommand
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry

fun hologramCommand() = commandAPICommand("hologram") {
    withAliases("holo", "hg")
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM)

    hologramCreateCommand()
    hologramDeleteCommand()
}