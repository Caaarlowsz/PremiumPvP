package me.sahustei.miraclepvp.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class playerBlockChange implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(!(event.getPlayer().hasMetadata("build")))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event){
        if(!(event.getPlayer().hasMetadata("build")))
            event.setCancelled(true);
    }
}
