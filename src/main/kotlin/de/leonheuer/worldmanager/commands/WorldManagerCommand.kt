package de.leonheuer.worldmanager.commands

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.commands.worldmanager.*
import de.leonheuer.worldmanager.enums.Flag
import de.leonheuer.worldmanager.enums.FlagPackage
import de.leonheuer.worldmanager.enums.FlagType
import de.leonheuer.worldmanager.enums.Message
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.lang.NumberFormatException
import java.util.*

class WorldManagerCommand(private val main: WorldManager) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            help(sender, 1)
            return true
        }

        when (args[0].lowercase()) {
            "allow" -> AllowSubcommand(sender, args, main).runTask(main)
            "deny" -> DenySubcommand(sender, args, main).runTask(main)
            "denyspawn" -> DenySpawnSubcommand(sender, args, main).runTask(main)
            "allowspawn" -> AllowSpawnSubcommand(sender, args, main).runTask(main)
            "denyinteract" -> DenyInteractSubcommand(sender, args, main).runTask(main)
            "allowinteract" -> AllowInteractSubcommand(sender, args, main).runTask(main)
            "info" -> {
                if (args.size < 2) {
                    sender.sendMessage(Message.INFO_HELP.getMessage())
                    return true
                }

                val world = Bukkit.getWorld(args[1])
                if (world == null) {
                    sender.sendMessage(Message.UNKNOWN_WORLD.getMessage().replace("%world", args[1]))
                    return true
                }
                val profile = main.dataManager.getWorldProfile(world)
                if (profile == null) {
                    sender.sendMessage(Message.UNKNOWN_WORLD.getMessage().replace("%world", args[1]))
                    return true
                }

                val flags = StringJoiner(", ")
                for (flag in profile.flags) {
                    flags.add(flag.name.lowercase())
                }
                val spawn = StringJoiner(", ")
                for (entity in profile.spawnList) {
                    spawn.add(entity.name.lowercase())
                }
                val interact = StringJoiner(", ")
                for (entity in profile.interactList) {
                    interact.add(entity.name.lowercase())
                }

                sender.sendMessage(Message.INFO_HEADER.getFormatted()
                    .replace("%world", world.name))
                sender.sendMessage(Message.INFO_UUID.getFormatted()
                    .replace("%uuid", world.uid.toString()))
                sender.sendMessage(Message.INFO_FLAGS.getFormatted()
                    .replace("%flags", flags.toString()))
                sender.sendMessage(Message.INFO_SPAWN.getFormatted()
                    .replace("%spawn", spawn.toString()))
                sender.sendMessage(Message.INFO_INTERACT.getFormatted()
                    .replace("%interact", interact.toString()))
                sender.sendMessage(Message.INFO_WHITELIST.getFormatted()
                    .replace("%whitelist", if (profile.whitelist) "§aAktiviert" else "§cDeaktiviert"))
            }
            "whitelist" -> {
                if (args.size < 2) {
                    sender.sendMessage(Message.WHITELIST_HELP.getMessage())
                    return true
                }

                val world = Bukkit.getWorld(args[1])
                if (world == null) {
                    sender.sendMessage(Message.UNKNOWN_WORLD.getMessage().replace("%world", args[1]))
                    return true
                }
                val profile = main.dataManager.getWorldProfile(world)
                if (profile == null) {
                    sender.sendMessage(Message.UNKNOWN_WORLD.getMessage().replace("%world", args[1]))
                    return true
                }

                profile.whitelist = !profile.whitelist
                if (profile.whitelist) {
                    sender.sendMessage(Message.WHITELIST_ON.getMessage().replace("%world", world.name))
                } else {
                    sender.sendMessage(Message.WHITELIST_OFF.getMessage().replace("%world", world.name))
                }
            }
            "worlds" -> {
                val sj = StringJoiner("§8, §7")
                Bukkit.getWorlds().forEach {
                    sj.add(it.name)
                }
                sender.sendMessage(Message.LIST_WORLDS.getFormatted().replace("%worlds", sj.toString()))
            }
            "entities" -> {
                val sj = StringJoiner("§8, §7")
                EntityType.values().forEach {
                    sj.add(it.toString().lowercase())
                }
                sender.sendMessage(Message.LIST_ENTITIES.getFormatted().replace("%entities", sj.toString()))
            }
            "flags" -> {
                val sj = StringJoiner("§8, §7")
                Flag.values().forEach {
                    sj.add(it.toString().lowercase())
                }
                sender.sendMessage(Message.LIST_FLAGS.getFormatted().replace("%flags", sj.toString()))
            }
            "types" -> FlagType.values().forEach { type ->
                val sj = StringJoiner("§8, §7")
                Flag.values().filter { flag -> flag.type == type }.forEach{ flag -> sj.add(flag.toString().lowercase()) }
                sender.sendMessage(Message.LIST_TYPES.getFormatted()
                    .replace("%type", type.toString())
                    .replace("%flags", sj.toString())
                )
            }
            "packs" -> FlagPackage.values().forEach { pack ->
                val sj = StringJoiner("§8, §7")
                Flag.values().filter { flag -> flag.pack == pack }.forEach{ flag -> sj.add(flag.toString().lowercase()) }
                sender.sendMessage(Message.LIST_PACKS.getFormatted()
                    .replace("%pack", pack.toString())
                    .replace("%flags", sj.toString())
                )
            }
            "help" -> {
                if (args.size >= 2) {
                    try {
                        val page = Integer.parseInt(args[1])
                        help(sender, page)
                    } catch (e: NumberFormatException) {
                        sender.sendMessage(Message.INVALID_NUMBER.getMessage())
                    }
                    return true
                }

                help(sender, 1)
            }
            "setworldspawn" -> {
                if (sender !is Player) {
                    main.logger.severe("This command is for players only!")
                    return true
                }
                main.dataManager.spawn = sender.location
                main.dataManager.writeSpawn()
                sender.sendMessage(Message.WORLDSPAWN_SUCCESS.getMessage())
            }
            else -> sender.sendMessage(Message.UNKNOWN_COMMAND.getMessage().replace("%cmd", label))
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, cmd: Command, label: String, args: Array<String>): List<String> {
        val arguments: ArrayList<String> = ArrayList()
        val completions: ArrayList<String> = ArrayList()

        if (args.size == 1) {
            arguments.addAll(arrayOf("allow", "deny", "allowSpawn", "denySpawn", "allowInteract", "denyInteract",
                "info", "whitelist", "setworldspawn", "worlds", "entities", "flags", "types", "packs", "help"))
            StringUtil.copyPartialMatches(args[0], arguments, completions)
        }

        if (args.size == 2) {
            when (args[0].lowercase()) {
                "allow", "deny", "info", "allowSpawn", "denySpawn", "allowInteract", "denyInteract", "whitelist" -> {
                    Bukkit.getWorlds().forEach { world -> arguments.add(world.name) }
                    StringUtil.copyPartialMatches(args[1], arguments, completions)
                }
            }
        }

        if (args.size == 3) {
            when (args[0].lowercase()) {
                "allow", "deny" -> {
                    Flag.values().forEach { flag -> arguments.add(flag.toString().lowercase()) }
                    FlagType.values().forEach { type -> arguments.add("@${type.toString().lowercase()}") }
                    FlagPackage.values().forEach { pack -> arguments.add("-${pack.toString().lowercase()}") }
                    arguments.add("all")
                    StringUtil.copyPartialMatches(args[2], arguments, completions)
                }
                "allowSpawn", "denySpawn", "allowInteract", "denyInteract" -> {
                    EntityType.values().forEach { entity -> arguments.add(entity.toString().lowercase()) }
                    StringUtil.copyPartialMatches(args[2], arguments, completions)
                }
            }
        }

        completions.sort()
        return completions
    }

    private fun help(sender: CommandSender, page: Int) {
        when (page) {
            1 -> {
                sender.sendMessage(Message.HELP_HEADER.getFormatted()
                    .replace("%page", "$page")
                    .replace("%max", "2"))
                sender.sendMessage(Message.HELP_DENY.getFormatted())
                sender.sendMessage(Message.HELP_ALLOW.getFormatted())
                sender.sendMessage(Message.HELP_DENY_SPAWN.getFormatted())
                sender.sendMessage(Message.HELP_ALLOW_SPAWN.getFormatted())
                sender.sendMessage(Message.HELP_DENY_INTERACT.getFormatted())
                sender.sendMessage(Message.HELP_ALLOW_INTERACT.getFormatted())
                sender.sendMessage(Message.HELP_INFO.getFormatted())
                sender.sendMessage(Message.HELP_WHITELIST.getFormatted())
                sender.sendMessage(Message.HELP_FOOTER.getFormatted()
                    .replace("%page", "$page")
                    .replace("%max", "2"))
            }
            2 -> {
                sender.sendMessage(Message.HELP_HEADER.getFormatted()
                    .replace("%page", "$page")
                    .replace("%max", "2"))
                sender.sendMessage(Message.HELP_SETWORLDSPAWN.getFormatted())
                sender.sendMessage(Message.HELP_FLAGS.getFormatted())
                sender.sendMessage(Message.HELP_WORLDS.getFormatted())
                sender.sendMessage(Message.HELP_ENTITIES.getFormatted())
                sender.sendMessage(Message.HELP_TYPES.getFormatted())
                sender.sendMessage(Message.HELP_PACKS.getFormatted())
                sender.sendMessage(Message.HELP_HELP.getFormatted())
                sender.sendMessage(Message.HELP_FOOTER.getFormatted()
                    .replace("%page", "$page")
                    .replace("%max", "2"))
            }
            else -> sender.sendMessage(Message.HELP_INVALID_PAGE.getMessage().replace("%max", "2"))
        }
    }

}