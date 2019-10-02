package me.sahustei.miraclepvp.listeners.player;

import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.kit.Kit;
import me.sahustei.miraclepvp.data.user.User;
import me.sahustei.miraclepvp.inventories.CosmeticsGUI;
import me.sahustei.miraclepvp.inventories.KitGUI;
import me.sahustei.miraclepvp.objects.CosmeticType;
import me.sahustei.miraclepvp.objects.hasKit;
import me.sahustei.miraclepvp.utils.TeleportUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static me.sahustei.miraclepvp.bukkit.Text.color;

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
                    if (user.getPreviousKit() == null) {
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
                player.sendMessage("kit unlocker");
                break;
            case SKULL_ITEM:
                player.sendMessage("profile");
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
