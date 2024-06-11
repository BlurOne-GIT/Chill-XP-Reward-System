package code.blurone.chillxprewardsystem.trackers

import code.blurone.chillxprewardsystem.XpGranter
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.persistence.PersistentDataType

abstract class Tracker(
    private val delta: Int,
    private val trackingKey: NamespacedKey
) : Listener {
    companion object {
        internal lateinit var xpGranter: XpGranter
    }

    protected fun accumulateForPlayer(player: Player, value: Double = 1.0) {
        var accumulator = (player.persistentDataContainer.get(trackingKey, PersistentDataType.DOUBLE) ?: .0) + value
        if (accumulator >= delta) {
            xpGranter.grantToPlayer(player)
            accumulator %= delta
        }
        player.persistentDataContainer.set(trackingKey, PersistentDataType.DOUBLE, accumulator)
    }
}