package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.*;
import net.miraclepvp.kitpvp.objects.CosmeticType;
import net.miraclepvp.kitpvp.objects.hasKit;
import net.miraclepvp.kitpvp.utils.CooldownUtil;
import net.miraclepvp.kitpvp.utils.TeleportUtil;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerInventory implements Listener {

    @EventHandler
    public void onInteractWithoutKit(PlayerInteractEvent event) {
        if (event.getPlayer().hasMetadata("kit")) return;
        if (event.getPlayer().hasMetadata("build")) return;
        Player player = event.getPlayer();
        User user = Data.getUser(player);
        if (user == null) return;
        if (event.getItem() == null || event.getItem().getType() == null) return;
        switch (event.getItem().getType()) {
            case COMPASS:
                if (user.isQuickSelect() && (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
                    if (user.getPreviousKit() == null || (Data.getKit(user.getPreviousKit()).getPrice() > 0 && !(user.getKitsList().contains(user.getPreviousKit())))) {
                        player.sendMessage(color("&cWe failed to get your previous kit, please select a kit."));
                        break;
                    }
                    Kit kit = Data.getKit(user.getPreviousKit());
                    player.sendMessage(color("&aYou've selected the " + kit.getName() + " kit."));
                    if (user.isAutoDeploy()) {
                        TeleportUtil.getTeleport(player);
                        if(user.giveKit(user.getPreviousKit(), true)){
                            player.setMetadata("kit", new hasKit());
                            player.setAllowFlight(false);
                            player.setFlying(false);
                        }else {
                            player.sendMessage(color("&cCouldn't set your kit, something went wrong."));
                            playerJoin.handleSpawn(player);
                        }
                        player.setMetadata("kit", new hasKit());
                    }
                    break;
                }
                player.openInventory(KitGUI.getInventory(player, false, 1));
                break;
            case EYE_OF_ENDER:
                player.openInventory(AbilityGUI.getMainInventory());
                event.setCancelled(true);
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
    public void interactWithKit(PlayerInteractEvent event) {
        if (!event.getPlayer().hasMetadata("kit")) return;
        if (event.getPlayer().hasMetadata("build")) return;
        Player player = event.getPlayer();
        User user = Data.getUser(player);
        if (user == null) return;
        if (event.getItem() == null || event.getItem().getType() == null) return;

        //              Enderpearl cooldown system

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem().getType().equals(Material.ENDER_PEARL)) {
                //ENDERPEARL TROWN
                CooldownUtil.Cooldown cooldown = CooldownUtil.getCooldown(player, "enderpearl");
                if (cooldown == null || !cooldown.hasTimeLeft()) {
                    CooldownUtil.Cooldown newCooldown = CooldownUtil.prepare(player, "enderpearl", 30);
                    newCooldown.start();
                } else {
                    event.setCancelled(true);
                    player.sendMessage(color("&cWait " + cooldown.getSecondsLeft() + " seconds before throwing a new enderpearl!"));
                    player.updateInventory();
                }
            }
            if(event.getItem().getType().equals(Material.COMPASS)){
                if(event.getItem().getItemMeta() != null &&
                        event.getItem().getItemMeta().getLore() != null &&
                        event.getItem().getItemMeta().getLore().get(0) != null &&
                        event.getItem().getItemMeta().getLore().get(0).equalsIgnoreCase(color("&7Player Tracker"))
                ) {
                    CooldownUtil.Cooldown cooldown = CooldownUtil.getCooldown(player, "tracker");
                    if (cooldown == null || !cooldown.hasTimeLeft()) {
                        CooldownUtil.Cooldown newCooldown = CooldownUtil.prepare(player, "tracker", 30);
                        newCooldown.start();

                        Player target = getNearest(player, 100.0);
                        if (target == null) {
                            player.setCompassTarget(player.getLocation());
                            ItemMeta meta = event.getItem().getItemMeta();
                            meta.setDisplayName(Text.color("&5Tracking nobody"));
                            event.getItem().setItemMeta(meta);
                            return;
                        }
                        player.setCompassTarget(target.getLocation());
                        ItemMeta meta = event.getItem().getItemMeta();
                        meta.setDisplayName(Text.color("&5Tracking: " + target.getName()));
                        event.getItem().setItemMeta(meta);
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(color("&cYou have to wait " + cooldown.getSecondsLeft() + " seconds before you can get a new location!"));
                        player.updateInventory();
                    }
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onMove(InventoryClickEvent event) {
        if (event == null) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getWhoClicked() == null) return;
        if (event.getInventory() == null) return;
        if (event.getClickedInventory() == null) return;
        if (event.getWhoClicked().getInventory() == null) return;
        if (event.getWhoClicked().hasMetadata("kit") || event.getWhoClicked().hasMetadata("build")) return;
        if (!(event.getClickedInventory().equals(event.getWhoClicked().getInventory()))) return;
        event.setCancelled(true);
    }

    public Player getNearest(Player p, Double range) {
        double distance = Double.POSITIVE_INFINITY;
        Player target = null;
        for (Entity e : p.getNearbyEntities(range, range, range)) {
            if (!(e instanceof Player))
                continue;
            if (e == p) continue;
            if (!((Player) e).getGameMode().equals(GameMode.ADVENTURE))
                continue;
            if (e.hasMetadata("build") || e.hasMetadata("vanished") || !e.hasMetadata("kit"))
                continue;
            double distanceto = p.getLocation().distance(e.getLocation());
            if (distanceto > distance)
                continue;
            distance = distanceto;
            target = (Player) e;
        }
        return target;
    }
}
