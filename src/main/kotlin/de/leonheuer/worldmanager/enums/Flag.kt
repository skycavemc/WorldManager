package de.leonheuer.worldmanager.enums

enum class Flag(val type: FlagType, val pack: FlagPackage) {

    // player actions
    BREAK(FlagType.PLAYER, FlagPackage.FULL),
    PLACE(FlagType.PLAYER, FlagPackage.FULL),
    INTERACT(FlagType.PLAYER, FlagPackage.FULL),
    IGNITE(FlagType.PLAYER, FlagPackage.FULL),
    BUCKET_USE(FlagType.PLAYER, FlagPackage.FULL),
    VULNERABLE(FlagType.PLAYER, FlagPackage.EXTENDED),
    PVP(FlagType.PLAYER, FlagPackage.BASIC),
    PVE(FlagType.PLAYER, FlagPackage.EXTENDED),
    SLOW_VOID_DEATH(FlagType.PLAYER, FlagPackage.EXTENDED),

    // block actions
    BLOCK_BURN(FlagType.BLOCK, FlagPackage.BASIC),
    BLOCK_EXPLODE(FlagType.BLOCK, FlagPackage.BASIC),
    BLOCK_SPREAD(FlagType.BLOCK, FlagPackage.FULL),
    BLOCK_GROW(FlagType.BLOCK, FlagPackage.FULL),
    BLOCK_LIGHTNING(FlagType.BLOCK, FlagPackage.FULL),
    BLOCK_FORM(FlagType.BLOCK, FlagPackage.FULL),

    // vehicle actions
    VEHICLE_DAMAGE(FlagType.VEHICLE, FlagPackage.FULL),
    VEHICLE_DESTROY(FlagType.VEHICLE, FlagPackage.FULL),
    VEHICLE_ENTER(FlagType.VEHICLE, FlagPackage.FULL),
    VEHICLE_COLLISION(FlagType.VEHICLE, FlagPackage.FULL),

    // world actions
    ENTITY_SPAWN(FlagType.WORLD, FlagPackage.EXTENDED),
    ENTITY_PICKUP(FlagType.WORLD, FlagPackage.EXTENDED),
    LEAVES_DECAY(FlagType.WORLD, FlagPackage.FULL),
    CREEPER_POWER(FlagType.WORLD, FlagPackage.FULL),
    ;

    companion object {
        fun fromString(flag: String): Flag? {
            for (result in values()) {
                if (result.toString().equals(flag, true)) {
                    return result
                }
            }
            return null
        }
    }

}