package net.miraclepvp.kitpvp.listeners.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class inventoryClose implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player)event.getPlayer(); //Get the player
        if(player.getOpenInventory().getTopInventory() != null){ //If there is a top inventory
            Inventory topInventory = player.getInventory(); //Get top inventory

            if(ChatColor.stripColor(topInventory.getName()).equalsIgnoreCase("Anvil")){ //If the top inventory is the custom anvil
                if (topInventory.getItem(29) != null) player.getInventory().addItem(topInventory.getItem(29)); //Give the item in the first slot back
                if (topInventory.getItem(33) != null) player.getInventory().addItem(topInventory.getItem(33)); //Give the item in the second slot back
            }
        }
    }
}
