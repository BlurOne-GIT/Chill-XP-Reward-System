package code.blurone.chillxprewardsystem.trackers

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent

open class GenericBreakingTracker(
    private val blocks: Set<Material>,
    instantXp: Boolean,
    xpFunction: String?,
    xpArg: Double,
    delta: Int,
    trackingKey: NamespacedKey
) : Tracker(instantXp, xpFunction, xpArg, delta, trackingKey) {
    @EventHandler(priority = EventPriority.LOWEST)
    private fun onBlockBreak(event: BlockBreakEvent) {
        if (
            !blocks.contains(event.block.type) ||
            !event.block.blockData.isPreferredTool(event.player.inventory.itemInMainHand) ||
            !accumulateForPlayer(event.player)
        ) return

        val toAward = xpFunction(event.player.level)

        if (instantXp)
            return event.player.giveExp(toAward)

        event.expToDrop = toAward
    }
}