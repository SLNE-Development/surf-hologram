package dev.slne.surf.hologram.example.dsl

import com.github.retrooper.packetevents.util.Vector3d
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.hologram.api.dsl.hologram
import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.api.hologram.SimpleHologram
import dev.slne.surf.hologram.api.hologram.type.SimpleHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
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
        val type = surfHologramApi.getHologramType<SimpleHologram, SimpleHologramOptions>(
            SimpleHologram::class.java
        ) ?: error("SimpleHologram type not found")
        val holo = hologram(
            this,
            "example_dsl_hologram",
            type,
            HologramOrientationType.ROTATING,
            location
        ) {
            displayedText {
                info("Hello World!")
                appendNewline(2)
                info("This is an example hologram.")
            }

            options {}

            //withViewer(player1) - You can add a specific viewer if you want
            //    withViewers {
            //        viewer(player2)
            //        viewers(listOf(player3, player4))
            //    } - or add multiple viewers in a block


            hitbox {
                width = 1.0f
                height = 1.0f
                hologramCenterOffset = Vector3d.zero()
            }

            withEventHandler<HologramClickEvent> {
                val bukkitPlayer = it.player.bukkitPlayer ?: return@withEventHandler

                bukkitPlayer.sendText {
                    appendPrefix()
                    success("You clicked the example hologram!")
                }
            }
        }

        println("Created example hologram with ID: ${holo.metaData.name}")

        holo.show()
    }
}