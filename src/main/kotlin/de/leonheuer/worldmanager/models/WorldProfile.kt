package de.leonheuer.worldmanager.models

import de.leonheuer.worldmanager.enums.Flag
import de.leonheuer.worldmanager.managers.DataManager
import org.bukkit.World
import org.bukkit.entity.EntityType

class WorldProfile(
    private val dm: DataManager,
    val world: World,
    val flags: ArrayList<Flag>,
    val spawnList: ArrayList<EntityType>,
    val interactList: ArrayList<EntityType>,
    var whitelist: Boolean
) {

    fun isFlagDenied(flag: Flag): Boolean {
        return flags.contains(flag)
    }

    fun denyFlag(flag: Flag): Boolean {
        return if (flags.contains(flag)) {
            false
        } else {
            flags.add(flag)
            dm.writeWorldData(this)
            true
        }
    }

    fun allowFlag(flag: Flag): Boolean {
        return if (!flags.contains(flag)) {
            false
        } else {
            flags.remove(flag)
            dm.writeWorldData(this)
            true
        }
    }

    fun isSpawnDenied(entityType: EntityType): Boolean {
        return spawnList.contains(entityType)
    }

    fun denySpawn(entityType: EntityType): Boolean {
        return if (spawnList.contains(entityType)) {
            false
        } else {
            spawnList.add(entityType)
            dm.writeWorldData(this)
            true
        }
    }

    fun allowSpawn(entityType: EntityType): Boolean {
        return if (!spawnList.contains(entityType)) {
            false
        } else {
            spawnList.remove(entityType)
            dm.writeWorldData(this)
            true
        }
    }

    fun isInteractDenied(entityType: EntityType): Boolean {
        return interactList.contains(entityType)
    }

    fun denyInteract(entityType: EntityType): Boolean {
        return if (interactList.contains(entityType)) {
            false
        } else {
            interactList.add(entityType)
            dm.writeWorldData(this)
            true
        }
    }

    fun allowInteract(entityType: EntityType): Boolean {
        return if (!interactList.contains(entityType)) {
            false
        } else {
            interactList.remove(entityType)
            dm.writeWorldData(this)
            true
        }
    }

}