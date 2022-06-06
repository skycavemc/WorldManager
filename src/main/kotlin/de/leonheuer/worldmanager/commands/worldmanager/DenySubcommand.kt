package de.leonheuer.worldmanager.commands.worldmanager

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import de.leonheuer.worldmanager.enums.FlagPackage
import de.leonheuer.worldmanager.enums.FlagType
import de.leonheuer.worldmanager.enums.Message
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.scheduler.BukkitRunnable

class DenySubcommand(private val sender: CommandSender, private val args: Array<String>, private val main: WorldManager):
    BukkitRunnable() {

    override fun run() {
        if (args.size < 3) {
            sender.sendMessage(Message.DENY_HELP.getMessage())
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

        val flag: Flag? = Flag.fromString(args[2])
        if (flag != null) {
            if (profile.denyFlag(flag)) {
                sender.sendMessage(Message.DENY_SUCCESS.getMessage()
                    .replace("%world", world.name)
                    .replace("%flag", flag.toString().lowercase()))
            } else {
                sender.sendMessage(Message.DENY_ALREADY.getMessage()
                    .replace("%world", world.name)
                    .replace("%flag", flag.toString().lowercase()))
            }
            return
        }

        if (args[2].contains("@")) {
            val extracted = args[2].replace("@", "")
            val type: FlagType? = FlagType.fromString(extracted)
            if (type == null) {
                sender.sendMessage(Message.UNKNOWN_TYPE.getMessage().replace("%type", extracted))
                return
            }

            Flag.values().filter { inner -> inner.type == type}.forEach(profile::denyFlag)
            sender.sendMessage(
                Message.DENY_SUCCESS_TYPE.getMessage()
                    .replace("%type", type.toString().lowercase())
                    .replace("%world", world.name))
            return
        }

        if (args[2].contains("-")) {
            val extracted = args[2].replace("-", "")
            val pack: FlagPackage? = FlagPackage.fromString(extracted)
            if (pack == null) {
                sender.sendMessage(Message.UNKNOWN_PACKAGE.getMessage().replace("%pack", extracted))
                return
            }

            Flag.values().filter { inner -> inner.pack == pack}.forEach(profile::denyFlag)
            sender.sendMessage(Message.DENY_SUCCESS_PACKAGE.getMessage()
                .replace("%pack", pack.toString().lowercase())
                .replace("%world", world.name))
            return
        }

        if (args[2].equals("all", ignoreCase = true)) {
            Flag.values().forEach(profile::denyFlag)
            sender.sendMessage(Message.DENY_SUCCESS_ALL.getMessage().replace("%world", world.name))
            return
        }

        sender.sendMessage(Message.UNKNOWN_FLAG.getMessage().replace("%flag", args[2]))
    }

}