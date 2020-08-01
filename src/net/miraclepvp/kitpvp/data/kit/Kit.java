package net.miraclepvp.kitpvp.data.kit;

import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

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

    public ArrayList<String> effects = new ArrayList<>();

    public Kit(String name, Material icon, Integer price, ItemStack[] items, ItemStack[] armor) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.description = "&7The " + name + " kit.";
        this.created = formatter.format(date);
        this.price = price;
        this.sellprice = price / 2;
        this.icon = icon;
        this.enabled = true;
        for (int i = 0; i < items.length; i++) {
            this.items.put(i, new KitItem(items[i]));
        }
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

    public ArrayList<String> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<String> effects) {
        this.effects = effects;
    }

    public void addEffect(PotionEffect potionEffect) {
        ArrayList<String> effect = this.getEffects();
        effect.add(KitEffects.serialize(potionEffect));
        this.setEffects(effect);
    }

    public void removeEffect(PotionEffect potionEffect) {
        ArrayList<String> effect = this.getEffects();
        effect.remove(KitEffects.serialize(potionEffect));
        this.setEffects(effect);
    }

    public void changeInventory(Player player) {
        ItemStack[] items = player.getInventory().getContents();
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (int i = 0; i < items.length; i++) {
            this.items.put(i, new KitItem(items[i]));
        }
        if (armor[3] != null) this.armor.put(ArmorSlot.HELMET, new KitItem(armor[3]));
        if (armor[2] != null) this.armor.put(ArmorSlot.CHESTPLATE, new KitItem(armor[2]));
        if (armor[1] != null) this.armor.put(ArmorSlot.LEGGING, new KitItem(armor[1]));
        if (armor[0] != null) this.armor.put(ArmorSlot.BOOTS, new KitItem(armor[0]));

        Data.users.forEach(user -> user.removeFromKitLayouts(this.uuid));
    }
}
