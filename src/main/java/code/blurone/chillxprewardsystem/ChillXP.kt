package code.blurone.chillxprewardsystem

import code.blurone.chillxprewardsystem.trackers.FarmingTracker
import code.blurone.chillxprewardsystem.trackers.GenericBreakingTracker
import code.blurone.chillxprewardsystem.trackers.Tracker
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

        val instantXp = config.getBoolean("instant-xp", false)
        val xpFunction = config.getString("xp-function")
        val xpArg = config.getDouble("xp-arg")

        val cuttingDelta = config.getInt("cutting-delta", 0)
        if (cuttingDelta > 0)
            server.pluginManager.registerEvents(GenericBreakingTracker(
                Tag.LOGS.values + Tag.LEAVES.values + setOf(Material.BEEHIVE),
                instantXp, xpFunction, xpArg,
                cuttingDelta, NamespacedKey(this, "cut-tracker")
            ), this)

        val diggingDelta = config.getInt("digging-delta", 0)
        if (diggingDelta > 0)
            server.pluginManager.registerEvents(GenericBreakingTracker(
                Tag.MINEABLE_SHOVEL.values - setOf(Material.SUSPICIOUS_SAND, Material.SUSPICIOUS_GRAVEL)
                + setOf(Material.POWDER_SNOW),
                instantXp, xpFunction, xpArg,
                diggingDelta, NamespacedKey(this, "dig-tracker")
            ), this)

        val farmingDelta = config.getInt("farming-delta", 0)
        if (farmingDelta > 0)
            server.pluginManager.registerEvents(FarmingTracker(
                instantXp, xpFunction, xpArg,
                farmingDelta, NamespacedKey(this, "farm-tracker")
            ), this)

        val miningDelta = config.getInt("mining-delta", 0)
        if (miningDelta > 0)
            server.pluginManager.registerEvents(GenericBreakingTracker(
                Tag.BASE_STONE_OVERWORLD.values + Tag.BASE_STONE_NETHER.values + Tag.CORAL_BLOCKS.values
                + Tag.TERRACOTTA.values,
                instantXp, xpFunction, xpArg,
                miningDelta, NamespacedKey(this, "mine-tracker")
            ), this)

        val travelingDelta = config.getInt("traveling-delta", 0)
        if (travelingDelta > 0)
            server.pluginManager.registerEvents(TravelingTracker(
                instantXp, xpFunction, xpArg,
                travelingDelta, NamespacedKey(this, "travel-tracker")
            ), this)
    }
}
