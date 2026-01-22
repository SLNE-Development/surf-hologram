package dev.slne.surf.hologram.api.command.argument

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.CustomArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.slne.surf.hologram.api.hologram.Hologram
import dev.slne.surf.hologram.api.surfHologramApi
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText

class HologramArgument(nodeName: String) :
    CustomArgument<Hologram, String>(StringArgument(nodeName), { info ->
        surfHologramApi.getHologram(info.input)
            ?: throw CustomArgumentException.fromAdventureComponent(
                buildText {
                    appendErrorPrefix()
                    error("Das Hologram wurde nicht gefunden.")
                })
    }) {
    init {
        replaceSuggestions(ArgumentSuggestions.stringCollection {
            surfHologramApi.all().map { it.metaData.name }
        })
    }
}

inline fun CommandAPICommand.hologramArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): CommandAPICommand =
    withArguments(HologramArgument(nodeName).setOptional(optional).apply(block))