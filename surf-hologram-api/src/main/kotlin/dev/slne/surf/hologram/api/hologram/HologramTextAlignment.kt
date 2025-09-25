package dev.slne.surf.hologram.api.hologram

enum class HologramTextAlignment(val bytes: Byte) {
    CENTER((0x08 or 0).toByte()),
    LEFT((0x09 or 1).toByte()),
    RIGHT((0x10 or 2).toByte())
}