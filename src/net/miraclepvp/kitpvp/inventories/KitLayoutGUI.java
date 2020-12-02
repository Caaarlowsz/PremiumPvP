package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.kit.ArmorSlot;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.kit.KitEffects;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class KitLayoutGUI {

    public static Inventory getPreviewInventory(Kit kit) {
        if (kit == null) return Bukkit.createInventory(null, 54, color("&8Layout Editor"));
        Inventory inv = Bukkit.createInventory(null, 54, color("&8Layout Editor - " + kit.getName()));

        //Set the glass
        Integer[] fill = {27, 28, 29, 30, 31, 32, 33, 34, 35, 47, 52, 53};
        Arrays.stream(fill).forEach(slot -> inv.setItem(slot, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" ")));

        //Save Button (45)
        inv.setItem(45, new ItemstackFactory(Material.EMERALD).setDisplayName("&aSave this layout").addLoreLine("&7Click to save this layout"));

        //Cancel Button (46)
        inv.setItem(46, new ItemstackFactory(Material.REDSTONE_BLOCK).setDisplayName("&aCancel").addLoreLine("&7Click to cancel the changes"));

        //Inventory
        for(int i = 9; i < kit.getItems().size(); i++){
            inv.setItem(i-9, getItem(kit.getItems().get(i).getItemStack()));
        }

        //Hotbar
        for(int i = 0; i < 9; i++){
            inv.setItem(36+i, getItem(kit.getItems().get(i).getItemStack()));
        }

        //Armor
        inv.setItem(48, kit.armor.get(ArmorSlot.HELMET).getItemStack());
        inv.setItem(49, kit.armor.get(ArmorSlot.CHESTPLATE).getItemStack());
        inv.setItem(50, kit.armor.get(ArmorSlot.LEGGING).getItemStack());
        inv.setItem(51, kit.armor.get(ArmorSlot.BOOTS).getItemStack());
        return inv;
    }

    private static ItemStack getItem(ItemStack item){
        if(item == null) return item;
        if(item.getType() == null || item.getType() == Material.AIR) return item;
        ItemMeta itemMeta = item.getItemMeta();
        List<String> currentLore = new ArrayList<>();
        if (itemMeta.hasLore()) currentLore = itemMeta.getLore();
        currentLore.add(color("&6Amount: " + item.getAmount()));
        itemMeta.setLore(currentLore);
        item.setItemMeta(itemMeta);
        return item;
    }
}
