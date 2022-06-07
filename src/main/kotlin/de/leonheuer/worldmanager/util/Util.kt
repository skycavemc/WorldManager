package de.leonheuer.worldmanager.util

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Recipe

object Util {

    fun removeRecipe(mat: Material) {
        val iterator = Bukkit.getServer().recipeIterator()
        var recipe: Recipe?
        while (iterator.hasNext()) {
            recipe = iterator.next()
            if (recipe != null && recipe.result.type == mat) {
                iterator.remove()
            }
        }
    }

    fun getListOfSummoners(): List<Material> {
        val materials = ArrayList<Material>()

        materials.add(Material.ARMOR_STAND)
        materials.add(Material.ITEM_FRAME)
        materials.add(Material.PAINTING)
        materials.add(Material.END_CRYSTAL)

        Material.values().filter {
            it.toString().contains("POTION") || it.toString().contains("SPAWN_EGG") ||
                    it.toString().contains("MINECART") || it.toString().contains("BOAT")
        }.forEach(materials::add)

        return materials
    }

}