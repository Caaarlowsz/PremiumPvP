package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.CosmeticType;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CosmeticsGUI {

    public static Integer[] slots = {12, 13, 14, 15, 21, 22, 23, 24, 30, 31, 32, 33, 39, 40, 41, 42};

    public static Inventory getInventory(Player player, CosmeticType cosmeticType, Boolean shop, Integer page) {
        Integer invSize = 9 * 6;
        Integer pages = 0;
        Integer listsize = 0;
        Integer first = slots.length * (page - 1)+1;
        Integer last = (slots.length * page);
        Integer gopage = page + 1;
        Integer gobpage = page - 1;
        Inventory inv = Bukkit.createInventory(null, invSize, Text.color("&8" + (shop ? "Shop" : "Selector") + " - Cosmetic: " + cosmeticType.getName()));
        User user = Data.getUser(player);
        user.setCosmeticWasShop(shop);
        user.setLastCosmeticType(cosmeticType);
        for (int i = 0; i < invSize; i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));
        ItemStack selected = new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName(" ");
        ItemStack notSelected = new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName(" ");
        CosmeticType.types.forEach(type -> {
            inv.setItem(getSlot(type.getPosition()), type.getItem((type == cosmeticType) ? true : false));
            inv.setItem(getSlot(type.getPosition())-1, notSelected);
            if (type.equals(cosmeticType))
                inv.setItem(getSlot(type.getPosition())-1, selected);
        });
        Arrays.stream(slots).forEach(value -> inv.setItem(value, null));

        //          Trails
        if (cosmeticType.equals(CosmeticType.TRAIL)) {
            if (shop) {
                List<Trail> cloneList = new ArrayList<>();
                cloneList.clear();
                Data.trails.forEach(trail -> {
                    if (!user.getTrailsList().contains(trail.getUuid()))
                        cloneList.add(trail);
                });
                listsize = cloneList.size();
                int i = 1;
                for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
                    Trail trail = (Trail) localIterator.next();
                    if ((i >= first) && (i <= last))
                        inv.addItem(new ItemstackFactory(trail.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(trail.getName()))
                                .addLoreLine(" ")
                                .addLoreLine("&7Name: " + trail.getName())
                                .addLoreLine("&7Price: " + trail.getCost())
                                .addLoreLine("&7Sell price: " + trail.getSell())
                                .addLoreLine(" "));
                    i++;
                }
            } else {
                List<Trail> cloneList = new ArrayList<>();
                cloneList.clear();
                Data.trails.forEach(trail -> {
                    if (user.getTrailsList().contains(trail.getUuid()))
                        cloneList.add(trail);
                });
                listsize = cloneList.size();
                int i = 1;
                for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
                    Trail trail = (Trail) localIterator.next();
                    if ((i >= first) && (i <= last))
                        inv.addItem(new ItemstackFactory(trail.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(trail.getName()))
                                .addLoreLine(" ")
                                .addLoreLine("&7Left Click to select this trail.")
                                .addLoreLine("&7Right Click to sell this trail.")
                                .addLoreLine(" ")
                                .addLoreLine("&7Sell price: " + trail.getSell())
                                .addLoreLine(" "));
                    i++;
                }
            }
        }

        //          Suffixes
        if(cosmeticType.equals(CosmeticType.SUFFIX)){
            if (shop) {
                List<Suffix> cloneList = new ArrayList<>();
                cloneList.clear();
                Data.suffixes.forEach(suffix -> {
                    if (!user.getSuffixesList().contains(suffix.getUuid()) && suffix.getBuyable())
                        cloneList.add(suffix);
                });
                listsize = cloneList.size();
                int i = 1;
                for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
                    Suffix suffix = (Suffix) localIterator.next();
                    if ((i >= first) && (i <= last))
                        inv.addItem(new ItemstackFactory(suffix.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(suffix.getName()))
                                .addLoreLine(" ")
                                .addLoreLine("&7Suffix: " + suffix.getSuffix())
                                .addLoreLine("&7Price: " + suffix.getCost())
                                .addLoreLine("&7Sell price: " + suffix.getSell())
                                .addLoreLine(" "));
                    i++;
                }
            } else {
                List<Suffix> cloneTrails = new ArrayList<>();
                cloneTrails.clear();
                Data.suffixes.forEach(suffix -> {
                    if (user.getSuffixesList().contains(suffix.getUuid()))
                        cloneTrails.add(suffix);
                });
                listsize = cloneTrails.size();
                int i = 1;
                for (Iterator localIterator = cloneTrails.iterator(); localIterator.hasNext(); ) {
                    Suffix suffix = (Suffix) localIterator.next();
                    if ((i >= first) && (i <= last))
                        inv.addItem(new ItemstackFactory(suffix.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(suffix.getName()))
                                .addLoreLine(" ")
                                .addLoreLine("&7Left Click to select this suffix.")
                                .addLoreLine("&7Right Click to sell this suffix.")
                                .addLoreLine(" ")
                                .addLoreLine("&7Sell price: " + suffix.getSell())
                                .addLoreLine(" "));
                    i++;
                }
            }
        }

        //          ChatColors
        if(cosmeticType.equals(CosmeticType.CHATCOLOR)){
            if (shop) {
                List<Chatcolor> cloneList = new ArrayList<>();
                cloneList.clear();
                Data.chatcolors.forEach(color -> {
                    if (!user.getChatcolorsList().contains(color.getUuid()))
                        cloneList.add(color);
                });
                listsize = cloneList.size();
                int i = 1;
                for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
                    Chatcolor chatcolor = (Chatcolor) localIterator.next();
                    if ((i >= first) && (i <= last))
                        inv.addItem(new ItemstackFactory(chatcolor.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(chatcolor.getName()))
                                .addLoreLine(" ")
                                .addLoreLine("&7Name: " + chatcolor.getName())
                                .addLoreLine("&7Price: " + chatcolor.getCost())
                                .addLoreLine("&7Sell price: " + chatcolor.getSell())
                                .addLoreLine(" "));
                    i++;
                }
            } else {
                List<Chatcolor> cloneTrails = new ArrayList<>();
                cloneTrails.clear();
                Data.chatcolors.forEach(color -> {
                    if (user.getChatcolorsList().contains(color.getUuid()))
                        cloneTrails.add(color);
                });
                listsize = cloneTrails.size();
                int i = 1;
                for (Iterator localIterator = cloneTrails.iterator(); localIterator.hasNext(); ) {
                    Chatcolor chatcolor = (Chatcolor) localIterator.next();
                    if ((i >= first) && (i <= last))
                        inv.addItem(new ItemstackFactory(chatcolor.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(chatcolor.getName()))
                                .addLoreLine(" ")
                                .addLoreLine("&7Left Click to select this chatcolor.")
                                .addLoreLine("&7Right Click to sell this chatcolor.")
                                .addLoreLine(" ")
                                .addLoreLine("&7Sell price: " + chatcolor.getSell())
                                .addLoreLine(" "));
                    i++;
                }
            }
        }

            //          NameColors
        if(cosmeticType.equals(CosmeticType.NameColor)){
            if (shop) {
                List<Namecolor> cloneList = new ArrayList<>();
                cloneList.clear();
                Data.namecolors.forEach(color -> {
                    if (!user.getNamecolorsList().contains(color.getUuid()))
                        cloneList.add(color);
                });
                listsize = cloneList.size();
                int i = 1;
                for (Iterator localIterator = cloneList.iterator(); localIterator.hasNext(); ) {
                    Namecolor namecolor = (Namecolor) localIterator.next();
                    if ((i >= first) && (i <= last))
                        inv.addItem(new ItemstackFactory(namecolor.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(namecolor.getName()))
                                .addLoreLine(" ")
                                .addLoreLine("&7Name: " + namecolor.getName())
                                .addLoreLine("&7Price: " + namecolor.getCost())
                                .addLoreLine("&7Sell price: " + namecolor.getSell())
                                .addLoreLine(" "));
                    i++;
                }
            } else {
                List<Namecolor> cloneTrails = new ArrayList<>();
                cloneTrails.clear();
                Data.namecolors.forEach(color -> {
                    if (user.getNamecolorsList().contains(color.getUuid()))
                        cloneTrails.add(color);
                });
                listsize = cloneTrails.size();
                int i = 1;
                for (Iterator localIterator = cloneTrails.iterator(); localIterator.hasNext(); ) {
                    Namecolor namecolor = (Namecolor) localIterator.next();
                    if ((i >= first) && (i <= last))
                        inv.addItem(new ItemstackFactory(namecolor.getIcon()).setDisplayName("&5" + WordUtils.capitalizeFully(namecolor.getName()))
                                .addLoreLine(" ")
                                .addLoreLine("&7Left Click to select this namecolor.")
                                .addLoreLine("&7Right Click to sell this namecolor.")
                                .addLoreLine(" ")
                                .addLoreLine("&7Sell price: " + namecolor.getSell())
                                .addLoreLine(" "));
                    i++;
                }
            }
        }
        if(!shop)
            inv.setItem(53, new ItemstackFactory(Material.BARRIER).setDisplayName("&5Disable current").addLoreLine("&7Click here to disable your current cosmetic"));
        pages = (listsize / (slots.length + 1)) + 1;
        if (pages > page)
            inv.setItem(51, new ItemstackFactory(SkullBuilder.ARROW_RIGHT.getSkull()).setDisplayName("&5Next page (" + gopage + ")").addLoreLine("&7Click here to go to the next page"));
        if (page > 1)
            inv.setItem(48, new ItemstackFactory(SkullBuilder.ARROW_LEFT.getSkull()).setDisplayName("&5Previous page (" + gobpage + ")").addLoreLine("&7Click here to go to the previous page"));
        inv.setItem(17, new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName())).setDisplayName("&5Tokens").addLoreLine("&7You have " + user.getTokens() + " tokens."));
        inv.setItem(35, new ItemstackFactory(shop ? Material.COMPASS : Material.EMERALD).setDisplayName(shop ? "&5Selector" : "&5Shop").addLoreLine("&7Click here to go to the " + cosmeticType.getName() + (shop ? " shop." : " selector.")));
        inv.setItem(45, new ItemstackFactory(Material.PAPER).setDisplayName("&5Page:&7 " + page + "/" + (pages == 0 ? 1 : pages)));
        return inv;
    }

    public static Integer getSlot(Integer position){
        return (position*9)+1;
    }

    public static Inventory getConfirmation(Boolean sell, CosmeticType type, UUID uuid){
        Inventory inv = Bukkit.createInventory(null, 1*9, color("&8Cosmetic " + (sell ? "Sell" : "Buy") + " Confirmation"));

        inv.setItem(2, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 14).setDisplayName("&cCancel"));

        String
                name = "",
                sellPrice = "",
                buyPrice = "";

        try {
            switch (type) {
                case TRAIL:
                    name = Data.getTrail(uuid).getName();
                    sellPrice = String.valueOf(Data.getTrail(uuid).getSell());
                    buyPrice = String.valueOf(Data.getTrail(uuid).getCost());
                    break;
                case SUFFIX:
                    name = Data.getSuffix(uuid).getName();
                    sellPrice = String.valueOf(Data.getSuffix(uuid).getSell());
                    buyPrice = String.valueOf(Data.getSuffix(uuid).getCost());
                    break;
                case CHATCOLOR:
                    name = Data.getChatcolor(uuid).getName();
                    sellPrice = String.valueOf(Data.getChatcolor(uuid).getSell());
                    buyPrice = String.valueOf(Data.getChatcolor(uuid).getCost());
                    break;
                case NameColor:
                    name = Data.getNamecolor(uuid).getName();
                    sellPrice = String.valueOf(Data.getNamecolor(uuid).getSell());
                    buyPrice = String.valueOf(Data.getNamecolor(uuid).getCost());
                    break;
            }
        }catch (Exception ex){
            name = "NaN";
            sellPrice = "NaN";
            buyPrice = "NaN";
        }

        //Information item
        inv.setItem(4, new ItemstackFactory(Material.PAPER)
                .setDisplayName("&5Are you sure?")
                .addLoreLine("&7You are on the edge of")
                .addLoreLine("&7" + (sell ? "selling your" : "buying the") + " " + name)
                .addLoreLine("&7" + type.toString().toLowerCase() + " for " + (sell ? sellPrice : buyPrice) + " cosmotokens")
                .addLoreLine(" ")
                .addLoreLine("&7Click left to cancel")
                .addLoreLine("&7Click right to accept")
        );

        inv.setItem(6, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 5).setDisplayName("&aAccept"));

        return inv;
    }
}
