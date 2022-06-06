package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.vehicle.VehicleDamageEvent
import org.bukkit.event.vehicle.VehicleDestroyEvent
import org.bukkit.event.vehicle.VehicleEnterEvent
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent

class VehicleListener(private val main: WorldManager) : Listener {

    @EventHandler
    fun onVehicleDamage(event: VehicleDamageEvent) {
        val attacker = event.attacker
        if (attacker is Player) {
            if (!attacker.hasPermission("worldmanager.bypass.vehicle")) {
                if (main.dataManager.getWorldProfile(attacker.getWorld())!!.isFlagDenied(Flag.VEHICLE_DAMAGE)) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onVehicleDestroy(event: VehicleDestroyEvent) {
        val attacker = event.attacker
        if (attacker is Player) {
            if (!attacker.hasPermission("worldmanager.bypass.vehicle")) {
                if (main.dataManager.getWorldProfile(attacker.getWorld())!!.isFlagDenied(Flag.VEHICLE_DESTROY)) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onVehicleEnter(event: VehicleEnterEvent) {
        val entered = event.entered
        if (entered is Player) {
            if (!entered.hasPermission("worldmanager.bypass.vehicle")) {
                if (main.dataManager.getWorldProfile(entered.getWorld())!!.isFlagDenied(Flag.VEHICLE_ENTER)) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onVehicleCollision(event: VehicleEntityCollisionEvent) {
        if (event.vehicle !is LivingEntity) {
            if (main.dataManager.getWorldProfile(event.vehicle.world)!!.isFlagDenied(Flag.VEHICLE_COLLISION)) {
                event.isCancelled = true
            }
        }
    }

}