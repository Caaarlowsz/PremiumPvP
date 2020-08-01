package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.Booster;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class BoosterGUI {

    public enum FilterType {

        ALL,
        COINS,
        EXPERIENCE;

        FilterType() {
        }

        public static FilterType getNextFilter(FilterType filterType) {
            switch (filterType) {
                case ALL:
                    return COINS;
                case COINS:
                    return EXPERIENCE;
                case EXPERIENCE:
                    return ALL;
                default:
                    return ALL;
            }
        }
    }

    public static Inventory getInventory(Player player, FilterType filter) {
        //Creating the inventory
        Inventory inventory = Bukkit.createInventory(null, 9 * 5, color("&8Boosters (" + filter.toString() + ")"));

        //Getting the user of the player
        User user = Data.getUser(player);

        List<Booster.ActiveBooster> coin = new ArrayList<>();
        List<Booster.ActiveBooster> experience = new ArrayList<>();

        List<Booster.ActiveBooster> pCoin = new ArrayList<>();
        List<Booster.ActiveBooster> pExperience = new ArrayList<>();

        Booster.activeBoosters.forEach(booster -> {
            if (booster.boosterType.equals(Booster.BoosterType.COINS))
                coin.add(booster);
            if (booster.boosterType.equals(Booster.BoosterType.EXPERIENCE))
                experience.add(booster);
        });

        AtomicReference<Integer> pCoinValue = new AtomicReference<>(0);
        AtomicReference<Integer> pExperienceValue = new AtomicReference<>(0);

        Booster.activePersonalBoosters.forEach((uuid, booster) -> {
            if (uuid.equals(player.getUniqueId())) {
                if (booster.boosterType.equals(Booster.BoosterType.COINS)) {
                    pCoin.add(booster);
                    pCoinValue.set(booster.percentage);
                }
                if (booster.boosterType.equals(Booster.BoosterType.EXPERIENCE)) {
                    pExperience.add(booster);
                    pExperienceValue.set(booster.percentage);
                }
            }
        });

        List<String> activeLore = new ArrayList<>();
        //Niet personal
        activeLore.add(color("&7Network"));
        if (coin.size() > 0) {
            activeLore.add(color("&7  Coins: " + Booster.coinBoost + "%"));
            if (experience.size() > 0) {
                activeLore.add(color("&7  Coins: " + Booster.coinBoost + "%"));
                activeLore.add(color("&7  Experience: " + Booster.experienceBoost + "%"));
            }
        } else if (experience.size() > 0) {
            activeLore.add(color("&7  Experience: " + Booster.experienceBoost + "%"));
        } else {
            activeLore.add(color("&7  There are no active network boosters."));
        }

        //Wel personal
        activeLore.add(color("&7Personal"));
        if (pCoin.size() > 0) {
            activeLore.add(color("&7  Coins: " + pCoinValue + "%"));
            if (pExperience.size() > 0) {
                activeLore.add(color("&7  Coins: " + pCoinValue + "%"));
                activeLore.add(color("&7  Experience: " + pExperienceValue + "%"));
            }
        } else if (pExperience.size() > 0) {
            activeLore.add(color("&7  Experience: " + pExperienceValue + "%"));
        } else {
            activeLore.add(color("&7  There are no active personal boosters."));
        }

        //Setting the emerald
        inventory.setItem(4, new ItemstackFactory(Material.EMERALD).setDisplayName("&5Active Boosters").setLore(activeLore));

        //Setting the filter item
        inventory.setItem(43, new ItemstackFactory(Material.HOPPER).setDisplayName("&2Filter Results")
                .addLoreLine((filter.equals(FilterType.ALL) ? "&a" : "&7") + "All")
                .addLoreLine((filter.equals(FilterType.COINS) ? "&a" : "&7") + "Coins")
                .addLoreLine((filter.equals(FilterType.EXPERIENCE) ? "&a" : "&7") + "Experience"));

        //Setting the glass
        Integer[] gSlot = {0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 44};
        ItemStack gItem = new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" ");
        Arrays.stream(gSlot).forEach(position -> inventory.setItem(position, gItem));

        user.getBoosterList().forEach(bstr -> {
            Booster booster = Booster.deSerialize(bstr);
            if (filter.equals(FilterType.COINS) && !booster.boosterType.equals(Booster.BoosterType.COINS)) return;
            if (filter.equals(FilterType.EXPERIENCE) && !booster.boosterType.equals(Booster.BoosterType.EXPERIENCE))
                return;
            inventory.addItem(new ItemstackFactory(Material.PAPER).setDisplayName("&3" + booster.value + "% " + booster.boosterType.toString() + " - " + (booster.personal ? "Personal" : "Network") + " Booster").addLoreLine("&7Click to activate."));
        });
        return inventory;
    }
}
