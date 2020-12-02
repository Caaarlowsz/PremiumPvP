package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import java.util.Arrays;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class PermissionsGUI {

    private static Integer
            mCount = 0,
            mRow = 1,
            oCount = 4,
            oRow = 1;

    public static Inventory getInventory(Guild guild){
        mCount = 0;
        mRow = 1;
        oCount = 4;
        oRow = 1;

        Inventory inventory = Bukkit.createInventory(null, 9*5, color("&8" + guild.getName() + "'s Permissions"));

        for(int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, 7).setDisplayName(" "));
        }

        //Setting member skull
        inventory.setItem(2, new ItemstackFactory(SkullBuilder.HEROBRINE.getSkull())
                .setDisplayName("&5Members").addLoreLine(" ")
                .addLoreLine("&7Change the member permissions")
        );

        //Setting member perms
        PermissionType.types.forEach(type -> {
            mCount++;
            if(mCount > 3) {
                mRow++;
                mCount = 1;
            }
            Integer pos = (9*mRow)+mCount;
            inventory.setItem(pos, type.getItem(guild.getMemberPerms().contains(type)));
        });

        //Setting officer skull
        inventory.setItem(6, new ItemstackFactory(SkullBuilder.BLAZE.getSkull())
                .setDisplayName("&5Officers").addLoreLine(" ")
                .addLoreLine("&7Change the officers permissions")
        );

        //Setting officer perms
        PermissionType.types.forEach(type -> {
            oCount++;
            if(oCount > 7) {
                oRow++;
                oCount = 5;
            }
            Integer pos = (9*oRow)+oCount;
            inventory.setItem(pos, type.getItem(guild.getOfficerPerms().contains(type)));
        });
        return inventory;
    }
}
