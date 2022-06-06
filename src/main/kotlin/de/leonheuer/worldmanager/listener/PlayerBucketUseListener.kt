package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerBucketFillEvent

class PlayerBucketUseListener(private val main: WorldManager) : Listener {

    @EventHandler
    fun onPlayerBucket(event: PlayerBucketEmptyEvent) {
        if (!event.player.hasPermission("worldmanager.bypass.bucket")) {
            if (main.dataManager.getWorldProfile(event.player.world)!!.isFlagDenied(Flag.BUCKET_USE)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onPlayerBucket(event: PlayerBucketFillEvent) {
        if (!event.player.hasPermission("worldmanager.bypass.bucket")) {
            if (main.dataManager.getWorldProfile(event.player.world)!!.isFlagDenied(Flag.BUCKET_USE)) {
                event.isCancelled = true
            }
        }
    }

}