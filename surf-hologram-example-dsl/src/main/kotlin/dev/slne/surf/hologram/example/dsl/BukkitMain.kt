package dev.slne.surf.hologram.example.dsl

import com.github.retrooper.packetevents.util.Vector3d
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.core.messages.adventure.appendNewline
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.hologram.api.dsl.hologram
import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.api.hologram.BouncingHologram
import dev.slne.surf.hologram.api.hologram.FadingHologram
import dev.slne.surf.hologram.api.hologram.UpdatingHologram
import dev.slne.surf.hologram.api.hologram.type.BouncingHologramOptions
import dev.slne.surf.hologram.api.hologram.type.FadingHologramOptions
import dev.slne.surf.hologram.api.hologram.type.UpdatingHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.hologramConversationUtil
import dev.slne.surf.hologram.api.hologramWorld
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.example.dsl.command.exampleHologramSetContentCommand
import org.bukkit.Bukkit
import org.bukkit.Location

class BukkitMain : SuspendingJavaPlugin() {
    override fun onEnable() {
        exampleHologramSetContentCommand()

        logger.warning(
            "You are running the example hologram plugin. This is only for demonstration purposes and should not be used in production. " +
                    "Example Commands may don't have permissions."
        )

        // --- Bouncing hologram using HologramLocation ---
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

            hitbox {
                width = 1.0f
                height = 1.0f
                hologramCenterOffset = Vector3d.zero()
            }

            withEventHandler<HologramClickEvent> {
                val bukkitPlayer = it.player.bukkitPlayer ?: return@withEventHandler

                bukkitPlayer.sendText {
                    appendSuccessPrefix()
                    success("You clicked the example hologram!")
                }
            }
        }

        // --- Updating hologram using HologramLocation ---
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

            hitbox {
                width = 1.0f
                height = 1.0f
                hologramCenterOffset = Vector3d.zero()
            }

            withEventHandler<HologramClickEvent> {
                val bukkitPlayer = it.player.bukkitPlayer ?: return@withEventHandler

                bukkitPlayer.sendText {
                    appendSuccessPrefix()
                    success("You clicked the example updating hologram!")
                }
            }
        }

        // --- Fading hologram with TTL using Bukkit Location directly ---
        val firstWorld = Bukkit.getWorlds().first()
        val fadingHolo = hologram<FadingHologram, FadingHologramOptions>(
            "example_dsl_hologram_fading",
            FadingHologram::class.java,
            HologramOrientationType.FIXED,
            Location(firstWorld, 10.0, 100.0, 10.0)
        ) {
            displayedText {
                success("+100 Points!")
            }

            options {
                fadeSpeed = 0.05
                fadeMaxOffset = 3.0
            }

            // TTL of 5 seconds as a safety net (the fading animation removes it first)
            ttl = 5_000L
        }

        updatingHolo.show()
        holo.show()
        fadingHolo.show()
    }
}