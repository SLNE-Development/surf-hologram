package dev.slne.surf.hologram.paper.command.sub.surfhologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry

fun CommandAPICommand.surfHologramDebugCommand() = subcommand("debug") {
    withPermission(HoloPermissionRegistry.COMMAND_SURFHOLOGRAM_DEBUG)

    surfHologramCreateDebugCommand()
    surfHologramScoreboardDebugCommand()
    surfHologramDebugDeleteCommand()
    surfHologramDebugTooltipCommand()
}