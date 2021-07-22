package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause

class EntityDamageListener(private val main: WorldManager) : Listener {

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity is Player) {
            val profile = main.dm!!.getWorldProfile(entity.getWorld())!!
            if (profile.isFlagDenied(Flag.SLOW_VOID_DEATH)) {
                if (event.cause == DamageCause.FALL && entity.location.y < 0) {
                    event.isCancelled = true
                    entity.teleport(main.dm!!.spawn!!)
                    return
                }
            }
            if (profile.isFlagDenied(Flag.VULNERABLE)) {
                when (event.cause) {
                    DamageCause.FIRE, DamageCause.FIRE_TICK, DamageCause.LAVA -> {
                        entity.setFireTicks(0)
                        event.isCancelled = true
                    }
                    DamageCause.FALL, DamageCause.DROWNING, DamageCause.CONTACT, DamageCause.FALLING_BLOCK,
                    DamageCause.HOT_FLOOR, DamageCause.MAGIC, DamageCause.POISON, DamageCause.WITHER, DamageCause.THORNS,
                    DamageCause.SUFFOCATION, DamageCause.PROJECTILE, DamageCause.BLOCK_EXPLOSION ->
                        event.isCancelled = true
                    else -> return
                }
            }
        }
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        val attacker = event.damager
        if (attacker is Player) {
            if (event.entity is Player) {
                if (main.dm!!.getWorldProfile(attacker.getWorld())!!.isFlagDenied(Flag.PVP)) {
                    event.isCancelled = true
                }
            } else if (event.entity is LivingEntity) {
                if (!attacker.hasPermission("worldmanager.bypass.pve")) {
                    if (main.dm!!.getWorldProfile(attacker.getWorld())!!.isFlagDenied(Flag.PVE)) {
                        event.isCancelled = true
                    }
                }
            } else {
                if (!attacker.hasPermission("worldmanager.bypass.break")) {
                    if (main.dm!!.getWorldProfile(attacker.getWorld())!!.isFlagDenied(Flag.BREAK)) {
                        event.isCancelled = true
                    }
                }
            }
        }
    }

}