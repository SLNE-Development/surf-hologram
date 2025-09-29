package dev.slne.surf.hologram.paper.command.sub.surfhologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import net.kyori.adventure.text.event.ClickEvent

fun CommandAPICommand.surfHologramDebugDeleteCommand() = subcommand("delete") {
    withPermission(HoloPermissionRegistry.COMMAND_SURFHOLOGRAM_DEBUG_DELETE)
    playerExecutor { player, _ ->
        val debugHolograms =
            hologramRegistry.holograms().filter { it.metaData.name.startsWith("debug-") }

        if (debugHolograms.isEmpty()) {
            player.sendText {
                appendPrefix()
                error("Es wurden keine Debug-Hologramme gefunden.")
            }
            return@playerExecutor
        }

        player.sendText {
            appendPrefix()
            info("Möchtest du wirklich ")
            variableValue(debugHolograms.size)
            info(" Debug-Hologramm(e) löschen? ")
            spacer("[")
            success("Bestätigen")
            spacer("]")
            hoverEvent(buildText {
                info("Klicke um alle Debug-Hologramme zu löschen.")
            })

            clickEvent(ClickEvent.callback {
                debugHolograms.forEach {
                    hologramService.deleteHologram(it)
                }

                player.sendText {
                    appendPrefix()
                    success("Es wurde erfolgreich ")
                    variableValue(debugHolograms.size)
                    success(" Debug-Hologramm(e) gelöscht.")
                }
            })
        }
    }
}