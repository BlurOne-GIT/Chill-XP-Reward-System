package code.blurone.chillxprewardsystem

import org.bukkit.entity.Player

interface XpGranter {
    fun grantToPlayer(player: Player)
}