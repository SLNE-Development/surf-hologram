package dev.slne.surf.hologram.paper.command.sub.surfhologram

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.hologramConversationUtil
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.paper.hologram.ScoreboardHologramImpl
import dev.slne.surf.hologram.paper.hologram.util.HologramHitboxImpl
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
            hologramConversationUtil.createHologramMeta(
                "debug-scoreboard-${
                    random.nextInt(
                        1,
                        99999
                    )
                }"
            ),
            HologramOrientationType.FIXED,
            player.location.clone().add(0.4, 0.0, 0.0).toHologramLocation(),
            buildText { error("Loading data...") },
            HologramHitboxImpl.default(),
            null,
            1..10,
            "P"
        )

        val names = objectListOf(
            buildText {
                text("TheBjoRedCraft")
            },
            buildText {
                text("NotAmmo")
            },
            buildText {
                text("Keviro")
            },
            buildText {
                text("Twisti_Twixi")
            },
            buildText {
                text("Timonso")
            },
            buildText {
                text("Koljav")
            },
            buildText {
                text("Floweryalina")
            },
            buildText {
                text("_Danilo")
            },
            buildText {
                text("Dorlino_")
            },
            buildText {
                text("PEKK29")
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

        hologramRegistry.registerHologram(scoreboardHologram)
        scoreboardHologram.show()

        player.sendText {
            appendPrefix()
            success("Es wurde ein Scoreboard-Hologramm erstellt.")
        }
    }
}