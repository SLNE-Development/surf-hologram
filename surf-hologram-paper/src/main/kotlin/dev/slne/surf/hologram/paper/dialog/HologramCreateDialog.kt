@file:Suppress("UnstableApiUsage")

package dev.slne.surf.hologram.paper.dialog

import dev.slne.surf.hologram.api.hologram.HologramCreationReason
import dev.slne.surf.hologram.api.hologram.HologramTextAlignment
import dev.slne.surf.hologram.api.hologram.HologramType
import dev.slne.surf.hologram.api.util.show
import dev.slne.surf.hologram.core.service.hologramService
import dev.slne.surf.hologram.paper.hologram.HologramMetaDataImpl
import dev.slne.surf.hologram.paper.util.toHologramLocation
import dev.slne.surf.surfapi.bukkit.api.dialog.base
import dev.slne.surf.surfapi.bukkit.api.dialog.dialog
import dev.slne.surf.surfapi.bukkit.api.dialog.type
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.random
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

fun createHologramCreateDialog() = dialog {
    base {
        title { primary("Hologram erstellen".toSmallCaps(), TextDecoration.BOLD) }
        body {
            plainMessage {
                info("Hier kannst du ein neues Hologramm erstellen. Fülle die unteren Felder aus um fortzufahren.")
            }

            input {
                text("holo_name") {
                    label { info("Hologramm Name") }
                    initial("HologramID${random.nextInt(1000, 9999)}")
                }
                singleOption("holo_type") {
                    HologramType.entries.sortedByDescending { it.name }.forEach {
                        option(
                            "holo_type_${it.name}",
                            buildText {
                                info(
                                    it.name.lowercase()
                                        .replaceFirstChar { first -> first.uppercase() })
                            })
                    }
                    label { info("Hologramm Typ") }
                }
                text("holo_text") {
                    label { info("Hologramm Inhalt") }
                    initial("<rainbow>Hello World!")
                    maxLength(Int.MAX_VALUE)
                }

                numberRange("holo_view_range", 1..64) {
                    label { info("Hologramm Sichtweite") }
                    step(1f)
                    initial(32f)
                }

                singleOption("holo_alignment") {
                    label { info("Hologramm Text Ausrichtung") }
                    HologramTextAlignment.entries.forEach {
                        option(
                            "holo_alignment_${it.name}",
                            buildText {
                                info(
                                    it.name.lowercase()
                                        .replaceFirstChar { first -> first.uppercase() })
                            })
                    }
                }

                text("holo_bg_color") {
                    label { info("Hologramm Hintergrund Farbe") }
                    width(100)
                    initial("#ffffff")
                }

                simpleBoolean("holo_clickable", true) {
                    info("Klickbarkeit")
                }
            }
        }
        type {
            confirmation {
                yes {
                    label { success("Erstellen") }
                    tooltip { success("Klicke um das Hologramm zu erstellen.") }
                    width(200)

                    action {
                        customClick { info, player ->
                            if (player !is Player) {
                                return@customClick
                            }

                            val nameInput = info.getText("holo_name")?.trim() ?: run {
                                player.sendText {
                                    error("Es wurde kein Name angegeben.")
                                }
                                return@customClick
                            }
                            val type = info.getText("holo_type")?.let {
                                val typeName = it.removePrefix("holo_type_")
                                HologramType.entries.firstOrNull { type -> type.name == typeName }
                            } ?: HologramType.ROTATING

                            val text = MiniMessage.miniMessage()
                                .deserialize(info.getText("holo_text") ?: "<red> Kein Inhalt")
                            val viewRange = info.getFloat("holo_view_range") ?: 32f
                            val alignment = info.getText("holo_alignment")?.let {
                                val alignName = it.removePrefix("holo_alignment_")
                                HologramTextAlignment.entries.firstOrNull { align -> align.name == alignName }
                            } ?: HologramTextAlignment.CENTER
                            val bgColor = info.getText("holo_bg_color")?.let {
                                TextColor.fromHexString(it)
                            } ?: TextColor.color(0)
                            val clickable = info.getBoolean("holo_clickable") ?: true

                            val holo = hologramService.createHologram(
                                type,
                                HologramMetaDataImpl(
                                    nameInput,
                                    random.nextInt(),
                                    random.nextInt(),
                                    1f,
                                    1f,
                                    viewRange,
                                    200,
                                    alignment,
                                    bgColor,
                                    clickable
                                ),
                                player.location.clone().add(0.0, 1.0, 0.0).toHologramLocation(),
                                text,
                                HologramCreationReason.Client(player.name)
                            )

                            holo.show()
                        }
                    }
                }

                no {
                    label { error("Abbrechen") }
                    tooltip { error("Klicke um den Vorgang abzubrechen.") }
                    width(200)

                    action {
                        customClick { _, player ->
                            player.closeDialog()
                        }
                    }
                }
            }
        }
    }
}