package me.sahustei.miraclepvp.inventories.listeners;

import me.sahustei.miraclepvp.data.Config;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.kit.Kit;
import me.sahustei.miraclepvp.data.user.User;
import me.sahustei.miraclepvp.inventories.KitGUI;
import me.sahustei.miraclepvp.objects.hasKit;
import me.sahustei.miraclepvp.utils.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Random;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class KitListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked());
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getName() == null) return;
        if (!(event.getClickedInventory().getName().contains("Kit"))) return;
        User user = Data.getUser(player);
        event.setCancelled(true);
        Boolean shop = false;
        Integer page = 0;
        if (org.bukkit.ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Shop"))
            shop = true;
        if(event.getSlot() == 10){
            user.setAutoDeploy(!user.isAutoDeploy());
            player.openInventory(KitGUI.getInventory(player, shop, 1));
        }
        if(event.getSlot() == 19){
            user.setQuickSelect(!user.isQuickSelect());
            player.openInventory(KitGUI.getInventory(player, shop, 1));
        }
        if (event.getSlot() == 35)
            player.openInventory(KitGUI.getInventory(player, !shop, 1));
        if (event.getSlot() == 48 && org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).contains("Previous page")) {
            try {
                String number = org.bukkit.ChatColor.stripColor(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName())
                        .replaceAll("Previous page", "")
                        .replaceAll("\\(", "")
                        .replaceAll("\\)", "")
                        .replaceAll(" ", "");
                page = Integer.parseInt(number);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            player.openInventory(KitGUI.getInventory(player, shop, page));
        }
        if (event.getSlot() == 51 && org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).contains("Next page")) {
            try {
                String number = org.bukkit.ChatColor.stripColor(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName())
                        .replaceAll("Next page", "")
                        .replaceAll("\\(", "")
                        .replaceAll("\\)", "")
                        .replaceAll(" ", "");
                page = Integer.parseInt(number);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            player.openInventory(KitGUI.getInventory(player, shop, page));
        }

        if(event.getSlot() == 53 && !shop){
            //todo open settings
            return;
        }

        if (shop) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            try {
                Kit kit = Data.getKit(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                if (user.getCoins() < kit.getPrice()) {
                    player.sendMessage(color("&cYou dont have enough coins to buy this kit."));
                    event.setCancelled(true);
                    return;
                }
                user.setCoins(user.getCoins() - kit.getPrice());
                user.addKit(kit.getUuid());
                player.sendMessage(color("&aYou've bought the " + kit.getName() + " kit for " + kit.getPrice() + " Coins."));
                player.openInventory(KitGUI.getInventory(player, shop, 1));
            } catch (NoSuchElementException ex) {
            }
        } else {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            try {
                Kit kit = Data.getKit(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                if (event.getClick().equals(ClickType.LEFT)) {
                    player.sendMessage(color("&aYou've selected the " + kit.getName() + " kit."));
                    user.setPreviousKit(kit.getUuid());
                    if(user.isAutoDeploy()) {
                        TeleportUtil.getTeleport(player);
                        kit.giveKit(player);
                        player.setMetadata("kit", new hasKit());
                    }
                    player.closeInventory();
                } else {
                    user.setTokens(user.getTokens() + kit.getSellprice());
                    user.removeKit(kit.getUuid());
                    player.sendMessage(color("&aYou've sold the " + kit.getName() + " kit."));
                    player.openInventory(KitGUI.getInventory(player, shop, 1));
                }
            } catch (NoSuchElementException ex) {
            }
        }
    }
}
