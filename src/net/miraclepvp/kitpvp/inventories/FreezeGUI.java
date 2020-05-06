package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class FreezeGUI {

    public static Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(null, 3*9, color("&8You are frozen!"));
        inv.setItem(13, new ItemstackFactory(Material.REDSTONE_BLOCK).setDisplayName("&cYou are frozen!")
                .addLoreLine("&7Join the support in our Discord.")
                .addLoreLine("&7Discord: discord.gg/TPXdxJK")
                .addLoreLine(" ")
                .addLoreLine("&7WARNING: DO NOT LEAVE THE SERVER!")
        );
        return inv;
    }

}
