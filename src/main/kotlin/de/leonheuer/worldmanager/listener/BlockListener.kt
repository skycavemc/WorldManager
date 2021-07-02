package de.leonheuer.worldmanager.listener

import de.leonheuer.worldmanager.WorldManager
import de.leonheuer.worldmanager.enums.Flag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*

class BlockListener(private val main: WorldManager) : Listener {

    @EventHandler
    fun onBlockBurn(event: BlockBurnEvent) {
        if (main.dm!!.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.BLOCK_BURN)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        if (main.dm!!.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.BLOCK_EXPLODE)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockGrow(event: BlockGrowEvent) {
        if (main.dm!!.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.BLOCK_GROW)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockSpread(event: BlockSpreadEvent) {
        if (main.dm!!.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.BLOCK_SPREAD)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockForm(event: BlockFormEvent) {
        if (main.dm!!.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.BLOCK_FORM)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockForm(event: EntityBlockFormEvent) {
        if (main.dm!!.getWorldProfile(event.block.world)!!.isFlagDenied(Flag.BLOCK_FORM)) {
            event.isCancelled = true
        }
    }

}