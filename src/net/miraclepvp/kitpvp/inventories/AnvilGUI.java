package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import static net.miraclepvp.kitpvp.bukkit.Text.IntegerToRomanNumeral;
import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AnvilGUI {

    public static Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(null, 6 * 9, color("&8Anvil"));

        //Set black glass
        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 15).setDisplayName(" "));

        inv.setItem(29, null); //Linker item slot
        inv.setItem(33, null); //Rechter item slot

        inv.setItem(11, getUpgrade(false));
        inv.setItem(12, getUpgrade(false));
        inv.setItem(20, getUpgrade(false));

        inv.setItem(14, getSacrifice(false));
        inv.setItem(15, getSacrifice(false));
        inv.setItem(24, getSacrifice(false));

        inv.setItem(13, new ItemstackFactory(Material.BARRIER)
                .setDisplayName("&cAnvil")
                .addLoreLine("&7Place a target item in the left")
                .addLoreLine("&7slot and a sacrifice item in the")
                .addLoreLine("&7right slot to combine")
                .addLoreLine("&7Enchantments"));

        inv.setItem(22, new ItemstackFactory(Material.ANVIL)
                        .setDisplayName("&5Combine Items")
                        .addLoreLine("&7Combine the Enchantments of the")
                        .addLoreLine("&7items in the slots to the left")
                        .addLoreLine("&7and right below."));

        inv.setItem(45, getIndicator(false));
        inv.setItem(46, getIndicator(false));
        inv.setItem(47, getIndicator(false));
        inv.setItem(48, getIndicator(false));
        inv.setItem(50, getIndicator(false));
        inv.setItem(51, getIndicator(false));
        inv.setItem(52, getIndicator(false));
        inv.setItem(53, getIndicator(false));

        inv.setItem(49, new ItemstackFactory(Material.BARRIER).setDisplayName("&cClose"));

        return inv;
    }

    public static ItemStack getUpgrade(Boolean good) {
        return new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, (good ? 5 : 14))
                .setDisplayName("&6Item To Upgrade")
                .addLoreLine("&7The item you want to")
                .addLoreLine("&7upgrade should be placed in")
                .addLoreLine("&7the slot on this side.");
    }

    public static ItemStack getSacrifice(Boolean good) {
        return new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, (good ? 5 : 14))
                .setDisplayName("&6Item To Sacrifice")
                .addLoreLine("&7The item you are")
                .addLoreLine("&7sacrifice in order to")
                .addLoreLine("&7upgrade the item on the")
                .addLoreLine("&7left should be placed in")
                .addLoreLine("&7the slot on this side.");
    }

    public static ItemStack getIndicator(Boolean good) {
        return new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, (good ? 5 : 14)).setDisplayName(" ");
    }

    public static ItemStack getEnchantedBook(Enchantment enchantment, Integer level){
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta)itemStack.getItemMeta();
        enchantmentStorageMeta.addStoredEnchant(enchantment, level, true);
        itemStack.setItemMeta(enchantmentStorageMeta);
        return itemStack;
    }
}
