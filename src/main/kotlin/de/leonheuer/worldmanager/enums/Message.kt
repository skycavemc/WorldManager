package de.leonheuer.worldmanager.enums

import org.bukkit.ChatColor

enum class Message(private val message: String) {

    PREFIX("&8&l| &5World&dManager &8» "),
    UNKNOWN_COMMAND("&cÜngültiger Befehl. Siehe /%cmd help"),
    UNKNOWN_FLAG("&cDie Flag %flag existiert nicht. Siehe /wm flags"),
    UNKNOWN_WORLD("&cDie Welt %world existiert nicht. Siehe /wm worlds"),
    UNKNOWN_TYPE("&cDer Flag-Typ %type existiert nicht. Siehe /wm types"),
    UNKNOWN_PACKAGE("&cDas Flag-Paket %pack existiert nicht. Siehe /wm packs"),
    UNKNOWN_ENTITY("&cDas Entity %entity existiert nicht. Siehe /wm entities"),
    INVALID_NUMBER("&cDu hast eine ungültige Nummer angegeben."),
    FORBIDDEN_BLOCK("&4Du darfst diesen Block nicht platzieren."),

    // allow subcommand
    ALLOW_HELP("&e/wm allow <Welt> <Flag>"),
    ALLOW_ALREADY("&cDie Flag &4%flag &cist bereits für die Welt &4%world &cerlaubt!"),
    ALLOW_SUCCESS("&aFlag &2%flag &afür die Welt &2%world &aerlaubt!"),
    ALLOW_SUCCESS_TYPE("&aFlag-Typ &2%type &afür die Welt &2%world &aerlaubt!"),
    ALLOW_SUCCESS_PACKAGE("&aFlag-Paket &2%pack &afür die Welt &2%world &aerlaubt!"),
    ALLOW_SUCCESS_ALL("&aAlle Flags für die Welt &2%world &aerlaubt!"),

    // deny subcommand
    DENY_HELP("&e/wm deny <Welt> <Flag>"),
    DENY_ALREADY("&cDie Flag &4%flag &cist bereits für die Welt &4%world &cverboten!"),
    DENY_SUCCESS("&cFlag &4%flag &cfür die Welt &4%world &cverboten!"),
    DENY_SUCCESS_TYPE("&cFlag-Typ &4%type &cfür die Welt &4%world &cverboten!"),
    DENY_SUCCESS_PACKAGE("&cFlag-Paket &4%pack &cfür die Welt &4%world &cverboten!"),
    DENY_SUCCESS_ALL("&cAlle Flags &cfür die Welt &4%world &cverboten!"),

    // deny spawn subcommand
    DENY_SPAWN_HELP("&e/wm denySpawn <Welt> <Entity>"),
    DENY_SPAWN_ALREADY("&cDas Entity &4%entity &cist bereits für die Welt &4%world &cverboten!"),
    DENY_SPAWN_SUCCESS("&cEntity &4%entity &cfür die Welt &4%world &cverboten!"),

    // allow spawn subcommand
    ALLOW_SPAWN_HELP("&e/wm allowSpawn <Welt> <Entity>"),
    ALLOW_SPAWN_ALREADY("&cDas Entity &4%entity &cist bereits für die Welt &4%world &cverboten!"),
    ALLOW_SPAWN_SUCCESS("&aEntity &2%entity &afür die Welt &2%world &aerlaubt!"),

    // deny interact subcommand
    DENY_INTERACT_HELP("&e/wm denyInteract <Welt> <Entity>"),
    DENY_INTERACT_ALREADY("&cInteraktionen mit &4%entity &csind bereits für die Welt &4%world &cverboten!"),
    DENY_INTERACT_SUCCESS("&cInteraktionen mit &4%entity &cfür die Welt &4%world &cverboten!"),

    // allow interact subcommand
    ALLOW_INTERACT_HELP("&e/wm allowInteract <Welt> <Entity>"),
    ALLOW_INTERACT_ALREADY("&cInteraktionen mit &4%entity &csind bereits für die Welt &4%world &cverboten!"),
    ALLOW_INTERACT_SUCCESS("&aInteraktionen mit &2%entity &afür die Welt &2%world &aerlaubt!"),

    // info subcommand
    INFO_HELP("&e/wm info <Welt>"),
    INFO_HEADER("&8━━━━━━━━━━━━━━━┫ &eInfos zur Welt %world&8 ┣━━━━━━━━━━━━━━━"),
    INFO_UUID("&bUUID: &7%uuid"),
    INFO_FLAGS("&bFlags: &7%flags"),
    INFO_SPAWN("&bSpawnen: &7%spawn"),
    INFO_INTERACT("&bInteragieren: &7%interact"),
    INFO_WHITELIST("&bWhitelist: %whitelist"),
    
    // whitelist subcommand
    WHITELIST_HELP("&e/wm whitelist <Welt>"),
    WHITELIST_ON("&aWhitelist-Modus für die Welt &2%world &aaktiviert."),
    WHITELIST_OFF("&cWhitelist-Modus für die Welt &4%world &cdeaktiviert."),

    // list subcommands
    LIST_FLAGS("&bVerfügbare Flags&c: &a%flags"),
    LIST_WORLDS("&bVerfügbare Welten&c: &a%worlds"),
    LIST_ENTITIES("&bVerfügbare Entities&c: &a%entities"),
    LIST_TYPES("&bFlag-Typ &c%type: &7%flags"),
    LIST_PACKS("&bFlag-Paket &c%pack: &7%flags"),

    // worldspawn subcommand
    WORLDSPAWN_SUCCESS("&aGlobaler Respawn-Punkt erfolgreich festgelegt."),
    WORLDSPAWN_NOT_YET("&cEs wurde noch kein Respawn-Punkt festgelegt."),

    // help
    HELP_ALLOW("&b/wm allow <Flag> <Welt>\n&8» &7Erlaubt eine Flag"),
    HELP_DENY("&b/wm deny <Flag> <Welt>\n&8» &7Verbietet eine Flag"),
    HELP_ALLOW_SPAWN("&b/wm allowSpawn <Flag> <Welt>\n&8» &7Erlaubt das Spawen eines Entities"),
    HELP_DENY_SPAWN("&b/wm denySpawn <Flag> <Welt>\n&8» &7Verbietet das Spawen eines Entities"),
    HELP_ALLOW_INTERACT("&b/wm allowInteract <Flag> <Welt>\n&8» &7Erlaubt Interaktionen mit bestimmten Entities"),
    HELP_DENY_INTERACT("&b/wm denyInteract <Flag> <Welt>\n&8» &7Verbietet Interaktionen mit bestimmten Entities"),
    HELP_INFO("&b/wm info <Welt>\n&8» &7Zeigt Informationen über die angegebene Welt an"),
    HELP_WHITELIST("&b/wm whitelist\n&8» &7Schaltet den Whitelist-Modus einer Welt um"),
    HELP_SETWORLDSPAWN("&b/wm setworldspawn\n&8» &7Setzt den globalen Respawn-Punkt"),
    HELP_FLAGS("&b/wm flags\n&8» &7Liste aller Flags"),
    HELP_WORLDS("&b/wm worlds\n&8» &7Liste aller Welten"),
    HELP_ENTITIES("&b/wm entities\n&8» &7Liste aller Entities"),
    HELP_TYPES("&b/wm types\n&8» &7Liste aller Flag-Typen"),
    HELP_PACKS("&b/wm packs\n&8» &7Liste aller Flag-Pakete"),
    HELP_HELP("&b/wm help\n&8» &7Zeigt Hilfe an"),

    // help subcommand
    HELP_HEADER("&8┏━━━━━━━━━━━━━━━┫ &eSeite %page/%max&8 ┣━━━━━━━━━━━━━━━┓"),
    HELP_FOOTER("&8┗━━━━━━━━━━━━━━━┫ &eSeite %page/%max&8 ┣━━━━━━━━━━━━━━━┛"),
    HELP_INVALID_PAGE("&cEs gibt nur %max Seiten."),

    // interact messages
    INTERACT_FORBIDDEN_ENTITY("&cDie Interaktion mit %entity ist in der Welt %world verboten."),
    ;

    fun getFormatted(): String {
        return ChatColor.translateAlternateColorCodes('&', message)
    }

    fun getMessage(): String {
        return ChatColor.translateAlternateColorCodes('&', PREFIX.message + message)
    }

}