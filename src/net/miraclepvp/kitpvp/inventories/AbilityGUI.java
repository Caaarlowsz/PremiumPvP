package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.Abilities;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AbilityGUI {

    public static Inventory getMainInventory(){
        Inventory inv = Bukkit.createInventory(null, 4*9, color("&8Abilities"));

        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));

        Integer[] slots = {10,11,12,13,14,15,16,19,20,21,22,23,24,25};
        Arrays.stream(slots).forEach(value -> inv.setItem(value, null));

        Abilities.AbilityType[] types = {
                Abilities.AbilityType.GOLDEN_APPLE,
                Abilities.AbilityType.STRENGTH,
                Abilities.AbilityType.ARROW_BACK,
                Abilities.AbilityType.ABSORTION,
                Abilities.AbilityType.REGENERATION,
                Abilities.AbilityType.MORE_COINS,
                Abilities.AbilityType.MORE_EXP,
                Abilities.AbilityType.ENDERPEARL,
                Abilities.AbilityType.REGEN_SPAWN
        };

        Arrays.stream(types).forEach(type -> inv.addItem(new ItemstackFactory(type.getIcon()).setDisplayName("&5" + type.getName()).addLoreLine("&7" + type.toString() + " - " + type.getDescription())));

        return inv;
    }

    public static Inventory getTypeInventory(Player player, Abilities.AbilityType type){
        Inventory inv = Bukkit.createInventory(null, 3*9, color("&8Abilities - Upgrade"));

        for (int i = 0; i < inv.getSize(); i++)
            inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));

        inv.setItem(26, new ItemstackFactory(Material.ARROW).setDisplayName("&5Go Back").addLoreLine("&7Click to go back to the main inventory."));

        User user = Data.getUser(player);

        Integer level = 0;

        if(user.getAbilities().size()>0) {
            if (user.getAbilities().get(type) != null) {
                level = user.getAbilities().get(type);
            }
        }

        Integer[] slots = {10,11,12,13,14,15,16};
        Arrays.stream(slots).forEach(value -> inv.setItem(value, null));

        inv.addItem(new ItemstackFactory(type.getIcon()).setDisplayName("&5" + type.getName()).addLoreLine("&7" + type.toString() + " - " + type.getDescription()));

        for(int i = 0; i<6; i++)
            inv.setItem(11+i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, (level > i ? 5 : 14)).setDisplayName(level > i ? "&aUnlocked" : "&cLocked").addLoreLine("&7Get " + type.getLvl(i+1) + " " + type.getAction()));

        if(level < 6)
            inv.setItem(11+level, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 4).setDisplayName("&eClick to Buy &8- &7" + type.getPrice(level+1) + " Coins").addLoreLine("&7Get " + type.getLvl(level+1) + " " + type.getAction()));
        return inv;
    }
}
