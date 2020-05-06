package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.Abilities;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.AbilityGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AbilityListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).equalsIgnoreCase("Abilities"))) return;
        event.setCancelled(true);
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getLore() == null) return;
        String[] words = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0)).split(" ");
        Abilities.AbilityType type = Abilities.AbilityType.valueOf(words[0].toUpperCase());
        event.getWhoClicked().openInventory(AbilityGUI.getTypeInventory(((Player) event.getWhoClicked()), type));
    }

    @EventHandler
    public void onClickk(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).equalsIgnoreCase("Abilities - Upgrade"))) return;
        event.setCancelled(true);
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(event.getSlot() == 26) {
            event.getWhoClicked().openInventory(AbilityGUI.getMainInventory());
            return;
        }
        String[] words = ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getLore().get(0)).split(" ");
        Abilities.AbilityType type = Abilities.AbilityType.valueOf(words[0].toUpperCase());
        Integer[] slots = {11,12,13,14,15,16};
        if(Arrays.stream(slots).filter(slot -> slot.equals(event.getSlot())).findFirst() == null) return;
        Integer level = event.getSlot()-10;
        String price = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())
                .replaceAll("Click to Buy - ", "").replaceAll(" Coins", "");
        try{
            Integer.parseInt(price);
        }catch (Exception e){
            return;
        }
        Integer cost = Integer.parseInt(price);
        User user = Data.getUser(((Player) event.getWhoClicked()));
        if(user.getCoins() < cost){
            event.getWhoClicked().sendMessage(color("&cYou don't have enough coins to buy this."));
            return;
        }
        user.setCoins(user.getCoins()-cost, false);
        user.getAbilities().put(type, level);
        event.getWhoClicked().openInventory(AbilityGUI.getTypeInventory(((Player) event.getWhoClicked()), type));
    }
}
