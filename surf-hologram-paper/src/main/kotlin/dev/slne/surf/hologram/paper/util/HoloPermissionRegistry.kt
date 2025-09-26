package dev.slne.surf.hologram.paper.util

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object HoloPermissionRegistry : PermissionRegistry() {
    const val PREFIX = "surf.hologram"
    const val COMMAND_BASE = "$PREFIX.command"

    val COMMAND_HOLOGRAM = create("$COMMAND_BASE.hologram")
    val COMMAND_HOLOGRAM_CREATE = create("$COMMAND_BASE.hologram.create")
    val COMMAND_HOLOGRAM_DELETE = create("$COMMAND_BASE.hologram.delete")
    val COMMAND_HOLOGRAM_LIST = create("$COMMAND_BASE.hologram.list")
    val COMMAND_HOLOGRAM_TELEPORT_THERE = create("$COMMAND_BASE.hologram.teleport.there")
    val COMMAND_HOLOGRAM_TELEPORT_HERE = create("$COMMAND_BASE.hologram.teleport.here")
    val COMMAND_HOLOGRAM_INFO = create("$COMMAND_BASE.hologram.info")
    val COMMAND_HOLOGRAM_REFRESH = create("$COMMAND_BASE.hologram.refresh")
    val COMMAND_HOLOGRAM_DEBUG_CREATE = create("$COMMAND_BASE.hologram.debug.create")
}