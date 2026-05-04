package dev.slne.surf.hologram.paper

import com.github.retrooper.packetevents.PacketEvents
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.api.paper.event.register
import dev.slne.surf.hologram.core.registry.hologramTypeRegistry
import dev.slne.surf.hologram.paper.command.hologramCommand
import dev.slne.surf.hologram.paper.command.surfHologramCommand
import dev.slne.surf.hologram.paper.hologram.*
import dev.slne.surf.hologram.paper.hologram.type.*
import dev.slne.surf.hologram.paper.listener.ConnectionListener
import dev.slne.surf.hologram.paper.listener.InternalEventListener
import dev.slne.surf.hologram.paper.listener.InternalEventPacketListener
import dev.slne.surf.hologram.paper.service.versionService
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
        FadingHologramImpl.startTicking()
        TooltipHologramImpl.startTicking()
        UpdatingHologramImpl.startUpdating()
        ScoreboardHologramImpl.startUpdating()
        TtlScheduler.start()

        hologramTypeRegistry.register(SimpleHologramType())
        hologramTypeRegistry.register(BouncingHologramType())
        hologramTypeRegistry.register(FadingHologramType())
        hologramTypeRegistry.register(ScoreboardHologramType())
        hologramTypeRegistry.register(UpdatingHologramType())
        hologramTypeRegistry.register(TooltipHologramType())

        launch {
            versionService.fetchGithubVersion()
        }
    }

    override fun onDisable() {
        BouncingHologramImpl.stopTicking()
        FadingHologramImpl.stopTicking()
        TooltipHologramImpl.stopTicking()
        UpdatingHologramImpl.stopUpdating()
        ScoreboardHologramImpl.stopUpdating()
        TtlScheduler.stop()
    }
}