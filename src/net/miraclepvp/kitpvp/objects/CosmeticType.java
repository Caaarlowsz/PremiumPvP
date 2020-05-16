package net.miraclepvp.kitpvp.objects;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum CosmeticType {

    SUFFIX("Suffix", 1, new ItemstackFactory(Material.SKULL_ITEM).setDisplayName("Suffix").addLoreLine("&7A nice suffix")),
    TRAIL("Trail", 2, new ItemstackFactory(Material.ARROW).setDisplayName("Trail").addLoreLine("&7A nice trail")),
    CHATCOLOR("ChatColor", 3, new ItemstackFactory(Material.REDSTONE).setDisplayName("ChatColor").addLoreLine("&7A nice chatcolor")),
    NameColor("NameColor", 4, new ItemstackFactory(Material.NAME_TAG).setDisplayName("NameColor").addLoreLine("&7A nice namecolor"));

    public static ArrayList<CosmeticType> types = new ArrayList();

    private String name;
    private Integer position;
    private ItemStack item;

    CosmeticType(String name, Integer position, ItemStack item){
        this.name = name;
        this.item = item;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem(Boolean active) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Text.color((active ? "&a" : "&c") + org.bukkit.ChatColor.stripColor(itemMeta.getDisplayName())));
        item.setItemMeta(itemMeta);
        return item;
    }

    public Integer getPosition() {
        return position;
    }

    public static void load(){
        types.add(SUFFIX);
        types.add(TRAIL);
        types.add(CHATCOLOR);
        types.add(NameColor);
    }
}
