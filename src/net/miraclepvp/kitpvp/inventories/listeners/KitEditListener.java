package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Editting;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.KitEditGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class KitEditListener implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = ((Player) event.getWhoClicked()).getPlayer();
        User user = Data.getUser(player);
        if (event.getInventory().getName().contains(color("&8Kit Editor"))) {
            event.setCancelled(true);
            Kit kit = Data.getKit(ChatColor.stripColor(event.getInventory().getName()).replaceAll("Kit Editor - ", ""));
            if (kit == null) return;
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || event.getCurrentItem().getType().equals(Material.AIR))
                return;
            if (event.getSlot() == 4) return;
            if (event.getSlot() == 19)
                KitEditGUI.editting.put(player, Editting.NAME);
            if (event.getSlot() == 21)
                KitEditGUI.editting.put(player, Editting.DESCRIPTION);
            if (event.getSlot() == 23)
                KitEditGUI.editting.put(player, Editting.PRICE);
            if (event.getSlot() == 25)
                KitEditGUI.editting.put(player, Editting.ICON);
            if (event.getSlot() == 29){
                if(user.giveKit(kit.getUuid(), false)) {
                } else {
                    player.sendMessage(color("&cCouldn't set your kit, something went wrong."));
                }
                return;
            }
            if (event.getSlot() == 31){
                player.openInventory(KitEditGUI.getPreviewInventory(kit, true));
                return;
            }
            if (event.getSlot() == 33){
                kit.changeInventory(player);
                player.sendMessage(color("&aYou've succesfully changed the " + kit.getName() + " kit."));
                return;
            }
            if (event.getSlot() == 36){
                kit.setEnabled(!kit.isEnabled());
                player.sendMessage(color("&aYou've succesfully " + (kit.isEnabled()?"enabled":"disabled") + " the " + kit.getName() + " kit."));
                player.openInventory(KitEditGUI.getMainInventory(kit.getName()));
                return;
            }
            if (event.getSlot() == 44){
                player.closeInventory();
                player.sendMessage(color("&aYou've succesfully deleted the " + kit.getName() + " kit."));
                Data.kits.remove(kit);
                return;
            }
            player.closeInventory();
            KitEditGUI.k_editting.put(player, kit);
            player.sendMessage(color("&aYou are editting the " + KitEditGUI.editting.get(player).getName() + " of the kit " + kit.getName() + ", type a new " + KitEditGUI.editting.get(player).getName() + " in the chat or type CANCEL to stop editting."));
        }

        if(event.getInventory().getName().contains(color("&8Armor Preview"))){
            event.setCancelled(true);
            Kit kit = Data.getKit(ChatColor.stripColor(event.getInventory().getName()).replaceAll("Armor Preview - ", ""));
            if(event.getSlot() == 0)
                player.openInventory(KitEditGUI.getMainInventory(kit.getName()));
        }
        if(event.getInventory().getName().contains(color("&8Items Preview"))){
            event.setCancelled(true);
            Kit kit = Data.getKit(ChatColor.stripColor(event.getInventory().getName()).replaceAll("Items Preview - ", ""));
            if(event.getSlot() == 49)
                player.openInventory(KitEditGUI.getMainInventory(kit.getName()));
        }
        if(event.getInventory().getName().contains(color("&8Kit Preview"))){
            Boolean admin = false;
            if(event.getInventory().getName().contains(color("&8Admin")))
                admin = true;
            event.setCancelled(true);
            Kit kit = Data.getKit(ChatColor.stripColor(event.getInventory().getName()).replaceAll("Kit Preview - ", ""));
            if(admin)
                kit = Data.getKit(ChatColor.stripColor(event.getInventory().getName()).replaceAll("Admin Kit Preview - ", ""));
            if(event.getSlot() == 45 && admin)
                player.openInventory(KitEditGUI.getMainInventory(kit.getName()));
        }
    }
}
