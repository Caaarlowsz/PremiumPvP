package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.KitGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class KitLayoutListener implements Listener {

    //Prevent item adding or removing
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryMoveItemEvent event) {
        if (event.getDestination() == null) return;
        if (event.getDestination().getName() == null) return;
        if (!event.getDestination().getName().startsWith(color("&8Layout Editor - "))) return;
        if(!event.getSource().getName().equalsIgnoreCase(event.getDestination().getName())) return;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked());
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!event.getClickedInventory().getName().startsWith(color("&8Layout Editor - "))) return;

        if(event.getSlot() <= 26 || (event.getSlot() >= 36 && event.getSlot() <=44)){
            if(event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) event.setCancelled(true);
            if(event.getAction().equals(InventoryAction.PLACE_ALL) || event.getAction().equals(InventoryAction.PLACE_ONE) || event.getAction().equals(InventoryAction.PLACE_SOME)){
                if(event.getInventory().getItem(event.getSlot()) != null && !event.getInventory().getItem(event.getSlot()).getType().equals(Material.AIR)) event.setCancelled(true);
            }
            return;
        }

        event.setCancelled(true);

        if(event.getSlot() == 45){
            User user = Data.getUser(player);
            Kit kit = Data.getKit(ChatColor.stripColor(event.getClickedInventory().getName().replaceAll("Layout Editor - ", "")));
            user.setToKitLayouts(kit.getUuid(), event.getInventory());
            player.openInventory(KitGUI.getInventory(player, false, 1));
            player.sendMessage(color("&aYou've succesfully saved the custom layout for " + kit.getName() + "."));
        }

        if(event.getSlot() == 46)
            player.openInventory(KitGUI.getInventory(player, false, 1));
    }
}
