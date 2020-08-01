package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.commands.FreezeCMD;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class inventoryClose implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player)event.getPlayer();
        if(player.getOpenInventory().getTopInventory() != null){
            //There is a top inventory
            Inventory topInventory = player.getInventory();

            //If Anvil
            if(ChatColor.stripColor(topInventory.getName()).equalsIgnoreCase("Anvil")){
                if (topInventory.getItem(29) != null) player.getInventory().addItem(topInventory.getItem(29));
                if (topInventory.getItem(33) != null) player.getInventory().addItem(topInventory.getItem(33));
            }
        }
    }
}
