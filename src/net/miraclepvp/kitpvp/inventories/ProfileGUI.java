package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static net.miraclepvp.kitpvp.bukkit.Text.IntegerToCompactInteger;
import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ProfileGUI {

    public static Inventory getInventory(Player player){
        //Creating the inventory
        Inventory inventory = Bukkit.createInventory(null, 9*5, color("&8" + player.getName() + "'s Profile"));

        //Getting the user of the player
        User user = Data.getUser(player);

        //Setting the skull
        inventory.setItem(4, new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName()))
                .setDisplayName("&5" + player.getName() + "'s PvP Profile")
                .addLoreLine("&7First Join: " + user.getFirstJoin() + " GMT")
                .addLoreLine("&7Online Time: " + User.transformTime(user.getOnlineTime()))
        );

        //Setting the glass
        Integer[] gSlot = {0,1,2,3,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44};
        ItemStack gItem = new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" ");
        Arrays.stream(gSlot).forEach(position -> inventory.setItem(position, gItem));

        //Setting the level item
        inventory.setItem(20, new ItemstackFactory(Material.EXP_BOTTLE)
                .setDisplayName("&5Level").addLoreLine(" ")
                .addLoreLine("&7Level: " + user.getLevel())
                .addLoreLine("&7Progress: " + IntegerToCompactInteger(user.getExperience(),1, false) + "&8/&7" + IntegerToCompactInteger(user.getExperienceNeeded(),1, false))
                .addLoreLine("&7 " + user.getProgressBar())
        );

        //Setting the kills & deaths item
        inventory.setItem(21, new ItemstackFactory(Material.IRON_SWORD)
                .setDisplayName("&5Kill Death Ratio").addLoreLine(" ")
                .addLoreLine("&7Kills: " + IntegerToCompactInteger(user.getKills(),1, false))
                .addLoreLine("&7Assists: " + IntegerToCompactInteger(user.getAssists(),1, false))
                .addLoreLine("&7Deaths: " + IntegerToCompactInteger(user.getDeaths(),1, false))
                .addLoreLine("&7K/D Ratio: " + user.getKdRatio())
        );

        //Setting the streak item
        inventory.setItem(22, new ItemstackFactory(Material.GLASS, 1,2)
                .setDisplayName("&5Streak").addLoreLine(" ")
                .addLoreLine("&7Current Killstreak: " + user.getKillstreak())
                .addLoreLine("&7Highest Killstreak: " + user.getBestkillstreak())
        );

        //Setting the economic item
        inventory.setItem(23, new ItemstackFactory(Material.GOLD_NUGGET)
                .setDisplayName("&5Economy").addLoreLine(" ")
                .addLoreLine("&7Balance: " + IntegerToCompactInteger(user.getCoins(),1, false))
                .addLoreLine("&7Active Bounty: " + user.getBounty())
                .addLoreLine(" ")
                .addLoreLine("&7Owned Kits: " + user.getKitsList().size())
        );

        inventory.setItem(24, new ItemstackFactory(Material.BLAZE_POWDER)
                .setDisplayName("&5Cosmetics").addLoreLine(" ")
                .addLoreLine("&7Tokens: " + user.getTokens())
                .addLoreLine(" ")
                .addLoreLine("&7Owned Cosmetics: " + (user.getTrailsList().size() + user.getChatcolorsList().size() + user.getNamecolorsList().size() + user.getSuffixesList().size()))
        );

        inventory.setItem(40, new ItemstackFactory(Material.CHEST)
                .setDisplayName("&5Crates").addLoreLine(" ")
                .addLoreLine("&7Gear Crates:")
                .addLoreLine("&7 Common: " + user.getGearcommon())
                .addLoreLine("&7 Miracle: " + user.getGearmiracle())
                .addLoreLine("&7Cosmetic Crates:")
                .addLoreLine("&7 Common: " + user.getCosmeticcommon())
                .addLoreLine("&7 Miracle: " + user.getCosmeticmiracle())
        );
        return inventory;
    }
}
