package dev.slne.surf.hologram.api.hologram.types

import dev.slne.surf.hologram.api.hologram.Hologram
import net.kyori.adventure.text.Component

interface ScoreboardHologram : Hologram {
    val placementRange: IntRange
    val scoreUnit: String

    fun update()

    fun addEntry(placement: Int, name: Component, score: Int): Boolean
    fun removeEntry(placement: Int): Boolean
    fun clearEntries()
}

interface ScoreboardHologramEntry {
    val placement: Int
    val name: Component
    val score: Int
}