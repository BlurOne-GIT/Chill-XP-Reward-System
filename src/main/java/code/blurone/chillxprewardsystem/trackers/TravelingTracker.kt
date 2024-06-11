package code.blurone.chillxprewardsystem.trackers

import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerMoveEvent

class TravelingTracker(delta: Int, trackingKey: NamespacedKey) : Tracker(delta, trackingKey) {
    @EventHandler(priority = EventPriority.LOWEST)
    private fun onPlayerTravel(event: PlayerMoveEvent) {
        val toVector = event.to?.toVector()?.setY(0) ?: return
        val fromVector = event.from.toVector().setY(0)

        accumulateForPlayer(event.player, toVector.distance(fromVector))
    }
}