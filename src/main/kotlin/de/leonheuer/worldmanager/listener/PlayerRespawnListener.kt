package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Message
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerRespawnListener(private val main: WorldManager) : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        if (main.dm!!.spawn == null) {
            Bukkit.getConsoleSender().sendMessage(Message.WORLDSPAWN_NOT_YET.getMessage())
            return
        }
        event.respawnLocation = main.dm!!.spawn!!
    }

}