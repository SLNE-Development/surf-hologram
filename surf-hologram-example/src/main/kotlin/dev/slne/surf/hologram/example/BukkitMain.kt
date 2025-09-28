package dev.slne.surf.hologram.example

import com.github.retrooper.packetevents.util.Vector3d
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.hologram.api.event.impl.HologramClickEvent
import dev.slne.surf.hologram.api.hologram.SimpleHologram
import dev.slne.surf.hologram.api.hologram.type.SimpleHologramOptions
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.hologramConversationUtil
import dev.slne.surf.hologram.api.hologramWorld
import dev.slne.surf.hologram.api.surfHologramApi
import dev.slne.surf.hologram.api.util.addEventHandler
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.surfapi.core.api.messages.adventure.appendNewline
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import org.bukkit.Bukkit

class BukkitMain : SuspendingJavaPlugin() {
    override fun onEnable() {
        val meta = hologramConversationUtil.createHologramMeta(
            "example_hologram"
        )
        val world = Bukkit.getWorlds().first().hologramWorld
        val hitbox = hologramConversationUtil.createHitbox(1.0f, 1.0f, Vector3d.zero())

        val holo = surfHologramApi.createHologram(
            this,
            meta,
            HologramOrientationType.ROTATING,
            hologramConversationUtil.createLocation(world, 0.0, 100.0, 0.0),
            buildText {
                info("Hello World!")
                appendNewline(2)
                info("This is an example hologram.")
            },
            hitbox,
            null,
            SimpleHologram::class.java,
            SimpleHologramOptions()
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