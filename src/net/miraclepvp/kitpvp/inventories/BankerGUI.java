package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class BankerGUI {

    public static Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(null, 9, color("&8Bank"));

        for(int i = 0; i<inv.getSize();i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7));

        inv.setItem(1, getBanknote(50));
        inv.setItem(3, getBanknote(100));
        inv.setItem(5, getBanknote(250));
        inv.setItem(7, getBanknote(500));
        return inv;
    }

    private static ItemStack getBanknote(Integer value){
        return new ItemstackFactory(Material.PAPER).setDisplayName("&5$" + value + " Banknote").addLoreLine("&7Left Click to buy").addLoreLine("&7Right Click to sell");
    }
}
