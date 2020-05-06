package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.inventories.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static net.miraclepvp.kitpvp.bukkit.Text.color;
import static org.bukkit.Bukkit.getLogger;

public class ShopListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        Player player = ((Player) event.getWhoClicked());

        String invName = ChatColor.stripColor(event.getClickedInventory().getName());

        if (invName.equalsIgnoreCase("Armory")) {
            switch (event.getSlot()) {
                case 0:
                    //Chain Helmet - 150
                    if (pay(player, 150)) {
                        player.getInventory().addItem(new ItemstackFactory(Material.CHAINMAIL_HELMET));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
                case 2:
                    //Chain Chestplate - 400
                    if (pay(player, 400)) {
                        player.getInventory().addItem(new ItemstackFactory(Material.CHAINMAIL_CHESTPLATE));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
                case 4:
                    //Chain Leggings - 200
                    if (pay(player, 200)) {
                        player.getInventory().addItem(new ItemstackFactory(Material.CHAINMAIL_LEGGINGS));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
                case 6:
                    //Iron Boots - 300
                    if (pay(player, 300)) {
                        player.getInventory().addItem(new ItemstackFactory(Material.IRON_BOOTS));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
                case 8:
                    //Stone Sword - 150
                    if (pay(player, 150)) {
                        player.getInventory().addItem(new ItemstackFactory(Material.STONE_SWORD));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
            }
        } else if (invName.equalsIgnoreCase("Brewery")) {
            switch (event.getSlot()) {
                case 1:
                    //Golden Apple - 50
                    if (pay(player, 50)) {
                        player.getInventory().addItem(new ItemstackFactory(Material.GOLDEN_APPLE));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
                case 4:
                    //Golden Apple+ - 200
                    if (pay(player, 200)) {
                        player.getInventory().addItem(new ItemstackFactory(Material.GOLDEN_APPLE, 1, (short) 1).setDisplayName("&5Golden Apple+"));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
                case 7:
                    //3x Arrow - 50
                    if (pay(player, 50)) {
                        player.getInventory().addItem(new ItemstackFactory(Material.ARROW, 3));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
            }
        } else if (invName.equalsIgnoreCase("Blacksmith")) {
            ItemStack hoe = new ItemStack(Material.IRON_HOE);
            ItemMeta hoemeta = hoe.getItemMeta();
            hoemeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
            hoe.setItemMeta(hoemeta);
            hoe.setDurability((short) 230);

            switch (event.getSlot()) {
                case 1:
                    //Protection 1 - 150
                    if (pay(player, 150)) {
                        player.getInventory().addItem(AnvilGUI.getEnchantedBook(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
                case 4:
                    //Sharpness 1 - 400
                    if (pay(player, 400)) {
                        player.getInventory().addItem(AnvilGUI.getEnchantedBook(Enchantment.DAMAGE_ALL, 1));
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
                case 7:
                    //Hoe - 200
                    if (pay(player, 200)) {
                        player.getInventory().addItem(hoe);
                    } else {
                        player.sendMessage(color("&cYou don't have enough banknotes!"));
                    }
                    break;
            }
        } else return;
        event.setCancelled(true);
    }

    public static boolean pay(Player player, Integer price) {
        if (player.getInventory().contains(Material.PAPER)) {
            try {
                ArrayList<Integer> slots = new ArrayList<>();
                AtomicReference<Integer> value = new AtomicReference<>(0);

                for (int slot = 0; slot < player.getInventory().getSize(); slot++) {
                    if (player.getInventory().getItem(slot) != null) {
                        ItemStack item = player.getInventory().getItem(slot);

                        if (item.getType().equals(Material.PAPER) &&
                                item.getItemMeta() != null &&
                                item.getItemMeta().getDisplayName() != null &&
                                item.getItemMeta().getLore() != null &&
                                item.getItemMeta().getDisplayName().startsWith(color("&5$")) &&
                                item.getItemMeta().getDisplayName().endsWith(" Banknote")) {

                            Integer worth = Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getDisplayName()).substring(1, ChatColor.stripColor(item.getItemMeta().getDisplayName()).length()).replaceAll(" Banknote", ""));
                            for (int i = 0; i < item.getAmount(); i++) {
                                slots.add(slot);
                                value.set(value.get() + worth);

                                if (value.get() >= price) {
                                    //If they have enough money
                                    Integer rest = value.get() - price;

                                    slots.forEach(s -> {
                                        ItemStack itemStack = player.getInventory().getItem(s);
                                        value.set(value.get() - Integer.parseInt(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).substring(1, ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).length()).replaceAll(" Banknote", "")));
                                        if (itemStack.getAmount() > 1) {
                                            //More than 1
                                            itemStack.setAmount(itemStack.getAmount() - 1);
                                        } else {
                                            //Only 1
                                            player.getInventory().setItem(s, null);
                                        }
                                    });

                                    while (rest > 0) {
                                        if (rest >= 500) {
                                            rest = rest - 500;
                                            player.getInventory().addItem(BankerListener.getBanknote(500));
                                        } else if (rest >= 250) {
                                            rest = rest - 250;
                                            player.getInventory().addItem(BankerListener.getBanknote(250));
                                        } else if (rest >= 100) {
                                            rest = rest - 100;
                                            player.getInventory().addItem(BankerListener.getBanknote(100));
                                        } else if (rest >= 50) {
                                            rest = rest - 50;
                                            player.getInventory().addItem(BankerListener.getBanknote(50));
                                        } else {
                                            getLogger().warning("The player " + player.getName() + " didn't get " + value + " coins back at the shop.");
                                            rest = 0;
                                        }
                                    }
                                    return true;
                                }

                            }
                        }
                    }
                }
                //Checking if the player has enough money
                if (value.get() < price) return false;
            } catch (Exception ex) {
                return false;
            }
        } else return false;
        return true;
    }

    public static boolean sell(Player player, Integer price) {
        try {
            Integer rest = price;

            while (rest > 0) {
                if (rest >= 500) {
                    rest = rest - 500;
                    player.getInventory().addItem(BankerListener.getBanknote(500));
                } else if (rest >= 250) {
                    rest = rest - 250;
                    player.getInventory().addItem(BankerListener.getBanknote(250));
                } else if (rest >= 100) {
                    rest = rest - 100;
                    player.getInventory().addItem(BankerListener.getBanknote(100));
                } else if (rest >= 50) {
                    rest = rest - 50;
                    player.getInventory().addItem(BankerListener.getBanknote(50));
                } else {
                    getLogger().warning("The player " + player.getName() + " didn't get " + price + " coins back at the bounty hunter.");
                    rest = 0;
                }
            }
        } catch (
                Exception ex) {
            return false;
        }
        return true;
    }
}
