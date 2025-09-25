package dev.slne.surf.hologram.api.hologram

/**
 * Represents the reason for the creation of a hologram. This can either be initiated by a client or through a plugin.
 */
sealed class HologramCreationReason {
    /**
     * Represents a client as a reason for hologram creation.
     *
     * This class is a specific implementation of [HologramCreationReason], used to define
     * that the hologram's creation is associated with a client. Each instance of the class
     * carries the name of the client as an identifying property.
     *
     * @property name The name of the client associated with the hologram creation reason.
     */
    data class Client(val name: String) : HologramCreationReason()

    /**
     * Represents a reason for creating a hologram that originates from a plugin.
     *
     * This data class holds the name of the plugin responsible for the creation
     * of the hologram. It inherits from the sealed class `HologramCreationReason`.
     *
     * @property pluginName The name of the plugin responsible for the hologram creation.
     */
    data class Plugin(val pluginName: String) : HologramCreationReason()

    /**
     * Determines if the current instance is of type `Client`.
     *
     * @return `true` if the instance is of type `Client`, otherwise `false`.
     */
    fun isClient() = this is Client

    /**
     * Determines if the current instance of `HologramCreationReason` is of the type `Plugin`.
     *
     * @return `true` if the instance is a `Plugin`, `false` otherwise.
     */
    fun isPlugin() = this is Plugin

    /**
     * Retrieves the identifying name of the hologram creation reason instance.
     *
     * If the instance is of type `Client`, it returns the name of the client.
     * If the instance is of type `Plugin`, it returns the name of the plugin.
     *
     * @return The name associated with the hologram creation reason, depending on the specific type.
     */
    fun name() = when (this) {
        is Client -> "$name (Client)"
        is Plugin -> "$pluginName (Plugin)"
    }
}