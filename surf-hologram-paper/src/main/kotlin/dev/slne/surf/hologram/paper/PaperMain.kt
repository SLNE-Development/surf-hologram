package dev.slne.surf.hologram.paper

import com.github.retrooper.packetevents.PacketEvents
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.hologram.core.registry.hologramTypeRegistry
import dev.slne.surf.hologram.paper.command.hologramCommand
import dev.slne.surf.hologram.paper.command.surfHologramCommand
import dev.slne.surf.hologram.paper.hologram.BouncingHologramImpl
import dev.slne.surf.hologram.paper.hologram.UpdatingHologramImpl
import dev.slne.surf.hologram.paper.hologram.type.BouncingHologramType
import dev.slne.surf.hologram.paper.hologram.type.ScoreboardHologramType
import dev.slne.surf.hologram.paper.hologram.type.SimpleHologramType
import dev.slne.surf.hologram.paper.hologram.type.UpdatingHologramType
import dev.slne.surf.hologram.paper.listener.ConnectionListener
import dev.slne.surf.hologram.paper.listener.InternalEventListener
import dev.slne.surf.hologram.paper.listener.InternalEventPacketListener
import dev.slne.surf.hologram.paper.service.versionService
import dev.slne.surf.surfapi.bukkit.api.event.register
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override fun onLoad() {
        super.onLoad()
    }

    override fun onEnable() {
        InternalEventListener.register()
        ConnectionListener.register()
        PacketEvents.getAPI().eventManager.registerListener(InternalEventPacketListener())

        hologramCommand()
        surfHologramCommand()
        BouncingHologramImpl.startTicking()
        UpdatingHologramImpl.startUpdating()

        hologramTypeRegistry.register(SimpleHologramType())
        hologramTypeRegistry.register(BouncingHologramType())
        hologramTypeRegistry.register(ScoreboardHologramType())
        hologramTypeRegistry.register(UpdatingHologramType())

        launch {
            versionService.fetchGithubVersion()
        }
    }

    override fun onDisable() {
        BouncingHologramImpl.stopTicking()
        UpdatingHologramImpl.stopUpdating()
    }
}