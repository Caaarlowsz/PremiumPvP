package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CrateGUI {

    private static Random random = new Random();
    private static HashMap<Player, Integer> count = new HashMap<>();
    public static HashMap<Player, ArrayList<UUID>> items = new HashMap<>();

    private static ArrayList<Player> players = new ArrayList<>();

    public static void getInventory(Player player) {
        players.add(player);
        count.put(player, 0);
        items.put(player, getList());
        new BukkitRunnable() {
            @Override
            public void run() {
                User user = Data.getUser(player);
                Inventory inv = player.getOpenInventory().getTopInventory();
                try {
                    if (inv.getItem(22).getType() == null || inv.getItem(22).getType().equals(Material.BARRIER) || inv.getItem(22).getType().equals(Material.AIR)) {
                        player.closeInventory();
                        players.remove(player);
                        return;
                    }
                    UUID uuid = UUID.fromString(ChatColor.stripColor(inv.getItem(22).getItemMeta().getLore().get(0)));
                    try {
                        Kit object = Data.getKit(uuid);
                        if (user.getKitsList().contains(uuid)) {
                            user.setCoins(user.getCoins() + object.getSellprice());
                            player.sendMessage(color("&cYou already have this kit, so you received " + object.getSellprice() + " coins!"));
                        } else {
                            player.sendMessage(color("&aYou won the " + object.getName() + " kit!"));
                            user.addKit(uuid);
                        }
                    } catch (NoSuchElementException ex) {
                        try {
                            Namecolor object = Data.getNamecolor(uuid);
                            if (user.getNamecolorsList().contains(uuid)) {
                                user.setCoins(user.getCoins() + object.getSell());
                                player.sendMessage(color("&cYou already have this namecolor, so you received " + object.getSell() + " coins!"));
                            } else {
                                player.sendMessage(color("&aYou won the " + object.getName() + " namecolor!"));
                                user.addNameColor(uuid);
                            }
                        } catch (NoSuchElementException ex1) {
                            try {
                                Chatcolor object = Data.getChatcolor(uuid);
                                if (user.getChatcolorsList().contains(uuid)) {
                                    user.setCoins(user.getCoins() + object.getSell());
                                    player.sendMessage(color("&cYou already have this chatcolor, so you received " + object.getSell() + " coins!"));
                                } else {
                                    player.sendMessage(color("&aYou won the " + object.getName() + " chatcolor!"));
                                    user.addChatColor(uuid);
                                }
                            } catch (NoSuchElementException ex2) {
                                try {
                                    Suffix object = Data.getSuffix(uuid);
                                    if (user.getSuffixesList().contains(uuid)) {
                                        user.setCoins(user.getCoins() + object.getSell());
                                        player.sendMessage(color("&cYou already have this suffix, so you received " + object.getSell() + " coins!"));
                                    } else {
                                        player.sendMessage(color("&aYou won the " + object.getName() + " suffix!"));
                                        user.addSuffix(uuid);
                                    }
                                } catch (NoSuchElementException ex3) {
                                    try {
                                        Trail object = Data.getTrail(uuid);
                                        if (user.getTrailsList().contains(uuid)) {
                                            user.setCoins(user.getCoins() + object.getSell());
                                            player.sendMessage(color("&cYou already have this trail, so you received " + object.getSell() + " coins!"));
                                        } else {
                                            player.sendMessage(color("&aYou won the " + object.getName() + " trail!"));
                                            user.addTrail(uuid);
                                        }
                                    } catch (NoSuchElementException ex4) {
                                        player.sendMessage(color("&4We failed to get this item."));
                                    }
                                }
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                    player.closeInventory();
                    players.remove(player);
                    return;
                }
                player.closeInventory();
                players.remove(player);
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), (random.nextInt(((10 - 5) + 1) + 5) * 20));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (players.contains(player))
                    player.openInventory(loadGUI(player));
                else
                    cancel();
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    private static Inventory loadGUI(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 3.0F, 0.533F);
        Integer invSize = 5 * 9;
        Inventory inv = Bukkit.createInventory(null, invSize, color("&8Crate"));
        Integer[] slots = {4, 13, 22, 31, 40};
        ItemStack arrow = new ItemstackFactory(Material.ARROW).setDisplayName(" ");

        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, ((random.nextInt(14)) + 1)).setDisplayName(" "));
        inv.setItem(21, arrow);
        inv.setItem(23, arrow);
        Arrays.stream(slots).forEach(value -> inv.setItem(value, getItem(player)));
        return inv;
    }

    private static ArrayList<UUID> getList() {
        ArrayList<UUID> items = new ArrayList<>();
        Data.kits.forEach(kit -> items.add(kit.getUuid()));
        Data.chatcolors.forEach(chatcolor -> items.add(chatcolor.getUuid()));
        Data.namecolors.forEach(namecolor -> items.add(namecolor.getUuid()));
        Data.suffixes.forEach(suffix -> items.add(suffix.getUuid()));
        Data.trails.forEach(trail -> items.add(trail.getUuid()));
        Collections.shuffle(items);
        return items;
    }

    private static ItemStack getItem(Player player) {
        UUID randomUUID;
        if (items.get(player).size() == 0)
            return new ItemstackFactory(Material.BARRIER).setDisplayName(color("&cNo items avaible"));
        if (count.get(player) >= items.get(player).size())
            count.put(player, 0);
        randomUUID = items.get(player).get(count.get(player));
        count.put(player, count.get(player) + 1);
        try {
            Kit object = Data.getKit(randomUUID);
            return createItemstack(object.getIcon(), object.getName(), randomUUID, object.getPrice(), object.getSellprice());
        } catch (NoSuchElementException ex) {
            try {
                Namecolor object = Data.getNamecolor(randomUUID);
                return createItemstack(object.getIcon(), object.getName(), randomUUID, object.getCost(), object.getSell());
            } catch (NoSuchElementException ex1) {
                try {
                    Chatcolor object = Data.getChatcolor(randomUUID);
                    return createItemstack(object.getIcon(), object.getName(), randomUUID, object.getCost(), object.getSell());
                } catch (NoSuchElementException ex2) {
                    try {
                        Suffix object = Data.getSuffix(randomUUID);
                        return createItemstack(object.getIcon(), object.getName(), randomUUID, object.getCost(), object.getSell());
                    } catch (NoSuchElementException ex3) {
                        try {
                            Trail object = Data.getTrail(randomUUID);
                            return createItemstack(object.getIcon(), object.getName(), randomUUID, object.getCost(), object.getSell());
                        } catch (NoSuchElementException ex4) {
                            return new ItemstackFactory(Material.BARRIER).setDisplayName(color("&cItem not found"));
                        }
                    }
                }
            }
        }
    }

    private static ItemStack createItemstack(Material material, String name, UUID uuid, Integer price, Integer sell) {
        ArrayList<String> finalLore = new ArrayList<>();
        finalLore.add(color("&7" + uuid));
        finalLore.add(color("&7"));
        finalLore.add(color("&7Price: " + price));
        finalLore.add(color("&7Sell price: " + sell));
        finalLore.add(color("&7"));
        return new ItemstackFactory(material).setDisplayName("&c" + name).setLore(finalLore);
    }
}
