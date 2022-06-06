package de.leonheuer.worldmanager.managers

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import de.leonheuer.worldmanager.models.WorldProfile
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.collections.ArrayList

class DataManager(private val main: WorldManager) {

    private val path = main.dataFolder.path
    private val worldsPath = "$path/worlds/"
    private val deletedPath = "$path/deleted/"
    private val worldProfiles = HashMap<UUID, WorldProfile>()
    var spawn: Location? = null

    init {
        readData()
    }

    private fun readData() {
        val jsonParser = JSONParser()

        val dir = File(worldsPath)
        if (!dir.exists()) {
            dir.mkdirs()
            return
        }

        val config = File("${path}config.json")
        if (config.exists()) {
            try {
                FileReader(config).use { reader ->
                    val data = jsonParser.parse(reader) as JSONObject
                    val spawnData = data["spawn"] as JSONObject
                    spawn = Location(
                        Bukkit.getWorld(UUID.fromString(spawnData["world"] as String)),
                        spawnData["x"] as Double,
                        spawnData["y"] as Double,
                        spawnData["z"] as Double,
                        (spawnData["yaw"] as Double).toFloat(),
                        (spawnData["pitch"] as Double).toFloat()
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        for (world in Bukkit.getWorlds()) {
            loadWorld(world)
        }

        if (dir.listFiles() != null) {
            for (file in dir.listFiles()!!) {
                val world = Bukkit.getWorld(UUID.fromString(file.nameWithoutExtension))
                if (world == null) {
                    val dir2 = File(deletedPath)
                    if (!dir2.exists()) {
                        dir2.mkdirs()
                    }
                    try {
                        Files.move(
                            FileSystems.getDefault().getPath(worldsPath, file.name),
                            FileSystems.getDefault().getPath(deletedPath, file.name),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun loadWorld(world: World) {
        val wf = File("${worldsPath}${world.uid}.json")
        if (!wf.exists()) {
            worldProfiles[world.uid] = WorldProfile(this, world, ArrayList(), ArrayList(), ArrayList(), false)
            main.logger.info("Created new world profile for world ${world.name} with UUID ${world.uid}")
            return
        }

        try {
            val parser = JSONParser()
            FileReader(wf).use { reader ->
                val data = parser.parse(reader) as JSONObject

                val flagsData = data["flags"] as JSONArray
                val flags: ArrayList<Flag> = ArrayList()
                flagsData.forEach { flag -> flags.add(Flag.valueOf(flag as String)) }

                val denySpawnData = data["denySpawn"] as JSONArray
                val denySpawn: ArrayList<EntityType> = ArrayList()
                denySpawnData.forEach { entityType -> denySpawn.add(EntityType.valueOf(entityType as String)) }

                val denyInteractData = data["denyInteract"] as JSONArray
                val denyInteract: ArrayList<EntityType> = ArrayList()
                denyInteractData.forEach { entityType -> denyInteract.add(EntityType.valueOf(entityType as String)) }

                val whitelist = (data["whitelist"] as String).toBoolean()
                worldProfiles[world.uid] = WorldProfile(this, world, flags, denySpawn, denyInteract, whitelist)
            }
            main.logger.info("§aSuccessfully loaded world profile for world ${world.name} with UUID ${world.uid}")
        } catch (e: IOException) {
            e.printStackTrace()
            main.logger.severe("Failed to load world profile for world ${world.name} with UUID ${world.uid}")
        } catch (e: ParseException) {
            e.printStackTrace()
            main.logger.severe("Failed to load world profile for world ${world.name} with UUID ${world.uid}")
        }
    }

    fun unloadWorld(world: World) {
        worldProfiles.remove(world.uid)
        main.logger.info("Unloaded world profile for world ${world.name} with UUID ${world.uid}")
    }

    fun writeWorldData(wp: WorldProfile) {
        val wf = File("${worldsPath}${wp.world.uid}.json")
        if (!wf.exists()) {
            try {
                wf.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return
            }
        }

        val data = JSONObject()
        val flags = JSONArray()
        val denySpawn = JSONArray()
        val denyInteract = JSONArray()

        wp.flags.forEach{ flag -> flags.add(flag.toString()) }
        wp.spawnList.forEach { entityType -> denySpawn.add(entityType.toString()) }
        wp.interactList.forEach { entityType -> denyInteract.add(entityType.toString()) }

        data["name"] = wp.world.name
        data["flags"] = flags
        data["denySpawn"] = denySpawn
        data["denyInteract"] = denyInteract
        data["whitelist"] = wp.whitelist.toString()

        try {
            FileWriter(wf).use { writer ->
                writer.write(data.toJSONString())
                writer.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun writeSpawn() {
        val spawn = this.spawn ?: return

        val file = File("${path}config.json")
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return
            }
        }

        val data = JSONObject()
        val spawnData = JSONObject()
        spawnData["world"] = spawn.world.uid.toString()
        spawnData["x"] = spawn.x
        spawnData["y"] = spawn.y
        spawnData["z"] = spawn.z
        spawnData["yaw"] = spawn.yaw
        spawnData["pitch"] = spawn.pitch
        data["spawn"] = spawnData

        try {
            FileWriter(file).use { writer ->
                writer.write(data.toJSONString())
                writer.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getWorldProfile(world: World): WorldProfile? {
        return worldProfiles[world.uid]
    }

}