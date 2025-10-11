package dev.slne.surf.hologram.paper.command.sub.hologram

import com.github.retrooper.packetevents.util.Vector3d
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.LocationType
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.locationArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.surf.hologram.api.command.argument.hologramOrientationTypeArgument
import dev.slne.surf.hologram.api.hologram.SimpleHologram
import dev.slne.surf.hologram.api.hologram.type.SimpleHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.hologram.util.HologramTextAlignment
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.command.argument.hologramNameArgument
import dev.slne.surf.hologram.paper.hologram.util.HologramHitboxImpl
import dev.slne.surf.hologram.paper.hologram.util.HologramMetaDataImpl
import dev.slne.surf.hologram.paper.hologram.util.location.HologramLocationImpl
import dev.slne.surf.hologram.paper.hologram.util.location.HologramWorldImpl
import dev.slne.surf.hologram.paper.util.HoloPermissionRegistry
import dev.slne.surf.surfapi.bukkit.api.command.args.miniMessageArgument
import dev.slne.surf.surfapi.core.api.messages.adventure.clickRunsCommand
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.random
import net.kyori.adventure.text.Component
import org.bukkit.Location

fun CommandAPICommand.hologramCreateCommand() = subcommand("create") {
    withPermission(HoloPermissionRegistry.COMMAND_HOLOGRAM_CREATE)

    hologramNameArgument("name")
    hologramOrientationTypeArgument("type")
    miniMessageArgument("text")
    locationArgument(
        "location",
        LocationType.BLOCK_POSITION,
        centerPosition = true,
        optional = true
    )

    playerExecutor { player, args ->
        val name: String by args
        val type: HologramOrientationType by args
        val location: Location? by args
        val text: Component by args
        val holoLocation = (location ?: player.location).let {
            HologramLocationImpl(
                it.x,
                it.y + 1,
                it.z,
                HologramWorldImpl(
                    it.world.name,
                    it.world.uid
                )
            )
        }

        val meta = HologramMetaDataImpl(
            name = name,
            holoEntityId = random.nextInt(),
            interactionEntityId = random.nextInt(),
            viewRange = 10f,
            lineWidth = 200,
            scale = Vector3d(1.0, 1.0, 1.0),
            textAlignment = HologramTextAlignment.CENTER,
            backgroundColor = null
        )

        val holo = hologramService.createHologram(
            meta,
            HologramOrientationType.ROTATING,
            holoLocation,
            text,
            HologramHitboxImpl.default(),
            null,
            SimpleHologram::class.java,
            SimpleHologramOptions()
        )
        holo.show()

        player.sendText {
            appendPrefix()
            success("Das Hologram ")
            variableValue(name)
            success(" wurde erstellt.")
            appendSpace()
            append {
                spacer("[")
                info("Teleportieren")
                spacer("]")
                clickRunsCommand("/hologram teleport-there $name")
            }
            appendSpace()
            append {
                spacer("[")
                error("Löschen")
                spacer("]")
                clickRunsCommand("/hologram delete $name")
            }
        }
    }
}