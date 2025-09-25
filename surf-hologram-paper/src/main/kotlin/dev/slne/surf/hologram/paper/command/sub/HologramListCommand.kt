package dev.slne.surf.hologram.paper.command.sub

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.messages.CommonComponents
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.plain
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.messages.pagination.Pagination
import net.kyori.adventure.text.format.TextDecoration

fun CommandAPICommand.hologramListCommand() = subcommand("list") {
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM_LIST)
    playerExecutor { player, _ ->
        val holograms = hologramRegistry.holograms()

        if (holograms.isEmpty()) {
            player.sendText {
                appendPrefix()
                error("Es wurden keine Hologramme gefunden.")
            }
            return@playerExecutor
        }

        val pagination = Pagination<Hologram> {
            title { primary("Registrierte Hologramme".toSmallCaps(), TextDecoration.BOLD) }
            rowRenderer { row, _ ->
                listOf(
                    buildText {
                        append(CommonComponents.EM_DASH)
                        appendSpace()
                        variableKey(row.metaData.name)
                        spacer(":")
                        appendSpace()
                        variableValue(row.displayedText.plain().take(25))

                        hoverEvent(buildText {
                            appendNewline {
                                append(CommonComponents.EM_DASH)
                                appendSpace()
                                variableKey("Name")
                                spacer(":")
                                variableValue(row.metaData.name)
                            }
                            appendNewline {
                                append(CommonComponents.EM_DASH)
                                appendSpace()
                                variableKey("Typ")
                                spacer(":")
                                variableValue(row.hologramType.name)
                            }
                            appendNewline {
                                append(CommonComponents.EM_DASH)
                                appendSpace()
                                variableKey("Location")
                                spacer(":")
                                variableValue(row.centerLocation.toString())
                            }
                            appendNewline {
                                append(CommonComponents.EM_DASH)
                                appendSpace()
                                variableKey("View Range")
                                spacer(":")
                                variableValue(row.metaData.viewRange)
                            }
                            appendNewline {
                                append(CommonComponents.EM_DASH)
                                appendSpace()
                                variableKey("Background Color")
                                spacer(":")
                                variableValue(
                                    row.metaData.backgroundColor?.asHexString() ?: "Durchsichtig"
                                )
                            }
                            appendNewline {
                                append(CommonComponents.EM_DASH)
                                appendSpace()
                                variableKey("Text Alignment")
                                spacer(":")
                                variableValue(row.metaData.textAlignment.name)
                            }
                        })
                    }
                )
            }
        }

        player.sendText {
            append(pagination.renderComponent(holograms))
        }
    }
}