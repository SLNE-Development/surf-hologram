package dev.slne.surf.hologram.api.hologram

import dev.slne.surf.hologram.api.hologram.location.HologramLocation

/**
 * Represents a hologram that is shown to a player only while they are looking at a specific
 * target – either a (potentially packet-based / fake) entity identified by its raw entity ID,
 * or a block position identified by a [HologramLocation].
 *
 * Since the targets may be completely virtual (sent only via packets), neither Bukkit entities
 * nor Bukkit blocks are used. Pass the **raw entity ID** for entity targets and a
 * [HologramLocation] for block targets.
 *
 * Exactly one of [targetEntityId] or [targetLocation] should be non-null.
 */
interface TooltipHologram : Hologram {
    /**
     * The raw entity ID of the entity that triggers the tooltip when looked at.
     *
     * Use this for (packet-based/fake) entities. Mutually exclusive with [targetLocation].
     * When this is set, the look-at check is performed against [Hologram.centerLocation].
     */
    val targetEntityId: Int?

    /**
     * The block position that triggers the tooltip when looked at.
     *
     * Use this for block targets (real or virtual). Mutually exclusive with [targetEntityId].
     * The tooltip itself ([Hologram.centerLocation]) can be placed anywhere independently.
     */
    val targetLocation: HologramLocation?

    /**
     * The half-angle (in degrees) of the cone around the player's look direction within which
     * a target counts as "being looked at". Smaller values require more precise aiming.
     *
     * Default: `5.0`
     */
    val lookAngleThreshold: Double

    /**
     * Maximum distance (in blocks) from the target at which the tooltip can be triggered.
     *
     * Default: `5.0`
     */
    val maxDistance: Double
}
