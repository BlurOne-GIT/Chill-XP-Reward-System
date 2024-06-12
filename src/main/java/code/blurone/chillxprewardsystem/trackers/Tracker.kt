package code.blurone.chillxprewardsystem.trackers

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.persistence.PersistentDataType

abstract class Tracker(
    protected val instantXp: Boolean,
    xpFunction: String?,
    private val xpArg: Double,
    private val delta: Int,
    private val trackingKey: NamespacedKey
) : Listener {
    protected val xpFunction = when (xpFunction?.uppercase()) {
        "CONSTANT" -> ::constant
        "PROPORTIONAL" -> ::proportional
        "LEVELED" -> ::leveled
        else -> ::skelun
    }

    private fun skelun(level: Int): Int = when {
        level <=  9 -> 12
        level <= 19 -> 15
        level <= 29 -> 21
        else        -> 33
    }

    private fun constant(level: Int): Int = xpArg.toInt()

    private fun proportional(level: Int): Int = (when {
        level <= 15 -> 2 * level + 7
        level <= 30 -> 5 * level - 38
        else        -> 9 * level - 158
    } * xpArg).toInt()

    private fun leveled(level: Int): Int = ((level + 1) * xpArg).toInt()

    // Accumulates and returns true if the delta was reached, allowing the trackers to reward the player
    protected fun accumulateForPlayer(player: Player, value: Double = 1.0): Boolean {
        val accumulator = (player.persistentDataContainer.get(trackingKey, PersistentDataType.DOUBLE) ?: .0) + value
        player.persistentDataContainer.set(trackingKey, PersistentDataType.DOUBLE, accumulator % delta)
        return accumulator >= delta
    }
}