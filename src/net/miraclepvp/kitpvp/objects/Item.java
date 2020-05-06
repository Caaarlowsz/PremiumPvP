package net.miraclepvp.kitpvp.objects;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public enum Item {

    KITS(1, new ItemstackFactory(Material.COMPASS).setDisplayName("&5Kits").addLoreLine("&7Click on this item to manage your kits.")),
    ABILITIES(3, new ItemstackFactory(Material.EYE_OF_ENDER).setDisplayName("&5Abilities").addLoreLine("&7Click on this item to manage your abilities.")),
    STATS(5, new ItemstackFactory(SkullBuilder.getCustomSkull("http://textures.minecraft.net/texture/373eef035658bf1b43f72a6ace3ab047265d863e0fe75586f84c02a7133e0cdf")).setDisplayName("&5Profile").addLoreLine("&7Click on this item to view your profile.")),
    COSMETICS(7, new ItemstackFactory(Material.BLAZE_POWDER).setDisplayName("&5Cosmetics").addLoreLine("&7Click on this item to manage your cosmetics."));

    public static ArrayList<Item> types = new ArrayList();

    private Integer position;
    private ItemStack item;

    Item(Integer position, ItemStack item){
        this.item = item;
        this.position = position;
    }

    public ItemStack getItem() {
        return item;
    }

    public Integer getPosition() {
        return position;
    }

    public static void load(){
        types.add(KITS);
        types.add(ABILITIES);
        types.add(STATS);
        types.add(COSMETICS);
    }
}
