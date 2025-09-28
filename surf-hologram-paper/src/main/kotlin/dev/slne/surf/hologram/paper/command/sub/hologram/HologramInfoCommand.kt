package dev.slne.surf.hologram.paper.command.sub.hologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.hologram.BouncingHologram
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.paper.command.argument.hologramArgument
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.messages.CommonComponents
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import net.kyori.adventure.text.format.TextDecoration

fun CommandAPICommand.hologramInfoCommand() = subcommand("info") {
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM_INFO)
    hologramArgument("hologram")
    playerExecutor { player, args ->
        val hologram: Hologram by args

        player.sendText {
            primary("Hologram-Informationen für ".toSmallCaps(), TextDecoration.BOLD)
            variableKey(hologram.metaData.name, TextDecoration.BOLD)

            appendNewline {
                append(CommonComponents.EM_DASH)
                appendSpace()
                variableKey("Typ")
                spacer(":")
                appendSpace()
                variableValue(hologram.hologramOrientationType.name)
            }
            appendNewline {
                append(CommonComponents.EM_DASH)
                appendSpace()
                variableKey("Location")
                spacer(":")
                appendSpace()
                variableValue(hologram.centerLocation.toString())
            }
            appendNewline {
                append(CommonComponents.EM_DASH)
                appendSpace()
                variableKey("View Range")
                spacer(":")
                appendSpace()
                variableValue(hologram.metaData.viewRange)
            }
            appendNewline {
                append(CommonComponents.EM_DASH)
                appendSpace()
                variableKey("Background Color")
                spacer(":")
                appendSpace()
                variableValue(
                    hologram.metaData.backgroundColor?.asHexString() ?: "Durchsichtig"
                )
            }
            appendNewline {
                append(CommonComponents.EM_DASH)
                appendSpace()
                variableKey("Text Alignment")
                spacer(":")
                appendSpace()
                variableValue(hologram.metaData.textAlignment.name)
            }
            appendNewline {
                append(CommonComponents.EM_DASH)
                appendSpace()
                variableKey("Ersteller")
                spacer(":")
                appendSpace()
                variableValue(hologram.creationReason.name())
            }

            if (hologram is BouncingHologram) {
                val bouncingHologram = hologram as BouncingHologram
                appendNewline {
                    append(CommonComponents.EM_DASH)
                    appendSpace()
                    variableKey("Bounce-Höhe")
                    spacer(":")
                    appendSpace()
                    variableValue(bouncingHologram.bounceHeight)
                }
                appendNewline {
                    append(CommonComponents.EM_DASH)
                    appendSpace()
                    variableKey("Bounce-Step")
                    spacer(":")
                    appendSpace()
                    variableValue(bouncingHologram.bounceSpeed)
                }
                appendNewline {
                    append(CommonComponents.EM_DASH)
                    appendSpace()
                    variableKey("Bounce-Richtung")
                    spacer(":")
                    appendSpace()
                    variableValue(bouncingHologram.bounceDirection.let {
                        if (it) "Nach oben" else "Nach unten"
                    })
                }

            }
        }
    }
}