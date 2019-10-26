package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.KitGUI;
import net.miraclepvp.kitpvp.objects.hasKit;
import net.miraclepvp.kitpvp.utils.TeleportUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

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
            if(!player.hasPermission("miraclepvp.donator.autodeploy")){
                player.sendMessage(color("&4You don't have enough permissions to do this!"));
                return;
            }
            user.setAutoDeploy(!user.isAutoDeploy());
            player.openInventory(KitGUI.getInventory(player, shop, 1));
        }
        if(event.getSlot() == 19){
            if(!player.hasPermission("miraclepvp.donator.quickselect")){
                player.sendMessage(color("&4You don't have enough permissions to do this!"));
                return;
            }
            user.setQuickSelect(!user.isQuickSelect());
            player.openInventory(KitGUI.getInventory(player, shop, 1));
        }
        if(event.getSlot() == 28){
            if(!player.hasPermission("miraclepvp.donator.killbroadcast")){
                player.sendMessage(color("&4You don't have enough permissions to do this!"));
                return;
            }
            user.setKillBroadcast(!user.isKillBroadcast());
            player.openInventory(KitGUI.getInventory(player, shop, 1));
        }
        if(event.getSlot() == 37){
            if(!player.hasPermission("miraclepvp.donator.streakbroadcast")){
                player.sendMessage(color("&4You don't have enough permissions to do this!"));
                return;
            }
            user.setStreakBroadcast(!user.isStreakBroadcast());
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
            //todo open layout editor
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
                    if(!kit.isEnabled()){
                        player.sendMessage(color("&cThis kit is temporary disabled, try again later."));
                        return;
                    }
                    player.sendMessage(color("&aYou've selected the " + kit.getName() + " kit."));
                    user.setPreviousKit(kit.getUuid());
                    if(user.isAutoDeploy()) {
                        TeleportUtil.getTeleport(player);
                        kit.giveKit(player);
                        player.setMetadata("kit", new hasKit());
                    }
                    player.closeInventory();
                } else {
                    if(kit.getPrice() == 0){
                        player.sendMessage(color("&cYou can't sell a free kit."));
                        return;
                    }
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
