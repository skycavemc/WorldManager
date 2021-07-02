package de.leonheuer.worldmanager

import de.leonheuer.worldmanager.commands.WorldManagerCommand
import de.leonheuer.worldmanager.listener.*
import de.leonheuer.worldmanager.managers.DataManager
import de.leonheuer.worldmanager.util.Util
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

class WorldManager : JavaPlugin() {

    var dm: DataManager? = null
        private set

    override fun onEnable() {
        val pm = server.pluginManager
        dm = DataManager(this)
        Util.removeRecipe(Material.COARSE_DIRT)

        pm.registerEvents(BlockBreakListener(this), this)
        pm.registerEvents(BlockIgniteListener(this), this)
        pm.registerEvents(BlockPlaceListener(this), this)
        pm.registerEvents(EntityDamageListener(this), this)
        pm.registerEvents(PlayerBucketUseListener(this), this)
        pm.registerEvents(PlayerInteractListener(this), this)
        pm.registerEvents(PlayerRespawnListener(this), this)
        pm.registerEvents(BlockListener(this), this)
        pm.registerEvents(VehicleListener(this), this)
        pm.registerEvents(WorldListener(this), this)

        getCommand("worldmanager")!!.setExecutor(WorldManagerCommand(this))
    }

}