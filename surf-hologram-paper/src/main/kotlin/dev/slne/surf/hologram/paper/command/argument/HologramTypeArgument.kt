package dev.slne.surf.hologram.paper.command.argument

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.CustomArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType

class HologramTypeArgument(nodeName: String) :
    CustomArgument<HologramOrientationType, String>(StringArgument(nodeName), { info ->
        HologramOrientationType.valueOf(info.input.uppercase())
    }) {
    init {
        replaceSuggestions(ArgumentSuggestions.stringCollection {
            HologramOrientationType.entries.map { it.name.lowercase() }
        })
    }
}

inline fun CommandAPICommand.hologramTypeArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): CommandAPICommand =
    withArguments(HologramTypeArgument(nodeName).setOptional(optional).apply(block))