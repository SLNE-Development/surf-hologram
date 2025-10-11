package dev.slne.surf.hologram.paper.hook

import io.github.miniplaceholders.api.MiniPlaceholders
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object MiniPlaceholdersHook {
    private fun isEnabled() = Bukkit.getPluginManager().isPluginEnabled("MiniPlaceholders")

    fun parse(player: Player, input: Component): Component {
        if (!this.isEnabled()) {
            return input
        }
        
        val miniMessage = MiniMessage.miniMessage().serialize(input)
        val resolver = MiniPlaceholders.audienceGlobalPlaceholders()
        return MiniMessage.miniMessage().deserialize(miniMessage, player, resolver)
    }
}