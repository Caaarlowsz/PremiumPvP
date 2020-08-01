package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.inventories.PermissionsGUI;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class PermissionsListener implements Listener {

    private static Integer
            mCount = 0,
            mRow = 1,
            oCount = 4,
            oRow = 1;

    private static HashMap<Integer, PermissionType> members;
    private static HashMap<Integer, PermissionType> officers;

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player)) return;
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getName() == null) return;
        if (!(event.getClickedInventory().getName().contains("'s Permissions"))) return;
        event.setCancelled(true);

        //Resetting variables
        mCount = 0;
        mRow = 1;
        oCount = 4;
        oRow = 1;
        members = new HashMap<>();
        officers = new HashMap<>();

        //Getting member perms
        PermissionType.types.forEach(type -> {
                    Integer pos;

                    mCount++;
                    if (mCount > 3) {
                        mRow++;
                        mCount = 1;
                    }
                    pos = (9 * mRow) + mCount;
                    members.put(pos, type);
            });

        PermissionType.types.forEach(type -> {
            Integer pos;

            oCount++;
            if(oCount > 7) {
                oRow++;
                oCount = 5;
            }
            pos = (9*oRow)+oCount;
            officers.put(pos, type);
        });

        Guild guild = Data.getGuild(Data.getUser(((Player) event.getWhoClicked())).getGuild());

        if(members.containsKey(event.getSlot())){
            PermissionType perm = PermissionType.valueOf(members.get(event.getSlot()).getName().toUpperCase());
            if(guild.getMemberPerms().contains(perm)) {
                guild.getMemberPerms().remove(perm);
            } else {
                guild.getMemberPerms().add(perm);
            }
            event.getWhoClicked().openInventory(PermissionsGUI.getInventory(guild));
        }
        if(officers.containsKey(event.getSlot())){
            PermissionType perm = PermissionType.valueOf(officers.get(event.getSlot()).getName().toUpperCase());
            if(guild.getOfficerPerms().contains(perm)) {
                guild.getOfficerPerms().remove(perm);
            } else {
                guild.getOfficerPerms().add(perm);
            }
            event.getWhoClicked().openInventory(PermissionsGUI.getInventory(guild));
        }
    }
}
