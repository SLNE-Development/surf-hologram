package dev.slne.surf.hologram.example.dsl.command

import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.greedyStringArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.hologram.api.command.argument.hologramArgument
import dev.slne.surf.hologram.api.hologram.Hologram
import net.kyori.adventure.text.minimessage.MiniMessage

fun exampleHologramSetContentCommand() = commandAPICommand("examplehologramsetcontent") {
    hologramArgument("hologram")
    greedyStringArgument("content")
    playerExecutor { player, args ->
        val hologram: Hologram by args
        val content: String by args

        hologram.displayedText = MiniMessage.miniMessage().deserialize(content)

        player.sendText {
            appendSuccessPrefix()
            success("Das Hologram ")
            variableValue(hologram.metaData.name)
            success(" wurde aktualisiert.")
        }
    }
}