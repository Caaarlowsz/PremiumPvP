package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.data.user.StatType;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class LeaderboardGUI {

    public static Inventory getTop(StatType statType) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, color("&8Top 27 - " + statType.getName()));

        Boolean compact = false;
        if(statType.equals(StatType.KILLS) || statType.equals(StatType.DEATHS))
            compact = true;

        for(int i = 1; i<(statType.getTop().size()+1); i++){
            User target = statType.getTop().get(i-1);

            try{
                if(target == null || target.getUuid() == null || Bukkit.getOfflinePlayer(target.getUuid()) == null){
                    inv.addItem(new ItemstackFactory(
                            SkullBuilder.QUESTION.getSkull())
                            .setDisplayName("&5#" + i)
                            .addLoreLine("&7Name: " + Bukkit.getOfflinePlayer(target.getUuid()).getName())
                            .addLoreLine("&7" + target.getStat(statType, compact) + " " + statType.getName())
                    );
                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target.getUuid());
                    inv.addItem(new ItemstackFactory(
                            SkullBuilder.getPlayerSkull(offlinePlayer.getName()))
                            .setDisplayName("&5#" + i)
                            .addLoreLine("&7Name: " + offlinePlayer.getName())
                            .addLoreLine("&7" + target.getStat(statType, compact) + " " + statType.getName())
                    );
                }
            } catch(Exception ex){
                inv.addItem(new ItemstackFactory(
                        SkullBuilder.QUESTION.getSkull())
                        .setDisplayName("&5#" + i)
                        .addLoreLine("&7Name: " + Bukkit.getOfflinePlayer(target.getUuid()).getName())
                        .addLoreLine("&7" + target.getStat(statType, compact) + " " + statType.getName())
                );
            }

        }

        return inv;
    }
}
