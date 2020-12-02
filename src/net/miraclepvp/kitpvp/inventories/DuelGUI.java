package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Arena;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.duel.Map;
import net.miraclepvp.kitpvp.data.kit.Kit;
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

public class DuelGUI {

    public static Inventory getMainInventory(Duel duel, Player player) {
        Inventory inv = Bukkit.createInventory(null, 5 * 9, color("&8Duel - Configure the game"));

        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));

        inv.setItem(4, new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName())).setDisplayName("&5Duel").addLoreLine("&7" + Bukkit.getPlayer(duel.host).getName() + " vs " + player.getName())); //Set the skull in top center

        inv.setItem(19, new ItemstackFactory(Material.EYE_OF_ENDER).setDisplayName("&5Allow Spectators").addLoreLine("&7" + (duel.allowSpectator ? "Yes" : "No")));
        inv.setItem(21, new ItemstackFactory(Material.GOLD_NUGGET).setDisplayName("&5Bed Money").addLoreLine("&7" + duel.bid + " Coins"));
        inv.setItem(23, new ItemstackFactory(Material.IRON_CHESTPLATE).setDisplayName("&5Kit").addLoreLine("&7" + (duel.kit == null ? "None Selected" : duel.kit.getName())));
        inv.setItem(25, new ItemstackFactory(Material.GRASS).setDisplayName("&5Map").addLoreLine("&7" + (duel.map == null ? "None Selected" : Data.getMap(duel.map).name)));

        inv.setItem(39, new ItemstackFactory(Material.STAINED_CLAY, 1, 14).setDisplayName("&cCancel")); //Set the cancel button in bottom center
        inv.setItem(41, new ItemstackFactory(Material.STAINED_CLAY, 1, 5).setDisplayName("&aInvite")); //Set the invite button in bottom center
        return inv;
    }

    public static Inventory getBidInventory(Duel duel, Player player) {
        Inventory inv = Bukkit.createInventory(null, 2 * 9, color("&8Duel - Select a bid"));

        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));

        inv.setItem(4, new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName())).setDisplayName("&5Duel").addLoreLine("&7" + Bukkit.getPlayer(duel.host).getName() + " vs " + player.getName())); //Set the skull in top center

        inv.setItem(10, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName("&a+50 Coins"));
        inv.setItem(11, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName("&a+100 Coins"));
        inv.setItem(12, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName("&a+500 Coins"));
        inv.setItem(14, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName("&c-50 Coins"));
        inv.setItem(15, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName("&c-100 Coins"));
        inv.setItem(16, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName("&c-500 Coins"));
        inv.setItem(13, new ItemstackFactory(Material.GOLD_NUGGET).setDisplayName("&6" + duel.bid + " Coins").addLoreLine("&7Click to confirm"));
        return inv;
    }

    public static Inventory getKitInventory(Duel duel, Player player, Integer page) {
        Integer[] slots = {
                10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34,
                37, 38, 39, 40, 41, 42, 43
        };
        Integer invSize = 6 * 9;
        Integer pages;
        Integer first = slots.length * (page - 1) + 1;
        Integer last = (slots.length * page);
        Integer gopage = page + 1;
        Integer gobpage = page - 1;
        Inventory inv = Bukkit.createInventory(null, invSize, color("&8Duel - Select a kit"));

        for (int i = 0; i < invSize; i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));
        Arrays.stream(slots).forEach(value -> inv.setItem(value, null));
        inv.setItem(4, new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName())).setDisplayName("&5Duel").addLoreLine("&7" + Bukkit.getPlayer(duel.host).getName() + " vs " + player.getName())); //Set the skull in top center

        List<Kit> cloneList = new ArrayList<>();
        cloneList.clear();
        cloneList.addAll(Data.kits);
        int i = 1;
        for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
            Kit kit = (Kit) localIterator.next();
            if ((i >= first) && (i <= last))
                inv.addItem(new ItemstackFactory(kit.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(kit.getName()))
                        .addLoreLine("&7UUID: " + kit.getUuid())
                        .addLoreLine(" ")
                        .addLoreLine("&7" + kit.getDescription())
                        .addLoreLine(" ")
                        .addLoreLine("&7Click to select this kit")
                );
            i++;
        }

        pages = (cloneList.size() / (slots.length + 1)) + 1;
        if (pages > page)
            inv.setItem(51, new ItemstackFactory(SkullBuilder.ARROW_RIGHT.getSkull()).setDisplayName("&5Next page (" + gopage + ")").addLoreLine("&7Click here to go to the next page"));
        if (page > 1)
            inv.setItem(47, new ItemstackFactory(SkullBuilder.ARROW_LEFT.getSkull()).setDisplayName("&5Previous page (" + gobpage + ")").addLoreLine("&7Click here to go to the previous page"));
        inv.setItem(45, new ItemstackFactory(Material.PAPER).setDisplayName("&5Page:&7 " + page + "/" + (pages == 0 ? 1 : pages)));
        return inv;
    }

    public static Inventory getMapInventory(Duel duel, Player player, Integer page) {
        Integer[] slots = {10, 11, 12, 13, 14, 15, 16};
        Integer invSize = 3 * 9;
        Integer pages;
        Integer first = slots.length * (page - 1) + 1;
        Integer last = (slots.length * page);
        Integer gopage = page + 1;
        Integer gobpage = page - 1;
        Inventory inv = Bukkit.createInventory(null, invSize, color("&8Duel - Select a Map"));

        for (int i = 0; i < invSize; i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));
        Arrays.stream(slots).forEach(value -> inv.setItem(value, null));
        inv.setItem(4, new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName())).setDisplayName("&5Duel").addLoreLine("&7" + Bukkit.getPlayer(duel.host).getName() + " vs " + player.getName())); //Set the skull in top center

        List<Map> cloneList = new ArrayList<>();
        cloneList.clear();
        cloneList.addAll(Data.maps);
        int i = 1;
        for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
            Map map = (Map) localIterator.next();
            if ((i >= first) && (i <= last))
                inv.addItem(new ItemstackFactory(map.icon).setDisplayName("&5" + WordUtils.capitalizeFully(map.name))
                        .addLoreLine("&7" + map.description)
                        .addLoreLine("&7There "+(map.getAvailableArenas()==1?"is":"are")+" "+map.getAvailableArenas()+" arena"+(map.getAvailableArenas()==1?"":"'s")+" available in this map.")
                        .addLoreLine(" ")
                        .addLoreLine("&7Click to select this map")
                );
            i++;
        }

        pages = (cloneList.size() / (slots.length + 1)) + 1;
        if (pages > page)
            inv.setItem(24, new ItemstackFactory(SkullBuilder.ARROW_RIGHT.getSkull()).setDisplayName("&5Next page (" + gopage + ")").addLoreLine("&7Click here to go to the next page"));
        if (page > 1)
            inv.setItem(20, new ItemstackFactory(SkullBuilder.ARROW_LEFT.getSkull()).setDisplayName("&5Previous page (" + gobpage + ")").addLoreLine("&7Click here to go to the previous page"));
        inv.setItem(18, new ItemstackFactory(Material.PAPER).setDisplayName("&5Page:&7 " + page + "/" + (pages == 0 ? 1 : pages)));
        return inv;
    }
}
