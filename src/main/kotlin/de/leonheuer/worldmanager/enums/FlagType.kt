package de.leonheuer.worldmanager.enums

enum class FlagType {

    PLAYER,
    BLOCK,
    VEHICLE,
    WORLD,
    ;

    companion object {
        fun fromString(type: String): FlagType? {
            for (result in values()) {
                if (result.toString().equals(type, true)) {
                    return result
                }
            }
            return null
        }
    }

}