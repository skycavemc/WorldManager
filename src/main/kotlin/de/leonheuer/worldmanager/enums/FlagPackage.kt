package de.leonheuer.worldmanager.enums

enum class FlagPackage {

    EXTENDED,
    FULL,
    BASIC,
    ;

    companion object {
        fun fromString(pack: String?): FlagPackage? {
            for (result in values()) {
                if (result.toString().equals(pack, ignoreCase = true)) {
                    return result
                }
            }
            return null
        }
    }

}