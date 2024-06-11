package code.blurone.chillxprewardsystem

import code.blurone.chillxprewardsystem.trackers.FarmingTracker
import code.blurone.chillxprewardsystem.trackers.GenericBreakingTracker
import code.blurone.chillxprewardsystem.trackers.TravelingTracker
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class ChillXP : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        saveDefaultConfig()
        val cuttingDelta = config.getInt("cutting-delta", 0)
        val diggingDelta = config.getInt("digging-delta", 0)
        val farmingDelta = config.getInt("farming-delta", 0)
        val miningDelta = config.getInt("mining-delta", 0)
        val travelingDelta = config.getInt("traveling-delta", 0)

        if (cuttingDelta > 0)
            server.pluginManager.registerEvents(GenericBreakingTracker(
                Tag.LOGS.values + Tag.LEAVES.values + setOf(Material.BEEHIVE),
                cuttingDelta,
                NamespacedKey(this, "cut-tracker")
            ), this)

        if (diggingDelta > 0)
            server.pluginManager.registerEvents(GenericBreakingTracker(
                Tag.MINEABLE_SHOVEL.values - setOf(Material.SUSPICIOUS_SAND, Material.SUSPICIOUS_GRAVEL)
                + setOf(Material.POWDER_SNOW),
                diggingDelta,
                NamespacedKey(this, "dig-tracker")
            ), this)

        if (farmingDelta > 0)
            server.pluginManager.registerEvents(
                FarmingTracker(farmingDelta, NamespacedKey(this, "farm-tracker")),
                this
            )

        if (miningDelta > 0)
            server.pluginManager.registerEvents(GenericBreakingTracker(
                Tag.BASE_STONE_OVERWORLD.values + Tag.BASE_STONE_NETHER.values + Tag.CORAL_BLOCKS.values
                + Tag.TERRACOTTA.values,
                miningDelta,
                NamespacedKey(this, "mine-tracker")
            ), this)

        if (travelingDelta > 0)
            server.pluginManager.registerEvents(
                TravelingTracker(travelingDelta, NamespacedKey(this, "travel-tracker"))
                , this
            )
    }
}
