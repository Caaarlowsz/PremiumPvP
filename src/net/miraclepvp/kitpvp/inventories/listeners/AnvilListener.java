package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.miraclepvp.kitpvp.bukkit.Text.RomanNumeralToInteger;
import static net.miraclepvp.kitpvp.bukkit.Text.color;
import static net.miraclepvp.kitpvp.inventories.AnvilGUI.*;

public class AnvilListener implements Listener {

    public static ArrayList<Material> weaponList = new ArrayList<>();
    public static ArrayList<Material> armorList = new ArrayList<>();

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked());
        if (player.getOpenInventory().getTopInventory() == null) return;
        if (player.getOpenInventory().getTopInventory().getName() == null) return;
        if (!(ChatColor.stripColor(player.getOpenInventory().getTopInventory().getName()).equalsIgnoreCase("Anvil")))
            return;

        //Get the top inventory (anvil)
        Inventory topInv = player.getOpenInventory().getTopInventory();

        if(event.getClickedInventory() == null) return;

        if(event.getClickedInventory().equals(player.getOpenInventory().getBottomInventory()) &&
                !event.getClick().equals(ClickType.SHIFT_LEFT) && !event.getClick().equals(ClickType.SHIFT_RIGHT)){
            event.setCancelled(false);
            return;
        }

        //GUI inventory
        if (event.getSlot() == 29 || event.getSlot() == 33 ||
                (
                        event.getClickedInventory().equals(player.getOpenInventory().getBottomInventory()) &&
                        event.getClick().equals(ClickType.SHIFT_LEFT) || event.getClick().equals(ClickType.SHIFT_RIGHT)
                )) {
            new BukkitRunnable() {
                @Override
                public void run() {

                    //Get the left and right item
                    ItemStack
                            leftItem = topInv.getItem(29),
                            rightItem = topInv.getItem(33);

                    //Set the default on false
                    Boolean
                            left = false,
                            right = false;


                    if (leftItem != null) {
                        //If there is a item in the left slot
                        Material leftMaterial = leftItem.getType();
                        //If the item is a weapon, armor set left on true
                        if (weaponList.contains(leftMaterial))
                            left = true;
                        else if (armorList.contains(leftMaterial))
                            left = true;

                        if (rightItem != null) {
                            //If there is a item in the right slot
                            ItemStack rightClone = rightItem.clone();
                            switch (rightClone.getType()) { //Checking if the right item is a allowed item in combination with the left item
                                case ENCHANTED_BOOK:
                                    if (((EnchantmentStorageMeta) rightClone.getItemMeta()).getStoredEnchants().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) && armorList.contains(leftItem.getType())) {
                                        right = true;
                                    } else if (((EnchantmentStorageMeta) rightClone.getItemMeta()).getStoredEnchants().containsKey(Enchantment.DAMAGE_ALL) && weaponList.contains(leftItem.getType()))
                                        right = true;
                                    else {
                                        right = false;
                                    }
                                    break;
                                default:
                                    if (leftItem.getType().equals(rightClone.getType()) && left)
                                        right = true;
                                    else
                                        right = false;
                                    break;
                            }
                        }
                    }

                    Boolean finalLeft = left;
                    Boolean finalRight = right;

                    topInv.setItem(11, getUpgrade(finalLeft));
                    topInv.setItem(12, getUpgrade(finalLeft));
                    topInv.setItem(20, getUpgrade(finalLeft));

                    topInv.setItem(14, getSacrifice(finalRight));
                    topInv.setItem(15, getSacrifice(finalRight));
                    topInv.setItem(24, getSacrifice(finalRight));

                    topInv.setItem(45, getIndicator(finalLeft && finalRight));
                    topInv.setItem(46, getIndicator(finalLeft && finalRight));
                    topInv.setItem(47, getIndicator(finalLeft && finalRight));
                    topInv.setItem(48, getIndicator(finalLeft && finalRight));
                    topInv.setItem(50, getIndicator(finalLeft && finalRight));
                    topInv.setItem(51, getIndicator(finalLeft && finalRight));
                    topInv.setItem(52, getIndicator(finalLeft && finalRight));
                    topInv.setItem(53, getIndicator(finalLeft && finalRight));

                    //Set the "Showcase Item"
                    if (!finalLeft || !finalRight) {
                        //No match
                        topInv.setItem(13, new ItemstackFactory(Material.BARRIER)
                                .setDisplayName("&cAnvil")
                                .addLoreLine("&7Place a target item in the left")
                                .addLoreLine("&7slot and a sacrifice item in the")
                                .addLoreLine("&7right slot to combine")
                                .addLoreLine("&7Enchantments"));

                        topInv.setItem(22, new ItemstackFactory(Material.ANVIL)
                                .setDisplayName("&5Combine Items")
                                .addLoreLine("&7Combine the Enchantments of the")
                                .addLoreLine("&7items in the slots to the left")
                                .addLoreLine("&7and right below.")
                        );
                    } else {
                        //Match

                        //Compiling the enchantments
                        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
                        leftItem.getEnchantments().forEach((enchantments::put));

                        Map<Enchantment, Integer> enchts = new HashMap<>();
                        if (rightItem.getType().equals(Material.ENCHANTED_BOOK))
                            enchts = ((EnchantmentStorageMeta) rightItem.getItemMeta()).getStoredEnchants();
                        else
                            enchts = rightItem.getEnchantments();


                        enchts.forEach((enchantment, integer) -> {
                            //Als de enchantment al op het item zit
                            if (enchantments.containsKey(enchantment)) {
                                //Als het nieuwe level hoger is als het oude
                                if (integer > enchantments.get(enchantment)) {
                                    //Als het niet over de maximale level heen gaat
                                    if (integer < enchantment.getMaxLevel()) {
                                        //Verander het level naar het nieuwe level
                                        enchantments.put(enchantment, integer);
                                    }
                                }

                                //Als het nieuwe level hetzelfde is als het oude
                                if (integer == enchantments.get(enchantment)) {
                                    //Als het niet over de maximale level heen gaat
                                    if (integer < enchantment.getMaxLevel()) {
                                        //Verander het level naar het nieuwe level
                                        enchantments.put(enchantment, integer + 1);
                                    }
                                }
                            } else {
                                enchantments.put(enchantment, integer);
                            }
                        });

                        ItemStack item = leftItem.clone();
                        item.addEnchantments(enchantments); //Set the enchantments

                        topInv.setItem(13, item);

                        topInv.setItem(22, new ItemstackFactory(Material.ANVIL)
                                .setDisplayName("&5Combine Items")
                                .addLoreLine("&7Combine the Enchantments of the")
                                .addLoreLine("&7items in the slots to the left")
                                .addLoreLine("&7and right below.")
                                .addLoreLine(" ")
                                .addLoreLine("&7Costs:")
                                .addLoreLine("&7 50 Coins")
                                .addLoreLine(" ")
                                .addLoreLine("&eClick to combine!")
                        );
                    }
                }
            }.runTaskLater(Main.getInstance(), 1L);
        } else if (event.getSlot() == 49) {
            if (topInv.getItem(29) != null) player.getInventory().addItem(topInv.getItem(29));
            if (topInv.getItem(33) != null) player.getInventory().addItem(topInv.getItem(33));
            player.closeInventory();
        } else if (event.getSlot() == 22) {
            if (!topInv.getItem(13).getType().equals(Material.BARRIER)) {
                if (ShopListener.pay(player, 50)) {
                    player.getInventory().addItem(topInv.getItem(13));
                    player.closeInventory();
                } else {
                    player.sendMessage(color("&cYou don't have enough banknotes!"));
                }
            }
            event.setCancelled(true);
        } else
            event.setCancelled(true);
    } //What happends on Click

    public static void prepareAnvil() {
        //Weapons
        weaponList.add(Material.WOOD_SWORD);
        weaponList.add(Material.STONE_SWORD);
        weaponList.add(Material.GOLD_SWORD);
        weaponList.add(Material.IRON_SWORD);
        weaponList.add(Material.DIAMOND_SWORD);

        weaponList.add(Material.WOOD_AXE);
        weaponList.add(Material.STONE_AXE);
        weaponList.add(Material.GOLD_AXE);
        weaponList.add(Material.IRON_AXE);
        weaponList.add(Material.DIAMOND_AXE);

        //Armor
        armorList.add(Material.LEATHER_BOOTS);
        armorList.add(Material.LEATHER_LEGGINGS);
        armorList.add(Material.LEATHER_CHESTPLATE);
        armorList.add(Material.LEATHER_HELMET);

        armorList.add(Material.CHAINMAIL_BOOTS);
        armorList.add(Material.CHAINMAIL_LEGGINGS);
        armorList.add(Material.CHAINMAIL_CHESTPLATE);
        armorList.add(Material.CHAINMAIL_HELMET);

        armorList.add(Material.GOLD_BOOTS);
        armorList.add(Material.GOLD_LEGGINGS);
        armorList.add(Material.GOLD_CHESTPLATE);
        armorList.add(Material.GOLD_HELMET);

        armorList.add(Material.IRON_BOOTS);
        armorList.add(Material.IRON_LEGGINGS);
        armorList.add(Material.IRON_CHESTPLATE);
        armorList.add(Material.IRON_HELMET);

        armorList.add(Material.DIAMOND_BOOTS);
        armorList.add(Material.DIAMOND_LEGGINGS);
        armorList.add(Material.DIAMOND_CHESTPLATE);
        armorList.add(Material.DIAMOND_HELMET);
    } //Fill the lists
}
