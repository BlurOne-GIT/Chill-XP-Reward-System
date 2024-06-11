package code.blurone.chillxprewardsystem.trackers

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerHarvestBlockEvent

class FarmingTracker(delta: Int, trackingKey: NamespacedKey) : GenericBreakingTracker(
    delta, trackingKey
    Tag.CROPS.values + Tag.MINEABLE_HOE.values - Tag.LEAVES.values + Tag.CORALS.values - Tag.CORAL_PLANTS.values
    - setOf(Material.SCULK, Material.SCULK_CATALYST, Material.SCULK_SENSOR, Material.SCULK_SHRIEKER),
) {
    @EventHandler(priority = EventPriority.MONITOR)
    private fun onHarvest(event: PlayerHarvestBlockEvent) = accumulateForPlayer(event.player)
}