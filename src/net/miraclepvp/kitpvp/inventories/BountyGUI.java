package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class BountyGUI {

    private static Integer[] slots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public static Inventory getMainInventory(Integer page) {
        Integer invSize = 9*6;
        Integer pages;
        Integer first = slots.length * (page - 1) + 1;
        Integer last = (slots.length * page);
        Integer gopage = page + 1;
        Integer gobpage = page - 1;
        Inventory inv = Bukkit.createInventory(null, invSize, color("&8Bounty Hunter"));

        //Filling the GUI with glass
        for (int i = 0; i < invSize; i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));

        //Clearing the skull slots
        Arrays.stream(slots).forEach(value -> inv.setItem(value, null));

        //Make a cloned list
        List<Player> cloneList = new ArrayList<>();
        cloneList.clear();

        //Adding the avaible players
        Bukkit.getOnlinePlayers().stream().filter(player -> Data.getUser(player).getBounty()>0).forEach(player -> cloneList.add(player));

        int i = 1;
        for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
            Player player = (Player) localIterator.next();
            if ((i >= first) && (i <= last))
                inv.addItem(new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName()))
                        .setDisplayName("&5" + player.getName())
                        .addLoreLine("&7has a bounty of " + Data.getUser(player).getBounty() + " on his head!"));
            i++;
        }

        pages = (cloneList.size() / (slots.length + 1)) + 1;
        if (pages > page)
            inv.setItem(51, new ItemstackFactory(SkullBuilder.ARROW_RIGHT.getSkull()).setDisplayName("&5Next page (" + gopage + ")").addLoreLine("&7Click here to go to the next page"));
        if (page > 1)
            inv.setItem(48, new ItemstackFactory(SkullBuilder.ARROW_LEFT.getSkull()).setDisplayName("&5Previous page (" + gobpage + ")").addLoreLine("&7Click here to go to the previous page"));
        inv.setItem(45, new ItemstackFactory(Material.PAPER).setDisplayName("&5Page:&7 " + page + "/" + (pages == 0 ? 1 : pages)));
        inv.setItem(49, new ItemstackFactory(Material.EMERALD).setDisplayName("&aSet a bounty").addLoreLine("&7Click to set a bounty on a player"));
        return inv;
    }

    public static Inventory getPlayersInventory(Player player, Integer page) {
        Integer invSize = 9*6;
        Integer pages;
        Integer first = slots.length * (page - 1) + 1;
        Integer last = (slots.length * page);
        Integer gopage = page + 1;
        Integer gobpage = page - 1;
        Inventory inv = Bukkit.createInventory(null, invSize, color("&8Bounty Hunter - Players"));

        //Filling the GUI with glass
        for (int i = 0; i < invSize; i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));

        //Clearing the skull slots
        Arrays.stream(slots).forEach(value -> inv.setItem(value, null));

        //Make a cloned list
        List<Player> cloneList = new ArrayList<>();
        cloneList.clear();

        //Adding the avaible players
        Bukkit.getOnlinePlayers().stream().filter(players -> checkAvaible(player, players)).forEach(players -> cloneList.add(players));

        //Adding the skulls
        int i = 1;
        for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
            Player target = (Player) localIterator.next();
            if ((i >= first) && (i <= last))
                inv.addItem(new ItemstackFactory(SkullBuilder.getPlayerSkull(target.getName()))
                        .setDisplayName("&5" + target.getName())
                        .addLoreLine("&7Click to set as bounty on this player!"));
            i++;
        }

        pages = (cloneList.size() / (slots.length + 1)) + 1;
        if (pages > page)
            inv.setItem(51, new ItemstackFactory(SkullBuilder.ARROW_RIGHT.getSkull()).setDisplayName("&5Next page (" + gopage + ")").addLoreLine("&7Click here to go to the next page"));
        if (page > 1)
            inv.setItem(48, new ItemstackFactory(SkullBuilder.ARROW_LEFT.getSkull()).setDisplayName("&5Previous page (" + gobpage + ")").addLoreLine("&7Click here to go to the previous page"));
        inv.setItem(45, new ItemstackFactory(Material.PAPER).setDisplayName("&5Page:&7 " + page + "/" + (pages == 0 ? 1 : pages)));
        return inv;
    }

    public static Inventory getSetBountyInventory(Player player, Integer value) {
        Inventory inv = Bukkit.createInventory(null, 9 * 5, color("&8Bounty Hunter - " + player.getName()));
        Integer[] sides = {0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
        Arrays.stream(sides).forEach(integer -> inv.setItem(integer, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" ")));
        inv.setItem(4, new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName())).setDisplayName("&5" + player.getName()));
        inv.setItem(12, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName("&a+50 Coins"));
        inv.setItem(13, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName("&a+100 Coins"));
        inv.setItem(14, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName("&a+500 Coins"));
        inv.setItem(30, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName("&c-50 Coins"));
        inv.setItem(31, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName("&c-100 Coins"));
        inv.setItem(32, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName("&c-500 Coins"));
        inv.setItem(22, new ItemstackFactory(Material.GOLD_NUGGET).setDisplayName("&6" + value + " Coins").addLoreLine("&7Costs: " + Integer.valueOf(((value / 100) * 10) + value)));
        return inv;
    }

    private static Boolean checkAvaible(Player clicker, Player player) {
        if (clicker.equals(player)) return false;
        if (player.hasMetadata("vanished") || player.hasMetadata("build")) return false;
        User user = Data.getUser(player);
        if (user.getBounty() > 0) return false;
        return true;
    }
}
