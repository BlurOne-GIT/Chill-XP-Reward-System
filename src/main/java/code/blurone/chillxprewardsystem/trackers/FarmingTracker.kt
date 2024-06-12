package code.blurone.chillxprewardsystem.trackers

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag
import org.bukkit.entity.EntityType
import org.bukkit.entity.ExperienceOrb
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerHarvestBlockEvent

class FarmingTracker(
    instantXp: Boolean,
    xpFunction: String?,
    xpArg: Double,
    delta: Int,
    trackingKey: NamespacedKey
) : GenericBreakingTracker(
    Tag.CROPS.values + Tag.MINEABLE_HOE.values - Tag.LEAVES.values + Tag.CORALS.values - Tag.CORAL_PLANTS.values
            - setOf(Material.SCULK, Material.SCULK_CATALYST, Material.SCULK_SENSOR, Material.SCULK_SHRIEKER),
    instantXp, xpFunction, xpArg, delta, trackingKey
) {
    @EventHandler(priority = EventPriority.MONITOR)
    private fun onHarvest(event: PlayerHarvestBlockEvent) {
        if (!accumulateForPlayer(event.player)) return

        val toAward = xpFunction(event.player.level)

        if (instantXp)
            return event.player.giveExp(toAward)

        (event.harvestedBlock.world.spawnEntity(
            event.harvestedBlock.location.add(.5, .5, .5),
            EntityType.EXPERIENCE_ORB
        ) as ExperienceOrb).apply {
            experience = toAward
        }
    }
}