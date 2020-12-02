package net.miraclepvp.kitpvp.inventories.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LeaderboardListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(event.getClickedInventory().getName().contains("Top 27 - "))) return;
        event.setCancelled(true);
    }
}
