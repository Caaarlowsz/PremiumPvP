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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;
import static org.bukkit.Bukkit.getServer;

public class CosmeticsListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked());
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getName() == null) return;
        if (!(event.getClickedInventory().getName().contains(" - Cosmetic: "))) return;
        User user = Data.getUser(player);
        event.setCancelled(true);
        Boolean shop = false;
        Integer page = 0;
        if (org.bukkit.ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Shop"))
            shop = true;
        CosmeticType cosmeticType = CosmeticType.valueOf(org.bukkit.ChatColor.stripColor(event.getClickedInventory().getName()
                .replaceAll((shop ? "Shop" : "Selector") + " - Cosmetic:", "").replaceAll(" ", "")).toUpperCase());
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
            if(cosmeticType.equals(CosmeticType.TRAIL))
                user.setActiveTrail(null);
            if(cosmeticType.equals(CosmeticType.SUFFIX))
                user.setActiveSuffix(null);
            if(cosmeticType.equals(CosmeticType.CHATCOLOR))
                user.setActiveChatcolor(null);
            if(cosmeticType.equals(CosmeticType.NAMECOLOR))
                user.setActiveNamecolor(null);
            player.sendMessage(color("&aYou've disabled your " + cosmeticType.getName() + "!"));
            return;
        }

        if (shop) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            if (cosmeticType.equals(CosmeticType.TRAIL)) {
                try {
                    Trail trail = Data.getTrail(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (user.getTokens() < trail.getCost()) {
                        player.sendMessage(color("&cYou dont have enough CosmoTokens to buy this trail."));
                        event.setCancelled(true);
                        return;
                    }
                    player.openInventory(CosmeticsGUI.getConfirmation(false, CosmeticType.TRAIL, trail.getUuid()));
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.SUFFIX)) {
                try {
                    Suffix suffix = Data.getSuffix(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (user.getTokens() < suffix.getCost()) {
                        player.sendMessage(color("&cYou dont have enough CosmoTokens to buy this suffix."));
                        event.setCancelled(true);
                        return;
                    }
                    player.openInventory(CosmeticsGUI.getConfirmation(false, CosmeticType.SUFFIX, suffix.getUuid()));
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.CHATCOLOR)) {
                try {
                    Chatcolor chatcolor = Data.getChatcolor(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (user.getTokens() < chatcolor.getCost()) {
                        player.sendMessage(color("&cYou dont have enough CosmoTokens to buy this chatcolor."));
                        event.setCancelled(true);
                        return;
                    }
                    player.openInventory(CosmeticsGUI.getConfirmation(false, CosmeticType.CHATCOLOR, chatcolor.getUuid()));
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.NAMECOLOR)) {
                try {
                    Namecolor namecolor = Data.getNamecolor(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (user.getTokens() < namecolor.getCost()) {
                        player.sendMessage(color("&cYou dont have enough CosmoTokens to buy this namecolor."));
                        event.setCancelled(true);
                        return;
                    }
                    player.openInventory(CosmeticsGUI.getConfirmation(false, CosmeticType.NAMECOLOR, namecolor.getUuid()));
                } catch (NoSuchElementException ex) {
                }
            }
        } else {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            if (cosmeticType.equals(CosmeticType.TRAIL)) {
                try {
                    Trail trail = Data.getTrail(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (event.getClick().equals(ClickType.LEFT)) {
                        Main.getInstance().playerTrails.put(player.getUniqueId(), new Trails(trail.getParticle()));
                        user.setActiveTrail(trail);
                        player.sendMessage(color("&aYou've selected the " + trail.getName() + " trail."));
                        player.closeInventory();
                    } else {
                        player.openInventory(CosmeticsGUI.getConfirmation(true, CosmeticType.TRAIL, trail.getUuid()));
                    }
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.SUFFIX)) {
                try {
                    Suffix suffix = Data.getSuffix(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (event.getClick().equals(ClickType.LEFT)) {
                        user.setActiveSuffix(suffix);
                        player.sendMessage(color("&aYou've selected the " + suffix.getName() + " suffix."));
                        player.closeInventory();
                    } else {
                        if(!suffix.getBuyable()){
                            player.sendMessage(color("&cYou can not sell this suffix."));
                            return;
                        }
                        player.openInventory(CosmeticsGUI.getConfirmation(true, CosmeticType.SUFFIX, suffix.getUuid()));
                    }
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.CHATCOLOR)) {
                try {
                    Chatcolor chatcolor = Data.getChatcolor(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (event.getClick().equals(ClickType.LEFT)) {
                        user.setActiveChatcolor(chatcolor);
                        player.sendMessage(color("&aYou've selected the " + chatcolor.getName() + " chatcolor."));
                        player.closeInventory();
                    } else {
                        player.openInventory(CosmeticsGUI.getConfirmation(true, CosmeticType.CHATCOLOR, chatcolor.getUuid()));
                    }
                } catch (NoSuchElementException ex) {
                }
            }
            if (cosmeticType.equals(CosmeticType.NAMECOLOR)) {
                try {
                    Namecolor namecolor = Data.getNamecolor(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if (event.getClick().equals(ClickType.LEFT)) {
                        user.setActiveNamecolor(namecolor);
                        player.sendMessage(color("&aYou've selected the " + namecolor.getName() + " namecolor."));
                        player.closeInventory();
                    } else {
                        player.openInventory(CosmeticsGUI.getConfirmation(true, CosmeticType.NAMECOLOR, namecolor.getUuid()));
                    }
                } catch (NoSuchElementException ex) {
                }
            }
        }
    }

    @EventHandler
    public void onConfirmClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked());
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Cosmetic ") && ChatColor.stripColor(event.getClickedInventory().getName()).endsWith("Confirmation"))) return;
        User user = Data.getUser(player);
        event.setCancelled(true);

        Boolean sell = ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(1)).contains("selling your ");

        String name = ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(1))
                .replaceAll("selling your ", "").replaceAll("buying the ", " ").trim();

        if(name == "NaN"){
            player.closeInventory();
            return;
        }

        CosmeticType type = CosmeticType.SUFFIX;
        UUID uuid = null;
        Integer
                sellPrice = 0,
                buyPrice = 0;

        try{
            type = CosmeticType.valueOf(ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(2)).split(" ")[0].toUpperCase());
            switch (type) {
                case TRAIL:
                    Trail object = Data.getTrail(name);
                    uuid = object.getUuid();
                    sellPrice = object.getSell();
                    buyPrice = object.getCost();
                    break;
                case SUFFIX:
                    Suffix object2 = Data.getSuffix(name);
                    uuid = object2.getUuid();
                    sellPrice = object2.getSell();
                    buyPrice = object2.getCost();
                    break;
                case CHATCOLOR:
                    Chatcolor object3 = Data.getChatcolor(name);
                    uuid = object3.getUuid();
                    sellPrice = object3.getSell();
                    buyPrice = object3.getCost();
                    break;
                case NAMECOLOR:
                    Namecolor object4 = Data.getNamecolor(name);
                    uuid = object4.getUuid();
                    sellPrice = object4.getSell();
                    buyPrice = object4.getCost();
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            getServer().getLogger().info("An error occurred on CosmeticListener::260");
            player.closeInventory();
        }

        if(event.getSlot() == 2){
            player.sendMessage(color("&aYou've cancelled to " + (sell ? "sell" : "buy") + " the " + name + " cosmetic."));
            player.openInventory(CosmeticsGUI.getInventory(player, type, false, 1));
        }
        else if(event.getSlot() == 6) {
            if(sell) {
                user.setTokens(user.getTokens() + sellPrice);
                switch (type){
                    case TRAIL:
                        if(user.getActiveTrail() != null && user.getActiveTrail().equals(uuid))
                            user.setActiveTrail(null);
                        user.removeTrail(uuid);
                        break;
                    case SUFFIX:
                        if(user.getActiveSuffix() != null && user.getActiveSuffix().equals(uuid))
                            user.setActiveSuffix(null);
                        user.removeSuffixes(uuid);
                        break;
                    case CHATCOLOR:
                        if(user.getActiveChatcolor() != null && user.getActiveChatcolor().equals(uuid))
                            user.setActiveChatcolor(null);
                        user.removeChatColor(uuid);
                        break;
                    case NAMECOLOR:
                        if(user.getActiveNamecolor() != null && user.getActiveNamecolor().equals(uuid))
                            user.setActiveNamecolor(null);
                        user.removeNameColor(uuid);
                        break;
                }
                player.sendMessage(color("&aYou've sold the " + name + " " + type.getName() + " for " + sellPrice + " tokens."));
                player.openInventory(CosmeticsGUI.getInventory(player, type, false, 1));
            } else {
                user.setTokens(user.getTokens() - buyPrice);
                switch (type){
                    case TRAIL:
                        user.addTrail(uuid);
                        break;
                    case SUFFIX:
                        user.addSuffix(uuid);
                        break;
                    case CHATCOLOR:
                        user.addChatColor(uuid);
                        break;
                    case NAMECOLOR:
                        user.addNameColor(uuid);
                        break;
                }
                player.sendMessage(color("&aYou've bought the " + name + " " + type.toString().toLowerCase() + " for " + buyPrice + " tokens."));
                player.openInventory(CosmeticsGUI.getInventory(player, type, true, 1));
            }
        }
    }
}
