package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.CosmeticsGUI;
import net.miraclepvp.kitpvp.inventories.KitGUI;
import net.miraclepvp.kitpvp.inventories.CrateGUI;
import net.miraclepvp.kitpvp.inventories.ProfileGUI;
import net.miraclepvp.kitpvp.objects.CosmeticType;
import net.miraclepvp.kitpvp.objects.hasKit;
import net.miraclepvp.kitpvp.utils.TeleportUtil;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerInventory implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getPlayer().hasMetadata("kit")) return;
        if(event.getPlayer().hasMetadata("build")) return;
        Player player = event.getPlayer();
        User user = Data.getUser(player);
        if(user == null) return;
        if(event.getItem() == null || event.getItem().getType() == null) return;
        switch(event.getItem().getType()){
            case COMPASS:
                if(user.isQuickSelect() && (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))){
                    if (user.getPreviousKit() == null || (Data.getKit(user.getPreviousKit()).getPrice() > 0 && !(user.getKitsList().contains(user.getPreviousKit())))) {
                        player.sendMessage(color("&cWe failed to get your previous kit, please select a kit."));
                        break;
                    }
                    Kit kit = Data.getKit(user.getPreviousKit());
                    player.sendMessage(color("&aYou've selected the " + kit.getName() + " kit."));
                    if(user.isAutoDeploy()) {
                        TeleportUtil.getTeleport(player);
                        kit.giveKit(player);
                        player.setMetadata("kit", new hasKit());
                    }
                    break;
                }
                player.openInventory(KitGUI.getInventory(player, false, 1));
                break;
            case CHEST:
                if(user.getCrate() != null && user.getCrate()>0) {
                    CrateGUI.getInventory(player);
                    user.setCrate(user.getCrate()-1);
                }
                else
                    player.sendMessage(color("&cYou have no crates to open."));
                break;
            case SKULL_ITEM:
                player.openInventory(ProfileGUI.getInventory(player));
                break;
            case BLAZE_POWDER:
                player.openInventory(CosmeticsGUI.getInventory(player, CosmeticType.valueOf(user.getLastCosmeticType()), user.getCosmeticWasShop(), 1));
                break;
        }
    }

    @EventHandler
    public void onMove(InventoryClickEvent event){
        if(event.getWhoClicked().hasMetadata("kit") || event.getWhoClicked().hasMetadata("build")) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(!(event.getClickedInventory().equals(event.getWhoClicked().getInventory()))) return;
        event.setCancelled(true);
    }
}
