package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.KitEditGUI;
import net.miraclepvp.kitpvp.inventories.KitGUI;
import net.miraclepvp.kitpvp.inventories.KitLayoutGUI;
import net.miraclepvp.kitpvp.listeners.player.playerJoin;
import net.miraclepvp.kitpvp.objects.hasKit;
import net.miraclepvp.kitpvp.utils.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        if (!(event.getClickedInventory().getName().endsWith(" - Kit"))) return;
        if(event.getClickedInventory().getName().startsWith(color("&8Kit Preview - "))) return;
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
        if (event.getSlot() == 51 &&
                event.getCurrentItem() != null &&
                event.getCurrentItem().getItemMeta() != null &&
                event.getCurrentItem().getItemMeta().getDisplayName() != null &&
                org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).contains("Next page")
        ) {
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

        if (shop) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            try {
                Kit kit = Data.getKit(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                if (event.getClick().equals(ClickType.LEFT)) {
                    if (user.getCoins() < kit.getPrice()) {
                        player.sendMessage(color("&cYou dont have enough coins to buy this kit."));
                        event.setCancelled(true);
                        return;
                    }
                    player.openInventory(KitGUI.getConfirmation(false, kit));
                } else {
                    player.openInventory(KitEditGUI.getPreviewInventory(kit, false));
                }
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
                        if(Duel.invites.containsKey(player.getUniqueId())){
                            player.sendMessage(color("&cYou can't deploy while your invite is still open."));
                            return;
                        }
                        TeleportUtil.getTeleport(player);
                        if(user.giveKit(user.getPreviousKit(), true, true)){
                            player.setMetadata("kit", new hasKit());
                            player.setAllowFlight(false);
                            player.setFlying(false);
                        }else {
                            player.sendMessage(color("&cCouldn't set your kit, something went wrong."));
                            playerJoin.handleSpawn(player);
                        }
                    }

                } else if (event.getClick().equals(ClickType.RIGHT)) {
                    if(kit.getPrice() == 0){
                        player.sendMessage(color("&cYou can't sell a free kit."));
                        return;
                    }
                    player.openInventory(KitGUI.getConfirmation(true, kit));
                } else if (event.getClick().equals(ClickType.SHIFT_LEFT)){
                    player.openInventory(KitLayoutGUI.getPreviewInventory(kit));
                }
            } catch (NoSuchElementException ex) {
            }
        }
    }

    @EventHandler
    public void onConfirmClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked());
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Kit ") && ChatColor.stripColor(event.getClickedInventory().getName()).endsWith("Confirmation"))) return;
        User user = Data.getUser(player);
        event.setCancelled(true);

        Boolean sell = ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(1)).contains("selling your ");

        Kit kit = Data.getKit( ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(1)).replaceAll("selling your ", "").replaceAll("buying the ", " ").trim());

        if(event.getSlot() == 2){
            player.sendMessage(color("&aYou've cancelled to " + (sell ? "sell" : "buy") + " the " + kit.getName() + " kit."));
            player.openInventory(KitGUI.getInventory(player, false, 1));
        }
        else if(event.getSlot() == 6) {
            if(sell) {
                user.setCoins(user.getCoins() + kit.getSellprice(), false);
                user.getKitsList().remove(kit.getUuid());
                player.sendMessage(color("&aYou've sold the " + kit.getName() + " kit for " + kit.getSellprice() + " Coins." ));
                player.openInventory(KitGUI.getInventory(player, false, 1));
            } else {
                user.setCoins(user.getCoins() - kit.getPrice(), false);
                user.getKitsList().add(kit.getUuid());
                player.sendMessage(color("&aYou've bought the " + kit.getName() + " kit for " + kit.getPrice() + " Coins."));
                player.openInventory(KitGUI.getInventory(player, true, 1));
            }
        }
    }
}
