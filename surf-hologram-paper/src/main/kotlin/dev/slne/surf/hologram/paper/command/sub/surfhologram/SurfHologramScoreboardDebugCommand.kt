package dev.slne.surf.hologram.paper.command.sub.surfhologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.hologram.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.surfHologramApi
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.hologram.HologramHitboxImpl
import dev.slne.surf.hologram.paper.hologram.types.ScoreboardHologramImpl
import dev.slne.surf.hologram.paper.plugin
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.hologram.paper.util.toHologramLocation
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.objectListOf
import dev.slne.surf.surfapi.core.api.util.random

fun CommandAPICommand.surfHologramScoreboardDebugCommand() = subcommand("scoreboard") {
    withPermission(HoloPermissionRegistry.COMMAND_SURFHOLOGRAM_DEBUG_SCOREBOARD)
    playerExecutor { player, _ ->

        val scoreboardHologram = ScoreboardHologramImpl(
            surfHologramApi.buildMetaData("debug-scoreboard-${random.nextInt(1, 99999)}"),
            HologramType.FIXED,
            player.location.toHologramLocation(),
            buildText { error("Loading data...") },
            HologramCreationReason.Plugin(plugin.name),
            HologramHitboxImpl.default(),
            null,
            1..10,
            "P"
        )

        val names = objectListOf(
            buildText {
                info("TheBjoRedCraft")
            },
            buildText {
                info("NotAmmo")
            },
            buildText {
                info("Keviro")
            },
            buildText {
                info("Twisti_Twixi")
            },
            buildText {
                info("Timonso")
            },
            buildText {
                info("Koljav")
            },
            buildText {
                info("Floweryalina")
            },
            buildText {
                info("_Danilo")
            },
            buildText {
                info("Dorlino_")
            },
            buildText {
                info("PEKK29")
            }
        )

        val entries = scoreboardHologram.placementRange.map {
            val name = names.random()
            val score = random.nextInt(1, 100)

            name to score
        }

        var currentPlacement = 1

        entries.sortedByDescending { it.second }.forEach {
            scoreboardHologram.addEntry(
                placement = currentPlacement,
                name = it.first,
                score = it.second
            )
            currentPlacement++
        }

        val holo = hologramService.createHologram(scoreboardHologram)
        holo.show()

        player.sendText {
            appendPrefix()
            success("Es wurde ein Scoreboard-Hologramm erstellt.")
        }
    }
}