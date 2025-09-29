package dev.slne.surf.hologram.paper.command

import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.slne.surf.hologram.paper.command.sub.surfhologram.surfHologramDebugCommand
import dev.slne.surf.hologram.paper.command.sub.surfhologram.surfHologramVersionCommand
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry

fun surfHologramCommand() = commandAPICommand("surfhologram") {
    withAliases("sh")
    withPermission(HoloPermissionRegistry.COMMAND_SURFHOLOGRAM)

    surfHologramDebugCommand()
    surfHologramVersionCommand()
}