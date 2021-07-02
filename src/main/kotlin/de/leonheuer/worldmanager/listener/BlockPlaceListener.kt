package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import de.leonheuer.worldmanager.enums.Message
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockPlaceListener(private val main: WorldManager) : Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.block.type == Material.BARRIER || event.block.type == Material.BEDROCK) {
            if (!event.player.hasPermission("worldmanager.bypass.forbidden")) {
                event.isCancelled = true
                event.player.sendMessage(Message.FORBIDDEN_BLOCK.getMessage())
            }
        } else {
            if (!event.player.hasPermission("worldmanager.bypass.place")) {
                if (main.dm!!.getWorldProfile(event.player.world)!!.isFlagDenied(Flag.PLACE)) {
                    event.isCancelled = true
                }
            }
        }
    }

}