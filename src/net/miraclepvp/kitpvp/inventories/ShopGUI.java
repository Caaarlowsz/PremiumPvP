package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ShopGUI {

    public static Inventory getArmory(){
        Inventory inv = Bukkit.createInventory(null, 9, color("&8Armory"));

        for(int i = 0; i<inv.getSize();i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7));

        inv.setItem(0, getItem("Chain Helmet", 150, new ItemStack(Material.CHAINMAIL_HELMET)));
        inv.setItem(2, getItem("Chain Chestplate", 400, new ItemStack(Material.CHAINMAIL_CHESTPLATE)));
        inv.setItem(4, getItem("Chain Leggings", 200, new ItemStack(Material.CHAINMAIL_LEGGINGS)));
        inv.setItem(6, getItem("Iron Boots", 300, new ItemStack(Material.IRON_BOOTS)));
        inv.setItem(8, getItem("Stone Sword", 150, new ItemStack(Material.STONE_SWORD)));
        return inv;
    }

    public static Inventory getBrewery(){
        Inventory inv = Bukkit.createInventory(null, 9, color("&8Brewery"));

        for(int i = 0; i<inv.getSize();i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7));

        inv.setItem(1, getItem("Golden Apple", 50, new ItemStack(Material.GOLDEN_APPLE)));
        inv.setItem(4, getItem("Golden Apple+", 200, new ItemStack(Material.GOLDEN_APPLE,1 , (short) 1)));
        inv.setItem(7, getItem("3x Arrow", 50, new ItemStack(Material.ARROW, 3)));
        return inv;
    }

    public static Inventory getBlacksmith(){
        Inventory inv = Bukkit.createInventory(null, 9, color("&8Blacksmith"));

        for(int i = 0; i<inv.getSize();i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7));

        inv.setItem(1, getItem("Protection I", 150, AnvilGUI.getEnchantedBook(Enchantment.PROTECTION_ENVIRONMENTAL, 1)));

        inv.setItem(4, getItem("Sharpness I", 400, AnvilGUI.getEnchantedBook(Enchantment.DAMAGE_ALL, 1)));

        ItemStack hoe = new ItemStack(Material.IRON_HOE);
        ItemMeta hoemeta = hoe.getItemMeta();
        hoemeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        hoe.setItemMeta(hoemeta);
        hoe.setDurability((short)230);
        inv.setItem(7, getItem("Hoe", 200, hoe));

        return inv;
    }

    private static ItemStack getItem(String name, Integer price, ItemStack itemStack){
        return new ItemstackFactory(itemStack).setDisplayName("&5" + name).addLoreLine("&7Click to buy this item for " + price + " coins.");
    }
}
