package code.blurone.chillxprewardsystem

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent

open class GenericBreakingTracker(
    private val blocks: MutableSet<Material>,
    delta: Int,
    trackingKey: NamespacedKey
) : Tracker(delta, trackingKey) {
    @EventHandler
    private fun onBlockBreak(event: BlockBreakEvent) {
        if (blocks.contains(event.block.type) && event.block.blockData.isPreferredTool(event.player.inventory.itemInMainHand))
            accumulateForPlayer(event.player)
    }
}