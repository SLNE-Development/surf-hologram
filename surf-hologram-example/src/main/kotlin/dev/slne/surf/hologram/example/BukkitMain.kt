package dev.slne.surf.hologram.example

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.surfHologramApi
import dev.slne.surf.hologram.api.util.addEventHandler
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.surfapi.core.api.messages.adventure.appendNewline
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import org.bukkit.Bukkit

class BukkitMain : SuspendingJavaPlugin() {
    override fun onEnable() {
        val meta = surfHologramApi.buildMetaData(
            "example_hologram"
        )
        val world = Bukkit.getWorlds().first().let {
            surfHologramApi.createWorld(it.name, it.uid)
        }
        val holo = surfHologramApi.createHologram(
            HologramType.ROTATING,
            meta,
            surfHologramApi.createLocation(world, 0.0, 100.0, 0.0),
            buildText {
                info("Hello World!")
                appendNewline(2)
                info("This is an example hologram.")
            }
        )

        holo.addEventHandler<HologramClickEvent> {
            val bukkitPlayer = it.player.bukkitPlayer ?: return@addEventHandler

            bukkitPlayer.sendText {
                appendPrefix()
                success("You clicked the example hologram!")
            }
        }

        holo.show()
    }
}