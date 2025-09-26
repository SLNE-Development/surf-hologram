package dev.slne.surf.hologram.paper

import com.github.retrooper.packetevents.PacketEvents
import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.command.hologramCommand
import dev.slne.surf.hologram.paper.listener.InternalEventListener
import dev.slne.surf.hologram.paper.listener.InternalEventPacketListener
import dev.slne.surf.surfapi.bukkit.api.event.register
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override fun onLoad() {
        super.onLoad()
    }

    override fun onEnable() {
        InternalEventListener.register()
        PacketEvents.getAPI().eventManager.registerListener(InternalEventPacketListener())

        hologramCommand()

        hologramService.startBouncing()
    }

    override fun onDisable() {
        hologramService.stopBouncing()
    }
}