package code.blurone.chillxprewardsystem.trackers

import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.ExperienceOrb
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerMoveEvent

class TravelingTracker(instantXp: Boolean, xpFunction: String?, xpArg: Double, delta: Int, trackingKey: NamespacedKey) :
    Tracker(instantXp, xpFunction, xpArg, delta, trackingKey) {
    @EventHandler(priority = EventPriority.LOWEST)
    private fun onPlayerTravel(event: PlayerMoveEvent) {
        val toVector = event.to?.toVector()?.setY(0) ?: return
        val fromVector = event.from.toVector().setY(0)

        if (!accumulateForPlayer(event.player, toVector.distance(fromVector))) return

        val toAward = xpFunction(event.player.level)

        if (instantXp)
            event.player.giveExp(toAward)

        (event.player.world.spawnEntity(event.player.location, EntityType.EXPERIENCE_ORB) as ExperienceOrb).apply {
            experience = toAward
        }
    }
}