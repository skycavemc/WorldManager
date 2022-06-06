package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldLoadEvent
import org.bukkit.event.world.WorldUnloadEvent

class WorldLoadListener(private val main: WorldManager): Listener {

    @EventHandler
    fun onWorldLoad(event: WorldLoadEvent) {
        main.dataManager.loadWorld(event.world)
    }

    @EventHandler
    fun onWorldUnload(event: WorldUnloadEvent) {
        main.dataManager.unloadWorld(event.world)
    }

}