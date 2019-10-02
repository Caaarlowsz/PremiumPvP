package me.sahustei.miraclepvp.data.kit;

import me.sahustei.miraclepvp.data.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.*;

public class Kit {

    public UUID uuid;
    public String name, description, created;
    public Integer price, sellprice;
    public Material icon;
    public Boolean enabled;
    public HashMap<Integer, KitItem> items = new HashMap<>();
    public HashMap<ArmorSlot, KitItem> armor = new HashMap<>();


    public Kit(String name, Material icon, Integer price, ItemStack[] items, ItemStack[] armor){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.description = "&7The " + name + " kit.";
        this.created = formatter.format(date);
        this.price = price;
        this.sellprice = price/2;
        this.icon = icon;
        this.enabled = false;

        for(int i = 0; i < items.length; i++)
            this.items.put(i, new KitItem(items[i]));
        if (armor[3] != null) this.armor.put(ArmorSlot.HELMET, new KitItem(armor[3]));
        if (armor[2] != null) this.armor.put(ArmorSlot.CHESTPLATE, new KitItem(armor[2]));
        if (armor[1] != null) this.armor.put(ArmorSlot.LEGGING, new KitItem(armor[1]));
        if (armor[0] != null) this.armor.put(ArmorSlot.BOOTS, new KitItem(armor[0]));
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public Integer getPrice() {
        return price;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSellprice() {
        return sellprice;
    }

    public void setSellprice(Integer sellprice) {
        this.sellprice = sellprice;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public HashMap<Integer, KitItem> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, KitItem> items) {
        this.items = items;
    }

    public HashMap<ArmorSlot, KitItem> getArmor() {
        return armor;
    }

    public void setArmor(HashMap<ArmorSlot, KitItem> armor) {
        this.armor = armor;
    }

    public void giveKit(Player p) {
        Kit kit = Data.getKit(uuid);
        if (kit != null) {
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);

            ItemStack helmet = kit.armor.get(ArmorSlot.HELMET).getItemStack();
            ItemStack chestplate = kit.armor.get(ArmorSlot.CHESTPLATE).getItemStack();
            ItemStack leggings = kit.armor.get(ArmorSlot.LEGGING).getItemStack();
            ItemStack boots = kit.armor.get(ArmorSlot.BOOTS).getItemStack();

            for (int i = 0; i < kit.getItems().size(); i++) {
                p.getInventory().setItem(i, kit.getItems().get(i).getItemStack());
            }

            if(helmet != null) p.getInventory().setHelmet(helmet);
            if(chestplate != null) p.getInventory().setChestplate(chestplate);
            if(leggings != null) p.getInventory().setLeggings(leggings);
            if(boots != null) p.getInventory().setBoots(boots);
        }
    }
}
