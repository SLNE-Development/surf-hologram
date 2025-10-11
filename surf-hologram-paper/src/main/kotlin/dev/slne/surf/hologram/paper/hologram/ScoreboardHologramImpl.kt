package dev.slne.surf.hologram.paper.hologram

import dev.slne.surf.hologram.api.hologram.ScoreboardHologram
import dev.slne.surf.hologram.api.hologram.ScoreboardHologramEntry
import dev.slne.surf.hologram.api.hologram.location.HologramLocation
import dev.slne.surf.hologram.api.hologram.util.HologramHitbox
import dev.slne.surf.hologram.api.hologram.util.HologramMetaData
import dev.slne.surf.hologram.api.hologram.util.HologramOrientationType
import dev.slne.surf.hologram.api.player.HoloOfflinePlayer
import dev.slne.surf.hologram.core.registry.hologramRegistry
import dev.slne.surf.hologram.paper.plugin
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import dev.slne.surf.surfapi.core.api.util.random
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

class ScoreboardHologramImpl(
    override val metaData: HologramMetaData,
    override var hologramOrientationType: HologramOrientationType,
    override var centerLocation: HologramLocation,
    override var displayedText: Component,
    override val hitbox: HologramHitbox?,
    override val viewers: ObjectSet<HoloOfflinePlayer>?,
    override val placementRange: IntRange,
    override val scoreUnit: String
) : BaseHologram(), ScoreboardHologram {
    private val _placements = mutableObjectSetOf<ScoreboardHologramEntry>()

    override fun update() {
        displayedText = buildText {
            for (placement in placementRange) {
                append(buildLine(placement))
                appendNewline()
            }
        }
        refreshContent()
    }

    override fun addEntry(placement: Int, name: Component, score: Int) = _placements.add(
        ScoreboardHologramEntryImpl(placement, name, score)
    )

    override fun removeEntry(placement: Int) = _placements.removeIf { it.placement == placement }
    override fun clearEntries() = _placements.clear()

    private fun buildLine(placement: Int) = buildText {
        _placements.firstOrNull { it.placement == placement }?.let {
            info("#")
            info(placement)
            appendSpace()
            spacer("-")
            appendSpace()
            append(it.name)
            appendSpace()
            spacer("-")
            appendSpace()
            variableValue("${it.score}$scoreUnit")
        } ?: {
            variableValue(placement)
            spacer(".")
            appendSpace()
        }
    }

    override fun duplicate(spawnable: Boolean) = ScoreboardHologramImpl(
        if (spawnable) metaData.duplicate().apply {
            name = "$name-DUPLICATE-${(1..1000).random()}"
            holoEntityId = random.nextInt()
            interactionEntityId = random.nextInt()
        } else metaData,
        hologramOrientationType,
        centerLocation,
        displayedText,
        hitbox,
        viewers,
        placementRange,
        scoreUnit
    ).apply {
        _placements.addAll(this@ScoreboardHologramImpl._placements)
    }

    companion object {
        private lateinit var updateTask: ScheduledTask

        @Deprecated("Only for testing purposes")
        fun startUpdating() {
            if (::updateTask.isInitialized && !updateTask.isCancelled) {
                return
            }

            updateTask = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                hologramRegistry.holograms()
                    .forEach { (it as? ScoreboardHologram)?.update() }
            }, 0L, 10L, TimeUnit.SECONDS)
        }

        @Deprecated("Only for testing purposes")
        fun stopUpdating() {
            if (::updateTask.isInitialized && !updateTask.isCancelled) {
                updateTask.cancel()
            }
        }
    }
}

data class ScoreboardHologramEntryImpl(
    override val placement: Int,
    override val name: Component,
    override val score: Int
) : ScoreboardHologramEntry