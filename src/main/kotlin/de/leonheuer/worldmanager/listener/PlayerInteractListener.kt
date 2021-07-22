package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import de.leonheuer.worldmanager.util.Util
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerArmorStandManipulateEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractListener(private val main: WorldManager) : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val block = event.clickedBlock ?: return
        if (main.dm!!.getWorldProfile(event.player.world)!!.isFlagDenied(Flag.INTERACT)) {
            if (event.action == Action.PHYSICAL && block.type.isInteractable) {
                event.isCancelled = true
                return
            }
            if (player.hasPermission("worldmanager.bypass.interact")) {
                return
            }
            if (event.action == Action.LEFT_CLICK_BLOCK) {
                event.isCancelled = true
                return
            }
            if (event.action == Action.RIGHT_CLICK_BLOCK) {
                if (Util.getListOfSummoners().contains(player.inventory.itemInMainHand.type) ||
                    Util.getListOfSummoners().contains(player.inventory.itemInOffHand.type)
                ) {
                    event.isCancelled = true
                } else if (block.type.isInteractable && block.type != Material.ENDER_CHEST && !block.type.toString().contains("STAIRS")) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        if (!event.player.hasPermission("worldmanager.bypass.interact")) {
            val profile = main.dm!!.getWorldProfile(event.player.world) ?: return
            if (profile.isFlagDenied(Flag.INTERACT)) {
                if (event.rightClicked !is LivingEntity) {
                    event.isCancelled = true
                    return
                }
            }
            if (profile.whitelist && !profile.isInteractDenied(event.rightClicked.type)) {
                event.isCancelled = true
                return
            }
            if (!profile.whitelist && profile.isInteractDenied(event.rightClicked.type)) {
                event.isCancelled = true
                return
            }
        }
    }

    @EventHandler
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        if (!event.player.hasPermission("worldmanager.bypass.interact")) {
            if (main.dm!!.getWorldProfile(event.player.world)!!.isFlagDenied(Flag.INTERACT)) {
                event.isCancelled = true
            }
        }
    }


}