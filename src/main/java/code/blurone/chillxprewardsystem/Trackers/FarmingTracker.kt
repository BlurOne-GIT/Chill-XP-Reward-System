package code.blurone.chillxprewardsystem.Trackers

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerHarvestBlockEvent

class FarmingTracker(
    blocks: List<Material>,
    delta: Int,
    trackingKey: NamespacedKey
) : GenericBreakingTracker(blocks, delta, trackingKey) {
    @EventHandler
    private fun onHarvest(event: PlayerHarvestBlockEvent) = accumulateForPlayer(event.player)
}