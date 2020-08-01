package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.objects.miracleAnvil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

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
        else
            if(event.getItemInHand() != null && event.getItemInHand().getType() != null && event.getItemInHand().getType().equals(Material.ANVIL) && event.getItemInHand().getItemMeta() != null && event.getItemInHand().getItemMeta().getDisplayName() != null)
                if(event.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(color("&5Anvil")))
                    event.getBlockPlaced().setMetadata("miracleAnvil", new miracleAnvil());
    }
}
