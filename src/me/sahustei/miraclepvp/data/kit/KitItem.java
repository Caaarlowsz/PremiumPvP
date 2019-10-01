package me.sahustei.miraclepvp.data.kit;

import me.sahustei.miraclepvp.bukkit.ItemstackFactory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class KitItem {

    public int type, amount;
    public MaterialData data;
    public short durability;
    public String displayName;
    public List<String> lore;
    public boolean unbreakable;
    public Set<ItemFlag> itemFlags;
    public Map<String, Integer> enchantments = new HashMap<>();

    public KitItem(ItemStack itemStack){
        if (itemStack == null) return;
        this.amount = itemStack.getAmount();
        this.data = itemStack.getData();
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) this.lore = itemStack.getItemMeta().getLore();
        if (itemStack.hasItemMeta()) this.unbreakable = itemStack.getItemMeta().spigot().isUnbreakable();
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) this.displayName = itemStack.getItemMeta().getDisplayName().replaceAll("§", "&").replaceAll("Â", "");
        this.durability = itemStack.getDurability();
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().getItemFlags() != null && itemStack.getItemMeta().getItemFlags().size() > 0) this.itemFlags = itemStack.getItemMeta().getItemFlags();
        this.type = itemStack.getTypeId();
        if (itemStack.getEnchantments() != null && itemStack.getEnchantments().size() > 0) itemStack.getEnchantments().keySet().forEach(enchantment -> this.enchantments.put(enchantment.getName(), itemStack.getEnchantmentLevel(enchantment)));
    }

    public ItemStack getItemStack(){
        ItemstackFactory itemstackFactory = new ItemstackFactory(new ItemStack(this.type, this.amount, this.durability));
        itemstackFactory.setData(this.data);
        if (this.enchantments != null && this.enchantments.size() > 0) this.enchantments.keySet().stream().forEach(enchantment -> itemstackFactory.addEnchantment(Enchantment.getByName(enchantment), this.enchantments.get(enchantment)));
        if (this.itemFlags != null && this.itemFlags.size() > 0) this.itemFlags.stream().forEach(flag -> itemstackFactory.addItemFlags(flag));
        if (this.displayName != null) itemstackFactory.setDisplayName(color("&f" + this.displayName));
        if (itemstackFactory.getItemMeta() != null && this.unbreakable) itemstackFactory.setUnbreakable(true);
        if (this.lore != null && this.lore.size() > 0) itemstackFactory.setLore(this.lore);
        return itemstackFactory;
    }

}
