package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.CosmeticsGUI;
import net.miraclepvp.kitpvp.objects.CosmeticType;
import net.miraclepvp.kitpvp.utils.Trails;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CosmeticsListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked());
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getName() == null) return;
        if (!(event.getClickedInventory().getName().contains("Cosmetic"))) return;
        User user = Data.getUser(player);
        event.setCancelled(true);
        Boolean shop = false;
        Integer page = 0;
        if (org.bukkit.ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Shop"))
            shop = true;
        CosmeticType cosmeticType = CosmeticType.valueOf(org.bukkit.ChatColor.stripColor(event.getClickedInventory().getName()
                .replaceAll((shop ? "Shop" : "Selector") + " - Cosmetic:", "").replaceAll(" ", "")));
        HashMap<Integer, CosmeticType> cosmeticMap = new HashMap<>();
        CosmeticType.types.forEach(value -> cosmeticMap.put(CosmeticsGUI.getSlot(value.getPosition()), value));
        if (cosmeticMap.containsKey(event.getSlot()))
            player.openInventory(CosmeticsGUI.getInventory(player, cosmeticMap.get(event.getSlot()), shop, 1));
        if (event.getSlot() == 35)
            player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, !shop, 1));
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
            player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, page));
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
            player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, page));
        }

        if(event.getSlot() == 53 && !shop){
            if(cosmeticType.equals(CosmeticType.Trail))
                user.setActiveTrail(null);
            if(cosmeticType.equals(CosmeticType.Suffix))
                user.setActiveSuffix(null);
            if(cosmeticType.equals(CosmeticType.ChatColor))
                user.setActiveChatcolor(null);
            if(cosmeticType.equals(CosmeticType.NameColor))
                user.setActiveNamecolor(null);
            player.sendMessage(color("&aYou've disabled your " + cosmeticType.getName() + "!"));
            return;
        }

        if (shop) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            if (cosmeticType.equals(CosmeticType.Trail)) {
                try {
                    Trail trail = Data.getTrail(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (user.getTokens() < trail.getCost()) {
                        player.sendMessage(color("&cYou dont have enough CosmoTokens to buy this trail."));
                        event.setCancelled(true);
                        return;
                    }
                    user.setTokens(user.getTokens() - trail.getCost());
                    user.addTrail(trail.getUuid());
                    player.sendMessage(color("&aYou've bought the " + trail.getName() + " trail for " + trail.getCost() + " CosmoPoints."));
                    player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, 1));
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.Suffix)) {
                try {
                    Suffix suffix = Data.getSuffix(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (user.getTokens() < suffix.getCost()) {
                        player.sendMessage(color("&cYou dont have enough CosmoTokens to buy this suffix."));
                        event.setCancelled(true);
                        return;
                    }
                    user.setTokens(user.getTokens() - suffix.getCost());
                    user.addSuffix(suffix.getUuid());
                    player.sendMessage(color("&aYou've bought the " + suffix.getName() + " suffix for " + suffix.getCost() + " CosmoPoints."));
                    player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, 1));
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.ChatColor)) {
                try {
                    Chatcolor chatcolor = Data.getChatcolor(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (user.getTokens() < chatcolor.getCost()) {
                        player.sendMessage(color("&cYou dont have enough CosmoTokens to buy this chatcolor."));
                        event.setCancelled(true);
                        return;
                    }
                    user.setTokens(user.getTokens() - chatcolor.getCost());
                    user.addChatColor(chatcolor.getUuid());
                    player.sendMessage(color("&aYou've bought the " + chatcolor.getName() + " chatcolor for " + chatcolor.getCost() + " CosmoPoints."));
                    player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, 1));
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.NameColor)) {
                try {
                    Namecolor namecolor = Data.getNamecolor(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (user.getTokens() < namecolor.getCost()) {
                        player.sendMessage(color("&cYou dont have enough CosmoTokens to buy this namecolor."));
                        event.setCancelled(true);
                        return;
                    }
                    user.setTokens(user.getTokens() - namecolor.getCost());
                    user.addNameColor(namecolor.getUuid());
                    player.sendMessage(color("&aYou've bought the " + namecolor.getName() + " namecolor for " + namecolor.getCost() + " CosmoPoints."));
                    player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, 1));
                } catch (NoSuchElementException ex) {
                }
            }
        } else {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            if (cosmeticType.equals(CosmeticType.Trail)) {
                try {
                    Trail trail = Data.getTrail(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (event.getClick().equals(ClickType.LEFT)) {
                        Main.getInstance().playerTrails.put(player.getUniqueId(), new Trails(trail.getParticle()));
                        user.setActiveTrail(trail);
                        player.sendMessage(color("&aYou've selected the " + trail.getName() + " trail."));
                        player.closeInventory();
                    } else {
                        user.setTokens(user.getTokens() + trail.getSell());
                        if(user.getActiveTrail() != null && user.getActiveTrail().equals(trail.getUuid()))
                            user.setActiveTrail(null);
                        user.removeTrail(trail.getUuid());
                        player.sendMessage(color("&aYou've sold the " + trail.getName() + " trail."));
                        player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, 1));
                    }
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.Suffix)) {
                try {
                    Suffix suffix = Data.getSuffix(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (event.getClick().equals(ClickType.LEFT)) {
                        user.setActiveSuffix(suffix);
                        player.sendMessage(color("&aYou've selected the " + suffix.getName() + " suffix."));
                        player.closeInventory();
                    } else {
                        user.setTokens(user.getTokens() + suffix.getSell());
                        if(user.getActiveSuffix() != null && user.getActiveSuffix().equals(suffix.getUuid()))
                            user.setActiveSuffix(null);
                        user.removeSuffixes(suffix.getUuid());
                        player.sendMessage(color("&aYou've sold the " + suffix.getName() + " suffix."));
                        player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, 1));
                    }
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.ChatColor)) {
                try {
                    Chatcolor chatcolor = Data.getChatcolor(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (event.getClick().equals(ClickType.LEFT)) {
                        user.setActiveChatcolor(chatcolor);
                        player.sendMessage(color("&aYou've selected the " + chatcolor.getName() + " chatcolor."));
                        player.closeInventory();
                    } else {
                        user.setTokens(user.getTokens() + chatcolor.getSell());
                        if(user.getActiveChatcolor() != null && user.getActiveChatcolor().equals(chatcolor.getUuid()))
                            user.setActiveChatcolor(null);
                        user.removeChatColor(chatcolor.getUuid());
                        player.sendMessage(color("&aYou've sold the " + chatcolor.getName() + " chatcolor."));
                        player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, 1));
                    }
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.NameColor)) {
                try {
                    Namecolor namecolor = Data.getNamecolor(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (event.getClick().equals(ClickType.LEFT)) {
                        user.setActiveNamecolor(namecolor);
                        player.sendMessage(color("&aYou've selected the " + namecolor.getName() + " namecolor."));
                        player.closeInventory();
                    } else {
                        user.setTokens(user.getTokens() + namecolor.getSell());
                        if(user.getActiveNamecolor() != null && user.getActiveNamecolor().equals(namecolor.getUuid()))
                            user.setActiveNamecolor(null);
                        user.removeNameColor(namecolor.getUuid());
                        player.sendMessage(color("&aYou've sold the " + namecolor.getName() + " namecolor."));
                        player.openInventory(CosmeticsGUI.getInventory(player, cosmeticType, shop, 1));
                    }
                } catch (NoSuchElementException ex) {
                }
            }
        }
    }
}
