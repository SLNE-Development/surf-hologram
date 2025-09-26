package dev.slne.surf.hologram.example.dsl

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.hologram.api.dsl.hologram
import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.surfHologramApi
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.surfapi.core.api.messages.adventure.appendNewline
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import org.bukkit.Bukkit

class BukkitMain : SuspendingJavaPlugin() {
    override fun onEnable() {
        val world = Bukkit.getWorlds().first().let {
            surfHologramApi.createWorld(it.name, it.uid)
        }
        val location = surfHologramApi.createLocation(world, 0.0, 100.0, 0.0)
        val holo = hologram(this, "example_dsl_hologram", HologramType.ROTATING, location) {
            displayedText {
                info("Hello World!")
                appendNewline(2)
                info("This is an example hologram.")
            }

            //withViewer(player1) - You can add a specific viewer if you want
            //    withViewers {
            //        viewer(player2)
            //        viewers(listOf(player3, player4))
            //    } - or add multiple viewers in a block

            clickable =
                true // You can set clickable to false if you don't want to handle click events

            withEventHandler<HologramClickEvent> {
                val bukkitPlayer = it.player.bukkitPlayer ?: return@withEventHandler

                bukkitPlayer.sendText {
                    appendPrefix()
                    success("You clicked the example hologram!")
                }
            }
        }

        holo.show()
    }
}