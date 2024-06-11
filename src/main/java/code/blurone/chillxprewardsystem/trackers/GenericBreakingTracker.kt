package code.blurone.chillxprewardsystem.trackers

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent

open class GenericBreakingTracker(
    private val blocks: Set<Material>,
    delta: Int,
    trackingKey: NamespacedKey
) : Tracker(delta, trackingKey) {
    @EventHandler(priority = EventPriority.MONITOR)
    private fun onBlockBreak(event: BlockBreakEvent) {
        if (blocks.contains(event.block.type) && event.block.blockData.isPreferredTool(event.player.inventory.itemInMainHand))
            accumulateForPlayer(event.player)
    }
}