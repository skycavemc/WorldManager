package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockIgniteEvent

class BlockIgniteListener(private val main: WorldManager) : Listener {

    @EventHandler
    fun onBlockIgnite(event: BlockIgniteEvent) {
        val player = event.player
        if (player == null) {
            if (event.cause == BlockIgniteEvent.IgniteCause.LIGHTNING) {
                if (main.dataManager.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.BLOCK_LIGHTNING)) {
                    event.isCancelled = true
                }
            } else {
                if (main.dataManager.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.BLOCK_BURN)) {
                    event.isCancelled = true
                }
            }
        } else {
            if (!player.hasPermission("worldmanager.bypass.ignite")) {
                if (main.dataManager.getWorldProfile(event.player!!.world)!!.isFlagDenied(Flag.IGNITE)) {
                    event.isCancelled = true
                }
            }
        }
    }

}