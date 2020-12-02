package net.miraclepvp.kitpvp.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ItemstackFactory extends ItemStack {

    public ItemstackFactory(Material material){
        super(material);
    }

    public ItemstackFactory(Material material, int amount){
        super(material, amount);
    }

    public ItemstackFactory(Material material, int amount, int Byte){
        super(material, amount, (byte) Byte);
    }

    public ItemstackFactory(ItemStack itemStack){
        super(itemStack);
    }

    public ItemstackFactory setDisplayName(String name){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(color(name));
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemstackFactory addItemFlags(ItemFlag... itemFlags){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemstackFactory removeItemFlags(ItemFlag... itemFlags){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.removeItemFlags(itemFlags);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemstackFactory setLore(List<String> lore){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
        return this;
    }

    public ItemstackFactory addLoreLine(String line){
        ItemMeta itemMeta = this.getItemMeta();
        List<String> currentLore = new ArrayList<>();
        if (itemMeta.hasLore()) currentLore = itemMeta.getLore();
        currentLore.add(replaceColor(line));
        itemMeta.setLore(currentLore);
        this.setItemMeta(itemMeta);
        return this;
    }

    private String replaceColor(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> getLore(){
        if (this.getItemMeta().getLore() == null){
            return new ArrayList<>();
        } else {
            return this.getItemMeta().getLore();
        }
    }

    private List<String> makeCleanLore(List<String> lore){
        List<String> returnList = new ArrayList<>();
        List<String> list = lore;
        for (String line : list){
            returnList.add(replaceColor(line));
        }
        return returnList;
    }

    public ItemstackFactory setUnbreakable(boolean unbreakable){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.spigot().setUnbreakable(unbreakable);
        this.setItemMeta(itemMeta);
        return this;
    }

}
