package de.leonheuer.worldmanager.commands.worldmanager

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Message
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.scheduler.BukkitRunnable
import java.lang.IllegalArgumentException

class DenyInteractSubcommand(private val sender: CommandSender, private val args: Array<String>, private val main: WorldManager):
    BukkitRunnable() {

    override fun run() {
        if (args.size < 3) {
            sender.sendMessage(Message.DENY_INTERACT_HELP.getMessage())
            return
        }

        val world = Bukkit.getWorld(args[1])
        if (world == null) {
            sender.sendMessage(Message.UNKNOWN_WORLD.getMessage().replace("%world", args[1]))
            return
        }
        val profile = main.dataManager.getWorldProfile(world)
        if (profile == null) {
            sender.sendMessage(Message.UNKNOWN_WORLD.getMessage().replace("%world", args[1]))
            return
        }

        try {
            val spawnType = EntityType.valueOf(args[2].uppercase())
            if (profile.denyInteract(spawnType)) {
                sender.sendMessage(Message.DENY_INTERACT_SUCCESS.getMessage()
                    .replace("%entity", spawnType.toString().lowercase())
                    .replace("%world", world.name))
            } else {
                sender.sendMessage(Message.DENY_INTERACT_ALREADY.getMessage()
                    .replace("%entity", spawnType.toString().lowercase())
                    .replace("%world", world.name))
            }
        } catch (e: IllegalArgumentException) {
            sender.sendMessage(Message.UNKNOWN_ENTITY.getMessage().replace("%entity", args[2]))
        }
    }

}