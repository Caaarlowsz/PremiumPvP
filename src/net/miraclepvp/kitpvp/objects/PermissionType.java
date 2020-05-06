package net.miraclepvp.kitpvp.objects;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public enum PermissionType {

    RENAME("Rename", new ItemstackFactory(Material.SKULL_ITEM).setDisplayName("Rename").addLoreLine("&7Players with this permission can change the guild name.")),
    CHAT("Chat", new ItemstackFactory(Material.SKULL_ITEM).setDisplayName("Chat").addLoreLine("&7Players with this permission can send messages in the guild chat.")),
    SLOW("Slow",  new ItemstackFactory(Material.SOUL_SAND).setDisplayName("Slow").addLoreLine("&7Players with this permission can toggle slow-mode in the guild chat.")),
    INVITE("Invite", new ItemstackFactory(Material.ARROW).setDisplayName("Invite").addLoreLine("&7Players with this permission can invite other players to join the guild.")),
    KICK("Kick", new ItemstackFactory(Material.REDSTONE).setDisplayName("Kick").addLoreLine("&7Players with this permission can kick other guild members.")),
    MOTD("Motd",  new ItemstackFactory(Material.NAME_TAG).setDisplayName("Motd").addLoreLine("&7Players with this permission can change the guild motd.")),
    PROMOTE("Promote", new ItemstackFactory(Material.EMERALD).setDisplayName("Promote").addLoreLine("&7Players with this permission can promote Members to Officers.")),
    DEMOTE("Demote", new ItemstackFactory(Material.COAL).setDisplayName("Demote").addLoreLine("&7Players with this permission can demote Officers to Members.")),
    DISCORD("Discord", new ItemstackFactory(Material.NAME_TAG).setDisplayName("Discord").addLoreLine("&7Players with this permission can change the guild discord link."));

    public static ArrayList<PermissionType> types = new ArrayList();

    private String name;
    private ItemStack item;

    PermissionType(String name, ItemStack item){
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem(Boolean active) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(color((active ? "&a" : "&c") + org.bukkit.ChatColor.stripColor(itemMeta.getDisplayName())));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static void load(){
        types.add(RENAME);
        types.add(CHAT);
        types.add(SLOW);
        types.add(INVITE);
        types.add(KICK);
        types.add(MOTD);
        types.add(PROMOTE);
        types.add(DEMOTE);
        types.add(DISCORD);
    }
}
