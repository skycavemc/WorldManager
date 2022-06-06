package de.leonheuer.worldmanager

import de.leonheuer.worldmanager.commands.WorldManagerCommand
import de.leonheuer.worldmanager.listener.*
import de.leonheuer.worldmanager.managers.DataManager
import de.leonheuer.worldmanager.util.Util
import org.bukkit.Material
import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class WorldManager : JavaPlugin() {

    lateinit var dataManager: DataManager
        private set

    override fun onEnable() {
        // managers
        dataManager = DataManager(this)

        // events
        registerEvents(
            BlockBreakListener(this),
            BlockIgniteListener(this),
            BlockPlaceListener(this),
            EntityDamageListener(this),
            PlayerBucketUseListener(this),
            PlayerInteractListener(this),
            PlayerRespawnListener(this),
            BlockListener(this),
            VehicleListener(this),
            WorldListener(this),
            WorldLoadListener(this)
        )

        registerCommand("worldmanager", WorldManagerCommand(this))

        Util.removeRecipe(Material.COARSE_DIRT)
    }

    @Suppress("SameParameterValue")
    private fun registerCommand(cmd: String, executor: CommandExecutor) {
        val command = getCommand(cmd)
        if (command == null) {
            logger.severe("No entry for command $cmd found in the plugin.yml.")
            return
        }
        command.setExecutor(executor)
    }

    private fun registerEvents(vararg events: Listener) {
        for (event in events) {
            server.pluginManager.registerEvents(event, this)
        }
    }

}