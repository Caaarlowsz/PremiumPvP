package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.ArmorSlot;
import net.miraclepvp.kitpvp.data.kit.Editting;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.kit.KitEffects;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class KitEditGUI {

    public static HashMap<Player, Editting> editting = new HashMap<>();
    public static HashMap<Player, Kit> k_editting = new HashMap<>();

    public static Inventory getMainInventory(String name){
        Kit kit = Data.getKit(name);
        if(kit == null)
            return Bukkit.createInventory(null, 45, color("&8Kit Editor"));
        Inventory inv = Bukkit.createInventory(null, 45, color("&8Kit Editor - " + kit.getName()));
        inv.setItem(4, new ItemstackFactory(kit.getIcon()).setDisplayName("&cKit: " + kit.getName()).addLoreLine("&7Price: " + kit.getPrice()).addLoreLine("&7Description: " + kit.getDescription()));
        inv.setItem(19, new ItemstackFactory(Material.NAME_TAG).setDisplayName("&cRename").addLoreLine("&7Click here to rename this kit."));
        inv.setItem(21, new ItemstackFactory(Material.BOOK_AND_QUILL).setDisplayName("&cChange Description").addLoreLine("&7Click here to change the kit description."));
        inv.setItem(23, new ItemstackFactory(Material.GOLD_INGOT).setDisplayName("&cChange Price").addLoreLine("&7Click here to change the kit price."));
        inv.setItem(25, new ItemstackFactory(kit.getIcon()).setDisplayName("&cChange Icon").addLoreLine("&7Click here to change the kit icon."));
        inv.setItem(29, new ItemstackFactory(Material.COBBLESTONE).setDisplayName("&cGet Kit").addLoreLine("&7Click here to get this kit."));
        inv.setItem(31, new ItemstackFactory(Material.EYE_OF_ENDER).setDisplayName("&cKit Preview").addLoreLine("&7Click here to preview this kit."));
        inv.setItem(33, new ItemstackFactory(Material.IRON_CHESTPLATE).setDisplayName("&cSave Kit").addLoreLine("&7Click here to set the kit to your current inventory."));
        inv.setItem(36, new ItemstackFactory(kit.isEnabled() ? Material.EMERALD : Material.REDSTONE_BLOCK).setDisplayName(kit.isEnabled() ? "&aKit is enabled" : "&cKit is disabled").addLoreLine("&7Click here to toggle this kit."));
        inv.setItem(44, new ItemstackFactory(Material.REDSTONE).setDisplayName("&cDelete").addLoreLine("&7Click here to delete this kit"));
        return inv;
    }

    public static Inventory getArmorInventory(Kit kit){
        if(kit == null) return Bukkit.createInventory(null, 9, color("&8Armor Preview"));
        Inventory inv = Bukkit.createInventory(null, 9, color("&8Armor Preview - " + kit.getName()));
        inv.setItem(0, new ItemstackFactory(Material.ARROW).setDisplayName("&cGo Back").addLoreLine("&7Click here to go back to the GUI."));
        inv.setItem(3, kit.armor.get(ArmorSlot.HELMET).getItemStack());
        inv.setItem(4, kit.armor.get(ArmorSlot.CHESTPLATE).getItemStack());
        inv.setItem(5, kit.armor.get(ArmorSlot.LEGGING).getItemStack());
        inv.setItem(6, kit.armor.get(ArmorSlot.BOOTS).getItemStack());
        return inv;
    }

    public static Inventory getPreviewInventory(Kit kit, Boolean admin) {
        if (kit == null) return Bukkit.createInventory(null, 54, color("&8" + (admin ? "Admin " : "") + "Kit Preview"));
        Inventory inv = Bukkit.createInventory(null, 54, color("&8" + (admin ? "Admin " : "") + "Kit Preview - " + kit.getName()));
        for(int i = 9; i < kit.getItems().size(); i++){
            inv.setItem(i-9, kit.getItems().get(i).getItemStack());
        }
        inv.setItem(27, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(28, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(29, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(30, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(31, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(32, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(33, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(34, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(35, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        for(int i = 0; i < 9; i++){
            inv.setItem(36+i, kit.getItems().get(i).getItemStack());
        }
        if(admin) inv.setItem(45, new ItemstackFactory(Material.ARROW).setDisplayName("&cGo Back").addLoreLine("&7Click here to go back to the GUI."));
        else {
            ArrayList<String> lore = new ArrayList<>();
            kit.getEffects().forEach(effect -> {
                PotionEffect potion = KitEffects.deSerialize(effect);
                Integer level = potion.getAmplifier()+1;
                lore.add(color("&7" + potion.getType().getName() + "&8:&7 " + level));
            });
            if(lore.size()<1){
                lore.add(color("&7None"));
            }
            inv.setItem(45, new ItemstackFactory(Material.PAPER).setDisplayName("&5Potion Effects").setLore(lore));
        }
        inv.setItem(46, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(47, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(48, kit.armor.get(ArmorSlot.HELMET).getItemStack());
        inv.setItem(49, kit.armor.get(ArmorSlot.CHESTPLATE).getItemStack());
        inv.setItem(50, kit.armor.get(ArmorSlot.LEGGING).getItemStack());
        inv.setItem(51, kit.armor.get(ArmorSlot.BOOTS).getItemStack());
        inv.setItem(52, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        inv.setItem(53, new ItemstackFactory(Material.STAINED_GLASS_PANE).setDisplayName(" "));
        return inv;
    }
}
