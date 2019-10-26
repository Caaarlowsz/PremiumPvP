package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class KitGUI implements Listener {

    public static Integer[] slots = {12, 13, 14, 15, 21, 22, 23, 24, 30, 31, 32, 33, 39, 40, 41, 42};

    public static Inventory getInventory(Player player, Boolean shop, Integer page) {
        Integer invSize = 9 * 6;
        Integer pages, listsize;
        Integer first = slots.length * (page - 1)+1;
        Integer last = (slots.length * page);
        Integer gopage = page + 1;
        Integer gobpage = page - 1;
        Inventory inv = Bukkit.createInventory(null, invSize, Text.color("&8" + (shop ? "Shop" : "Selector") + " - Kit"));
        User user = Data.getUser(player);
        for (int i = 0; i < invSize; i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));
        ItemStack selected = new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName(" ");
        ItemStack notSelected = new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName(" ");
        inv.setItem(9, user.isAutoDeploy() ? selected : notSelected);
        inv.setItem(10, new ItemstackFactory(Material.GOLDEN_APPLE).setDisplayName("&5Auto Deployment").addLoreLine("&7Automatic Deployment spawns you in the game directly after selecting a kit."));
        inv.setItem(18, user.isQuickSelect() ? selected : notSelected);
        inv.setItem(19, new ItemstackFactory(Material.COMPASS).setDisplayName("&5Quick Select").addLoreLine("&7When you have Quick Select on and left click with the").addLoreLine("&7selector in your hand, you will select your last selected kit."));
        inv.setItem(27, user.isKillBroadcast() ? selected : notSelected);
        inv.setItem(28, new ItemstackFactory(Material.BONE).setDisplayName("&5Kill Broadcast").addLoreLine("&7This feature will broadcast your kills in the chat."));
        inv.setItem(36, user.isStreakBroadcast() ? selected : notSelected);
        inv.setItem(37, new ItemstackFactory(Material.BLAZE_ROD).setDisplayName("&5Streak Broadcast").addLoreLine("&7This feature will broadcast your killstreaks in chat."));
        inv.setItem(53, new ItemstackFactory(Material.CHEST).setDisplayName("&5Editor").addLoreLine("&7Click here to edit a kit layout"));

        Arrays.stream(slots).forEach(value -> inv.setItem(value, null));

        if (shop) {
            List<Kit> cloneList = new ArrayList<>();
            cloneList.clear();
            Data.kits.forEach(kit -> {
                if (!user.getKitsList().contains(kit.getUuid()) && kit.getPrice() > 0)
                    cloneList.add(kit);
            });
            listsize = cloneList.size();
            int i = 1;
            for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
                Kit kit = (Kit) localIterator.next();
                if ((i >= first) && (i <= last))
                    inv.addItem(new ItemstackFactory(kit.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(kit.getName()))
                            .addLoreLine("&7" + kit.getDescription())
                            .addLoreLine(" ")
                            .addLoreLine("&7UUID: " + kit.getUuid())
                            .addLoreLine(" ")
                            .addLoreLine("&7Name: " + kit.getName())
                            .addLoreLine("&7Price: " + kit.getPrice())
                            .addLoreLine("&7Sell price: " + kit.getSellprice())
                            .addLoreLine(" "));
                i++;
            }
        } else {
            List<Kit> cloneList = new ArrayList<>();
            cloneList.clear();
            Data.kits.forEach(kit -> {
                if (user.getKitsList().contains(kit.getUuid()) || kit.getPrice() == 0)
                    cloneList.add(kit);
            });
            listsize = cloneList.size();
            int i = 1;
            for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
                Kit kit = (Kit) localIterator.next();
                if ((i >= first) && (i <= last))
                    inv.addItem(new ItemstackFactory(kit.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(kit.getName()))
                            .addLoreLine("&7" + kit.getDescription())
                            .addLoreLine(" ")
                            .addLoreLine("&7UUID: " + kit.getUuid())
                            .addLoreLine(" ")
                            .addLoreLine("&7Left Click to select this kit.")
                            .addLoreLine("&7Right Click to sell this kit.")
                            .addLoreLine(kit.isEnabled() ? "" : "&cTEMPORARY DISABLED")
                            .addLoreLine(" "));
                i++;
            }
        }

        pages = (listsize / (slots.length + 1)) + 1;
        if (pages > page)
            inv.setItem(51, new ItemstackFactory(SkullBuilder.ARROW_RIGHT.getSkull()).setDisplayName("&5Next page (" + gopage + ")").addLoreLine("&7Click here to go to the next page"));
        if (page > 1)
            inv.setItem(48, new ItemstackFactory(SkullBuilder.ARROW_LEFT.getSkull()).setDisplayName("&5Previous page (" + gobpage + ")").addLoreLine("&7Click here to go to the previous page"));
        inv.setItem(17, new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName())).setDisplayName("&5Coins").addLoreLine("&7You have " + user.getCoins() + " coins."));
        inv.setItem(35, new ItemstackFactory(shop ? Material.COMPASS : Material.EMERALD).setDisplayName(shop ? "&5Selector" : "&5Shop").addLoreLine("&7Click here to go to the kit" + (shop ? " shop." : " selector.")));
        inv.setItem(45, new ItemstackFactory(Material.PAPER).setDisplayName("&5Page:&7 " + page + "/" + (pages == 0 ? 1 : pages)));
        return inv;
    }
}
