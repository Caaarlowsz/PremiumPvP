package net.miraclepvp.kitpvp.inventories.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class FreezeListener implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getInventory().getName().contains(color("&8You are frozen!"))) {
            event.setCancelled(true);
        }
    }
}
