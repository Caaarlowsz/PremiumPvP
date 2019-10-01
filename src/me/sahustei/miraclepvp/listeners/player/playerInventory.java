package me.sahustei.miraclepvp.listeners.player;

import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.user.User;
import me.sahustei.miraclepvp.inventories.CosmeticsGUI;
import me.sahustei.miraclepvp.inventories.KitGUI;
import me.sahustei.miraclepvp.objects.CosmeticType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class playerInventory implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getPlayer().hasMetadata("kit")) return;
        Player player = event.getPlayer();
        User user = Data.getUser(player);
        if(user == null) return;
        switch(event.getItem().getType()){
            case COMPASS:
                player.openInventory(KitGUI.getInventory(player, false, 1));
                break;
            case CHEST:
                player.sendMessage("kit unlocker");
                break;
            case SKULL_ITEM:
                player.sendMessage("profile");
                break;
            case BLAZE_POWDER:
                player.openInventory(CosmeticsGUI.getInventory(player, CosmeticType.valueOf(user.getLastCosmeticType()), user.getCosmeticWasShop(), 1));
                break;
        }
    }

    @EventHandler
    public void onMove(InventoryClickEvent event){
        if(event.getWhoClicked().hasMetadata("kit")) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(!(event.getClickedInventory().equals(event.getWhoClicked().getInventory()))) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }
}
