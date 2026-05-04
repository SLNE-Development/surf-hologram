package dev.slne.surf.hologram.paper.util

import dev.slne.surf.api.paper.permission.PermissionRegistry

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
    val COMMAND_HOLOGRAM_REFRESH_CONTENT = create("$COMMAND_BASE.hologram.refreshcontent")

    val COMMAND_SURFHOLOGRAM = create("$COMMAND_BASE.surfhologram")
    val COMMAND_SURFHOLOGRAM_VERSION = create("$COMMAND_BASE.surfhologram.version")
    val COMMAND_SURFHOLOGRAM_DEBUG = create("$COMMAND_BASE.surfhologram.debug")
    val COMMAND_SURFHOLOGRAM_DEBUG_CREATE = create("$COMMAND_BASE.surfhologram.debug.create")
    val COMMAND_SURFHOLOGRAM_DEBUG_SCOREBOARD =
        create("$COMMAND_BASE.surfhologram.debug.scoreboard")
    val COMMAND_SURFHOLOGRAM_DEBUG_DELETE = create("$COMMAND_BASE.surfhologram.debug.delete")
    val COMMAND_SURFHOLOGRAM_DEBUG_TOOLTIP = create("$COMMAND_BASE.surfhologram.debug.tooltip")
}