package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Arena;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.duel.Map;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.DuelGUI;
import net.miraclepvp.kitpvp.inventories.KitGUI;
import net.miraclepvp.kitpvp.inventories.KitLayoutGUI;
import net.miraclepvp.kitpvp.listeners.player.playerJoin;
import net.miraclepvp.kitpvp.objects.hasKit;
import net.miraclepvp.kitpvp.utils.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class DuelListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Duel - "))) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;

        /*      Duel Invite - Main Menu         */
        if (ChatColor.stripColor(event.getClickedInventory().getName()).equalsIgnoreCase("Duel - Configure the game")) {
            String[] words = ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(0)).split(" ");
            Player host = Bukkit.getPlayer(words[0]);
            Player player = Bukkit.getPlayer(words[2]);
            Duel duel = Duel.getDuel(host);
            switch (event.getSlot()) {
                case 19:
                    duel.allowSpectator = !duel.allowSpectator;
                    host.openInventory(DuelGUI.getMainInventory(duel, player));
                    break;
                case 21:
                    host.openInventory(DuelGUI.getBidInventory(duel, player));
                    break;
                case 23:
                    host.openInventory(DuelGUI.getKitInventory(duel, player, 1));
                    break;
                case 25:
                    host.openInventory(DuelGUI.getMapInventory(duel, player, 1));
                    break;
                case 39:
                    Duel.duels.remove(duel);
                    host.closeInventory();
                    break;
                case 41:
                    if (duel.kit == null || duel.map == null) {
                        host.sendMessage(color("&cCouldn't create the game! Make sure you selected a kit and a map!"));
                        break;
                    }

                    if(Data.getMap(duel.map).getAvailableArenas()<=0){
                        host.sendMessage(color("&cThere are currently no available arena's for this map. Try again later or pick a different map."));
                        return;
                    }

                    User user = Data.getUser(host);
                    if(duel.bid>user.getCoins()){
                        host.sendMessage(color("&cYou dont have enough coins to afford this."));
                        return;
                    }

                    Arena arena = Data.getMap(duel.map).getAvailableArena();
                    arena.enabled=false;
                    duel.arena = arena.uuid;

                    duel.invitePlayer(player);
                    host.closeInventory();
                    break;
            }
        }

        /*      Duel Invite - Bed Menu         */
        if (ChatColor.stripColor(event.getClickedInventory().getName()).equalsIgnoreCase("Duel - Select a bid")) {
            String[] words = ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(0)).split(" ");
            Player host = Bukkit.getPlayer(words[0]);
            Player player = Bukkit.getPlayer(words[2]);
            Duel duel = Duel.getDuel(host);
            if (event.getSlot() == 10) duel.bid += 50;
            if (event.getSlot() == 11) duel.bid += 100;
            if (event.getSlot() == 12) duel.bid += 500;
            if (event.getSlot() == 13) {
                host.openInventory(DuelGUI.getMainInventory(duel, player));
                return;
            }
            if (event.getSlot() == 14) {
                if((duel.bid-50)<0) return;
                duel.bid -= 50;
            }
            if (event.getSlot() == 15) {
                if((duel.bid-100)<0) return;
                duel.bid -= 100;
            }
            if (event.getSlot() == 16) {
                if((duel.bid-500)<0) return;
                duel.bid -= 500;
            }
            host.openInventory(DuelGUI.getBidInventory(duel, player));
        }

        /*      Duel Invite - Kit Menu         */
        if (ChatColor.stripColor(event.getClickedInventory().getName()).equalsIgnoreCase("Duel - Select a kit")) {
            String[] words = ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(0)).split(" ");
            Player host = Bukkit.getPlayer(words[0]);
            Player player = Bukkit.getPlayer(words[2]);
            Duel duel = Duel.getDuel(host);
            Integer page = 0;

            if (event.getSlot() == 47 && org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).contains("Previous page")) {
                try {
                    String number = org.bukkit.ChatColor.stripColor(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName())
                            .replaceAll("Previous page", "")
                            .replaceAll("\\(", "")
                            .replaceAll("\\)", "")
                            .replaceAll(" ", "");
                    page = Integer.parseInt(number);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                host.openInventory(DuelGUI.getKitInventory(duel, player, page));
            }
            if (event.getSlot() == 51 &&
                    event.getCurrentItem() != null &&
                    event.getCurrentItem().getItemMeta() != null &&
                    event.getCurrentItem().getItemMeta().getDisplayName() != null &&
                    org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).contains("Next page")
            ) {
                try {
                    String number = org.bukkit.ChatColor.stripColor(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName())
                            .replaceAll("Next page", "")
                            .replaceAll("\\(", "")
                            .replaceAll("\\)", "")
                            .replaceAll(" ", "");
                    page = Integer.parseInt(number);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                host.openInventory(DuelGUI.getKitInventory(duel, player, page));
            }

            if (event.getCurrentItem().getType() == Material.AIR) return;
            try {
                Kit kit = Data.getKit(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                if (event.getClick().equals(ClickType.LEFT)) {
                    if (!kit.isEnabled()) {
                        host.sendMessage(color("&cThis kit is temporary disabled, try again later."));
                        return;
                    }
                    duel.kit = kit;
                    host.openInventory(DuelGUI.getMainInventory(duel, player));
                }
            } catch (NoSuchElementException ex) {
            }
        }

        /*      Duel Invite - Map Menu         */
        if (ChatColor.stripColor(event.getClickedInventory().getName()).equalsIgnoreCase("Duel - Select a map")) {
            String[] words = ChatColor.stripColor(event.getClickedInventory().getItem(4).getItemMeta().getLore().get(0)).split(" ");
            Player host = Bukkit.getPlayer(words[0]);
            Player player = Bukkit.getPlayer(words[2]);
            Duel duel = Duel.getDuel(host);
            Integer page = 0;

            if (event.getSlot() == 20 && org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).contains("Previous page")) {
                try {
                    String number = org.bukkit.ChatColor.stripColor(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName())
                            .replaceAll("Previous page", "")
                            .replaceAll("\\(", "")
                            .replaceAll("\\)", "")
                            .replaceAll(" ", "");
                    page = Integer.parseInt(number);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                host.openInventory(DuelGUI.getMapInventory(duel, player, page));
                return;
            }
            if (event.getSlot() == 24 &&
                    event.getCurrentItem() != null &&
                    event.getCurrentItem().getItemMeta() != null &&
                    event.getCurrentItem().getItemMeta().getDisplayName() != null &&
                    org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).contains("Next page")
            ) {
                try {
                    String number = org.bukkit.ChatColor.stripColor(event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName())
                            .replaceAll("Next page", "")
                            .replaceAll("\\(", "")
                            .replaceAll("\\)", "")
                            .replaceAll(" ", "");
                    page = Integer.parseInt(number);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                host.openInventory(DuelGUI.getMapInventory(duel, player, page));
                return;
            }

            if (event.getCurrentItem().getType() == Material.AIR) return;
            try {
                if (event.getClick().equals(ClickType.LEFT)) {
                    Map map = Data.getMap(org.bukkit.ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    if(map == null) return;
                    duel.map = map.uuid;
                    host.openInventory(DuelGUI.getMainInventory(duel, player));
                }
            } catch (NoSuchElementException ex) {
            }
        }

    }
}
