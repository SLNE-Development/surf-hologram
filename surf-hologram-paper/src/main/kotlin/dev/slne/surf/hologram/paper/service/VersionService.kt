package dev.slne.surf.hologram.paper.service

import dev.slne.surf.hologram.paper.plugin
import dev.slne.surf.surfapi.core.api.util.logger

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI

class VersionService {
    var currentVersion = SurfPluginVersion.local()
    var latestVersion: SurfPluginVersion? = null
    var link: String? = null

    private val fetchUrl =
        "https://api.github.com/repos/SLNE-DEVELOPMENT/surf-hologram/releases/latest"

    fun isUpToDate() = !(latestVersion?.isNewerThan(currentVersion) ?: false)

    suspend fun fetchGithubVersion() = withContext(Dispatchers.IO) {
        runCatching {
            val url = URI(fetchUrl).toURL()
            val connection = url.openConnection() as HttpURLConnection

            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json")

            if (connection.responseCode == 200) {
                val body =
                    BufferedReader(InputStreamReader(connection.inputStream)).use { it.readText() }
                val json = Json.parseToJsonElement(body).jsonObject
                val latestTag = json["tag_name"]?.jsonPrimitive?.content
                val link = json["html_url"]?.jsonPrimitive?.content

                link?.let {
                    this@VersionService.link = it
                }

                latestTag?.let {
                    this@VersionService.latestVersion =
                        SurfPluginVersion.fromString(latestTag.removePrefix("v"))
                }
            } else {
                logger().atWarning().log("Version Request failed with ${connection.responseCode}")
            }

            connection.disconnect()
        }.onFailure {
            logger().atWarning().log("Version Request failed: ${it.message}")
        }
    }

    companion object {
        val INSTANCE = VersionService()
    }
}

data class SurfPluginVersion(
    val mcVersion: String,
    val pluginVersion: String,
    val isSnapshot: Boolean = false
) {
    override fun toString(): String {
        return "$mcVersion-$pluginVersion${if (isSnapshot) "-SNAPSHOT" else ""}"
    }

    fun isNewerThan(other: SurfPluginVersion?): Boolean {
        if (other == null) return true

        val mcPartsThis = this.mcVersion.split(".").mapNotNull { it.toIntOrNull() }
        val mcPartsOther = other.mcVersion.split(".").mapNotNull { it.toIntOrNull() }
        val mcLength = maxOf(mcPartsThis.size, mcPartsOther.size)

        for (i in 0 until mcLength) {
            val thisPart = mcPartsThis.getOrNull(i) ?: 0
            val otherPart = mcPartsOther.getOrNull(i) ?: 0
            if (thisPart > otherPart) return true
            if (thisPart < otherPart) return false
        }

        val pluginPartsThis = this.pluginVersion.split(".").mapNotNull { it.toIntOrNull() }
        val pluginPartsOther = other.pluginVersion.split(".").mapNotNull { it.toIntOrNull() }
        val pluginLength = maxOf(pluginPartsThis.size, pluginPartsOther.size)

        for (i in 0 until pluginLength) {
            val thisPart = pluginPartsThis.getOrNull(i) ?: 0
            val otherPart = pluginPartsOther.getOrNull(i) ?: 0
            if (thisPart > otherPart) return true
            if (thisPart < otherPart) return false
        }

        return this.isSnapshot && !other.isSnapshot
    }


    companion object {
        fun local(): SurfPluginVersion {
            val desc = plugin.pluginMeta
            val version = desc.version

            val parts = version.split("-")
            val mcVersion = parts.getOrNull(0) ?: "unknown"
            val pluginVersion = parts.getOrNull(1) ?: "unknown"
            val isSnapshot = parts.size > 2 && parts[2].equals("SNAPSHOT", ignoreCase = true)

            return SurfPluginVersion(
                mcVersion = mcVersion,
                pluginVersion = pluginVersion,
                isSnapshot = isSnapshot
            )
        }

        fun fromString(version: String): SurfPluginVersion {
            val parts = version.split("-")
            val mcVersion = parts.getOrNull(0) ?: "unknown"
            val pluginVersion = parts.getOrNull(1) ?: "unknown"
            val isSnapshot = parts.size > 2 && parts[2].equals("SNAPSHOT", ignoreCase = true)

            return SurfPluginVersion(mcVersion, pluginVersion, isSnapshot)
        }
    }
}

val versionService get() = VersionService.INSTANCE