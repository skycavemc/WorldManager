package de.leonheuer.worldmanager.listener

import com.destroystokyo.paper.event.entity.CreeperIgniteEvent
import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.LeavesDecayEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.EntitySpawnEvent

class WorldListener(private val main: WorldManager) : Listener {

    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        val profile = main.dm!!.getWorldProfile(event.location.world) ?: return
        if (profile.isFlagDenied(Flag.ENTITY_SPAWN)) {
            if (event.entity is LivingEntity && event.entity !is Player) {
                event.isCancelled = true
            }
        }
        if (profile.whitelist && !profile.isSpawnDenied(event.entityType)) {
            event.isCancelled = true
            return
        }
        if (!profile.whitelist && profile.isSpawnDenied(event.entityType)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityPickupItem(event: EntityPickupItemEvent) {
        if (event.entity !is Player) {
            if (main.dm!!.getWorldProfile(event.entity.world)!!.isFlagDenied(Flag.ENTITY_PICKUP)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onLeavesDecay(event: LeavesDecayEvent) {
        if (main.dm!!.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.LEAVES_DECAY)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onCreeperIgnite(event: CreeperIgniteEvent) {
        if (main.dm!!.getWorldProfile(event.entity.world)!!.isFlagDenied(Flag.CREEPER_POWER)) {
            event.isCancelled = true
        }
    }

}