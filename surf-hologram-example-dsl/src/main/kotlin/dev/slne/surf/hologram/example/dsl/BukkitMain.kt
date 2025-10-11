package dev.slne.surf.hologram.example.dsl

import com.github.retrooper.packetevents.util.Vector3d
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.hologram.api.dsl.hologram
import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.api.hologram.BouncingHologram
import dev.slne.surf.hologram.api.hologram.UpdatingHologram
import dev.slne.surf.hologram.api.hologram.type.BouncingHologramOptions
import dev.slne.surf.hologram.api.hologram.type.UpdatingHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.hologramConversationUtil
import dev.slne.surf.hologram.api.hologramWorld
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.example.dsl.command.exampleHologramSetContentCommand
import dev.slne.surf.surfapi.core.api.messages.adventure.appendNewline
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import org.bukkit.Bukkit

class BukkitMain : SuspendingJavaPlugin() {
    override fun onEnable() {
        exampleHologramSetContentCommand()

        logger.warning(
            "You are running the example hologram plugin. This is only for demonstration purposes and should not be used in production. " +
                    "Example Commands may don't have permissions."
        )

        val location = hologramConversationUtil.createLocation(
            Bukkit.getWorlds().first().hologramWorld,
            0.0,
            100.0,
            0.0
        )
        val holo = hologram<BouncingHologram, BouncingHologramOptions>(
            "example_dsl_hologram",
            BouncingHologram::class.java,
            HologramOrientationType.ROTATING,
            location
        ) {
            displayedText {
                info("Hello World!")
                appendNewline(2)
                info("This is an example hologram.")
            }

            options {
                bounceHeight = 1.0
                bounceSpeed = 0.1
            }

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

        val updatingHoloLocation = hologramConversationUtil.createLocation(
            Bukkit.getWorlds().first().hologramWorld,
            5.0,
            100.0,
            5.0
        )

        val updatingHolo = hologram<UpdatingHologram, UpdatingHologramOptions>(
            "example_dsl_hologram_updating",
            UpdatingHologram::class.java,
            HologramOrientationType.ROTATING,
            updatingHoloLocation
        ) {
            displayedText {
                info("Hello World!")
                appendNewline(2)
                info("This is an example hologram.")
            }

            options {
                updateInterval = 3000L
            }

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
                    success("You clicked the example updating hologram!")
                }
            }
        }

        updatingHolo.show()
        holo.show()
    }
}