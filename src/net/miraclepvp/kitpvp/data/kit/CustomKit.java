package net.miraclepvp.kitpvp.data.kit;

import java.util.HashMap;
import java.util.UUID;

public class CustomKit {

    private UUID uuid;
    public HashMap<Integer, KitItem> items;
    public HashMap<ArmorSlot, KitItem> armor;

    public CustomKit(UUID uuid, HashMap<Integer, KitItem> items, HashMap<ArmorSlot, KitItem> armor) {
        this.uuid = uuid;
        this.items = items;
        this.armor = armor;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
