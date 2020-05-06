package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.data.user.Abilities;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongBinaryOperator;

import static net.miraclepvp.kitpvp.Main.getRandom;
import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CrateGUI {

    private static Random random = new Random();

    private static HashMap<UUID, Integer> coins = new HashMap<>();
    private static HashMap<UUID, Integer> tokens = new HashMap<>();
    private static HashMap<UUID, Abilities.AbilityType> abilities = new HashMap<>();

    public static Inventory getInventory(Player player, Boolean common, Boolean gear){
        Inventory inv = Bukkit.createInventory(null, 3 * 9, color("&8 " + (gear ? "Gear" : "Comsetic") + " Crate - " + (common ? "Common" : "Miracle")));

        //Get list of items
        List<UUID> items = getList(gear, common);

        //Get the count of the items
        final Integer[] currentCount = {0};

        //Get a random duration time between 4 and 6 seconds.
        final Integer[] timeLeft = {getRandom(16, 22)};

        //Slots where the items will be
        Integer[] slots = {17, 16, 15, 14, 13, 12, 11, 10, 9};

        //Arrow next to the items
        ItemStack arrow = new ItemstackFactory(Material.ARROW).setDisplayName(" ");

        new BukkitRunnable() {
            @Override
            public void run() {
                //Set the time
                if (timeLeft[0] <= 0) {
                    cancel();

                    User user = Data.getUser(player);

                    try {
                        //Als er geen prijs is
                        if (inv.getItem(13).getType() == null || inv.getItem(13).getType().equals(Material.BARRIER) || inv.getItem(13).getType().equals(Material.AIR)) {
                            player.closeInventory();
                            return;
                        }

                        //Haal de UUID van het object op
                        UUID uuid = UUID.fromString(ChatColor.stripColor(inv.getItem(13).getItemMeta().getLore().get(0)));

                        //Als het een kit is
                        try {
                            Kit object = Data.getKit(uuid);
                            if (user.getKitsList().contains(uuid)) {
                                user.setCoins(user.getCoins() + object.getSellprice(), false);
                                player.sendMessage(color("&cYou already have this kit, so you received " + object.getSellprice() + " coins!"));
                            } else {
                                player.sendMessage(color("&aYou won the " + object.getName() + " kit!"));
                                user.getKitsList().add(uuid);
                            }
                        } catch (NoSuchElementException ex) {
                            //Als het een namecolor is
                            try {
                                Namecolor object = Data.getNamecolor(uuid);
                                if (user.getNamecolorsList().contains(uuid)) {
                                    user.setTokens(user.getTokens() + object.getSell());
                                    player.sendMessage(color("&cYou already have this namecolor, so you received " + object.getSell() + " tokens!"));
                                } else {
                                    player.sendMessage(color("&aYou won the " + object.getName() + " namecolor!"));
                                    user.addNameColor(uuid);
                                }
                            } catch (NoSuchElementException ex1) {
                                //Als het een chatcolor is
                                try {
                                    Chatcolor object = Data.getChatcolor(uuid);
                                    if (user.getChatcolorsList().contains(uuid)) {
                                        user.setTokens(user.getTokens() + object.getSell());
                                        player.sendMessage(color("&cYou already have this chatcolor, so you received " + object.getSell() + " tokens!"));
                                    } else {
                                        player.sendMessage(color("&aYou won the " + object.getName() + " chatcolor!"));
                                        user.addChatColor(uuid);
                                    }
                                } catch (NoSuchElementException ex2) {
                                    //Als het een suffix is
                                    try {
                                        Suffix object = Data.getSuffix(uuid);
                                        if (user.getSuffixesList().contains(uuid)) {
                                            user.setTokens(user.getTokens() + object.getSell());
                                            player.sendMessage(color("&cYou already have this suffix, so you received " + object.getSell() + " tokens!"));
                                        } else {
                                            player.sendMessage(color("&aYou won the " + object.getName() + " suffix!"));
                                            user.addSuffix(uuid);
                                        }
                                    } catch (NoSuchElementException ex3) {
                                        //Als het een trail is
                                        try {
                                            Trail object = Data.getTrail(uuid);
                                            if (user.getTrailsList().contains(uuid)) {
                                                user.setTokens(user.getTokens() + object.getSell());
                                                player.sendMessage(color("&cYou already have this trail, so you received " + object.getSell() + " tokens!"));
                                            } else {
                                                player.sendMessage(color("&aYou won the " + object.getName() + " trail!"));
                                                user.addTrail(uuid);
                                            }
                                        } catch (NoSuchElementException ex4) {
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        if(inv.getItem(13).getType().equals(Material.GOLD_NUGGET)){
                            Integer coins = Integer.parseInt(ChatColor.stripColor(inv.getItem(13).getItemMeta().getDisplayName()).replaceAll(" Coins", ""));
                            user.setCoins(user.getCoins()+coins, false);
                            player.sendMessage(color("&aYou won " + coins + " coins!"));
                        } else if(inv.getItem(13).getType().equals(Material.EMERALD)){
                            Integer tokens = Integer.parseInt(ChatColor.stripColor(inv.getItem(13).getItemMeta().getDisplayName()).replaceAll(" Tokens", ""));
                            user.setTokens(user.getTokens()+tokens);
                            player.sendMessage(color("&aYou won " + tokens + " tokens!"));
                        } else if(ChatColor.stripColor(inv.getItem(13).getItemMeta().getLore().get(0)).contains("Ability")){
                            String[] words = inv.getItem(13).getItemMeta().getLore().get(0).split(" ");
                            Abilities.AbilityType type = Abilities.AbilityType.valueOf(words[2]);
                            Integer level = 1;
                            if(user.getAbilities().containsKey(type)) {
                                level = user.getAbilities().get(type);
                                if(user.getAbilities().get(type) >= 6){
                                    player.sendMessage(color("&cYou already have this ability at the maximum level, so you received " + type.getPrice(6)/2 + " coins!"));
                                    user.setCoins(user.getCoins()+(type.getPrice(6)/2), false);
                                    player.closeInventory();
                                    return;
                                }
                                user.getAbilities().put(type, level+1);
                                player.sendMessage(color("&aYou won the " + type.toString() + " ability!"));
                                return;
                            }
                            user.getAbilities().put(type, level);
                            player.sendMessage(color("&aYou won the " + type.toString() + " ability!"));
                        }
                    }

                    ItemStack finalItem = inv.getItem(13).clone();
                    Arrays.stream(slots).forEach(slot -> inv.setItem(slot, null));
                    inv.setItem(13, finalItem);
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 0.572F);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                        }
                    }.runTaskLater(Main.getInstance(), 60);
                } else {
                    timeLeft[0]--;

                    //Play a sound
                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.216F);

                    //Set the colored glass panes.. DISCO!!!!!!
                    for (int i = 0; i < inv.getSize(); i++) {
                        Integer color = getRandom(1, 3);
                        inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, (color == 1 ? 7 : color == 2 ? 10 : 15)).setDisplayName(" "));
                    }

                    //Set the arrows
                    inv.setItem(4, arrow);
                    inv.setItem(22, arrow);

                    //Set the items

                    AtomicReference<Integer> plus = new AtomicReference<>(0);
                    Arrays.stream(slots).forEach(value -> {
                        inv.setItem(value, getItem(items, currentCount[0], plus.get()));
                        plus.getAndSet(plus.get() + 1);
                    });

                    currentCount[0]++;
                }
                player.openInventory(inv);
            }
        }.runTaskTimer(Main.getInstance(), 0, 5);

        return inv;
    }

    private static ArrayList<UUID> getList(Boolean gear, Boolean common) {
        ArrayList<UUID> items = new ArrayList<>();

        if(gear) {
            Data.kits.stream().filter(kit -> kit.getPrice()>0).forEach(kit -> items.add(kit.getUuid()));
            Abilities.AbilityType[] types = {
                    Abilities.AbilityType.ENDERPEARL,
                    Abilities.AbilityType.MORE_EXP,
                    Abilities.AbilityType.MORE_COINS,
                    Abilities.AbilityType.REGEN_SPAWN,
                    Abilities.AbilityType.ABSORTION,
                    Abilities.AbilityType.ARROW_BACK,
                    Abilities.AbilityType.STRENGTH,
                    Abilities.AbilityType.REGENERATION,
                    Abilities.AbilityType.GOLDEN_APPLE,
            };
            for(int i = 0; i<types.length; i++){
                UUID uuid = UUID.randomUUID();
                abilities.put(uuid, types[i]);
                items.add(uuid);
            }
        }else{
            Data.chatcolors.forEach(chatcolor -> items.add(chatcolor.getUuid()));
            Data.namecolors.forEach(namecolor -> items.add(namecolor.getUuid()));
            Data.suffixes.forEach(suffix -> items.add(suffix.getUuid()));
            Data.trails.forEach(trail -> items.add(trail.getUuid()));
        }

        if(common){
            Double itemSize = (double)items.size();
            Double amount = (itemSize/100)*35;
            Integer round = Math.toIntExact(Math.round(amount));

            for(int i = 1; i<round; i++){
                UUID uuid = UUID.randomUUID();
                if(gear) {
                    Integer ran = random.nextInt(90)+10;
                    coins.put(uuid, ran);
                }
                if(!gear)
                    tokens.put(uuid, random.nextInt(2)+1);
                items.add(uuid);
            }
        }

        if(items.size()<25) {
            List<UUID> dupe = new ArrayList<>();
            items.forEach(item -> dupe.add(item));
            dupe.forEach(item -> items.add(item));
        }

        Collections.shuffle(items);
        return items;
    }

    private static ItemStack getItem(List<UUID> items, Integer count, Integer plus) {
        UUID randomUUID;
        if (items.size() == 0)
            return new ItemstackFactory(Material.BARRIER).setDisplayName(color("&cNo items avaible"));
        if ((count + 7) > items.size())
            count = 0;
        randomUUID = items.get(count+plus);
        count++;
        if(coins.containsKey(randomUUID))
            return new ItemstackFactory(Material.GOLD_NUGGET).setDisplayName("&6" + coins.get(randomUUID) + " Coins");
        if(tokens.containsKey(randomUUID))
            return new ItemstackFactory(Material.EMERALD).setDisplayName("&a" + tokens.get(randomUUID) + " Tokens");
        if(abilities.containsKey(randomUUID)) {
            Abilities.AbilityType type = abilities.get(randomUUID);
            return new ItemstackFactory(type.getIcon()).setDisplayName("&5" + type.getName()).addLoreLine("&7Ability - " + type.toString());
        }
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
