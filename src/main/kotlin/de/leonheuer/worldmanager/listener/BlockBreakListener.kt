package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.hanging.HangingBreakByEntityEvent

class BlockBreakListener(private val main: WorldManager) : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (!event.player.hasPermission("worldmanager.bypass.break")) {
            if (main.dataManager.getWorldProfile(event.player.world)!!.isFlagDenied(Flag.BREAK)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onHangingBreakByEntityEvent(event: HangingBreakByEntityEvent) {
        if (event.remover is Player) {
            if (!event.remover!!.hasPermission("worldmanager.bypass.break")) {
                if (main.dataManager.getWorldProfile(event.remover!!.world)!!.isFlagDenied(Flag.BREAK)) {
                    event.isCancelled = true
                }
            }
        }
    }

}