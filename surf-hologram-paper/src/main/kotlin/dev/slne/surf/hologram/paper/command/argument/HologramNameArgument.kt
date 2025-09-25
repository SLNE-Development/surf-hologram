package dev.slne.surf.hologram.paper.command.argument

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.CustomArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText

class HologramNameArgument(nodeName: String) :
    CustomArgument<String, String>(StringArgument(nodeName), { info ->
        if (info.input.contains(" ")) {
            throw CustomArgumentException.fromAdventureComponent(buildText {
                appendPrefix()
                error("Der Name darf keine Leerzeichen enthalten.")
            })
        } else {
            info.input
        }
    })

inline fun CommandAPICommand.hologramNameArgument(
    nodeName: String,
    optional: Boolean = false,
    block: Argument<*>.() -> Unit = {}
): CommandAPICommand =
    withArguments(HologramNameArgument(nodeName).setOptional(optional).apply(block))