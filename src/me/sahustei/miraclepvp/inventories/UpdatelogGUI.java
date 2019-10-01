package me.sahustei.miraclepvp.inventories;

import me.sahustei.miraclepvp.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class UpdatelogGUI {

    public static ItemStack getBook(){
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bm = (BookMeta)book.getItemMeta();
        bm.setAuthor("MiraclePvP");
        bm.setTitle("Update log " + Main.getInstance().version);
        ArrayList<String> pages = new ArrayList<>();
        pages.add("Using fire");
        pages.add("Using a furnace");
        book.setItemMeta(bm);
        return book;
    }
}
