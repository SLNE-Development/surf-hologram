package dev.slne.surf.hologram.api.hologram.types.type

import dev.slne.surf.hologram.api.hologram.Hologram

data class HologramType(
    val id: String,
    val hologramClazz: Class<out Hologram>
)