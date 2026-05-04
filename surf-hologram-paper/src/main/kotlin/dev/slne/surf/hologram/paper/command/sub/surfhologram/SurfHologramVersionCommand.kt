package dev.slne.surf.hologram.paper.command.sub.surfhologram

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.api.core.messages.adventure.clickOpensUrl
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.hologram.paper.plugin
import dev.slne.surf.hologram.paper.service.versionService
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry

fun CommandAPICommand.surfHologramVersionCommand() = subcommand("version") {
    withPermission(HoloPermissionRegistry.COMMAND_SURFHOLOGRAM_VERSION)
    anyExecutor { executor, _ ->
        plugin.launch {
            executor.sendText {
                appendNewline()
                appendInfoPrefix()
                if (versionService.isUpToDate()) info("Das Plugin ist up-to-date.") else variableKey(
                    "Es gibt eine neuere Version."
                )

                versionService.currentVersion.let {
                    appendNewline {
                        appendInfoPrefix()
                        variableKey("Aktuelle Version: ")
                        variableValue(it.toString())
                    }
                }

                versionService.latestVersion.let {
                    appendNewline {
                        appendInfoPrefix()
                        variableKey("Neueste Version: ")
                        variableValue(it.toString())
                    }
                }

                appendNewline {
                    appendInfoPrefix()
                    success("Neuste Version herunterladen: ")
                    variableValue("[DOWNLOAD]")
                    clickOpensUrl(
                        versionService.link
                            ?: "http://github.com/SLNE-Development/surf-hologram/releases/latest"
                    )
                }
            }
        }
    }
}