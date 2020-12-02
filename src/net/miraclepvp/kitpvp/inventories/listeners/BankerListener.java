package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class BankerListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).equalsIgnoreCase("Bank"))) return;
        event.setCancelled(true);
        Player player = ((Player) event.getWhoClicked());
        switch (event.getSlot()) {
            case 1:
                if (event.getClick().equals(ClickType.LEFT)) {
                    execute(true, 50, player);
                }
                if (event.getClick().equals(ClickType.RIGHT)) {
                    execute(false, 50, player);
                }
                break;
            case 3:
                if (event.getClick().equals(ClickType.LEFT)) {
                    execute(true, 100, player);
                }
                if (event.getClick().equals(ClickType.RIGHT)) {
                    execute(false, 100, player);
                }
                break;
            case 5:
                if (event.getClick().equals(ClickType.LEFT)) {
                    execute(true, 250, player);
                }
                if (event.getClick().equals(ClickType.RIGHT)) {
                    execute(false, 250, player);
                }
                break;
            case 7:
                if (event.getClick().equals(ClickType.LEFT)) {
                    execute(true, 500, player);
                }
                if (event.getClick().equals(ClickType.RIGHT)) {
                    execute(false, 500, player);
                }
                break;
        }
    }

    private void execute(Boolean buy, Integer value, Player player) {
        User user = Data.getUser(player);
        if (buy) {
            if (user.getCoins() >= value) {
                user.setCoins(user.getCoins() - value, false);
                player.getInventory().addItem(getBanknote(value));
                return;
            }
            player.sendMessage(color("&cYou don't have enough coins to buy this."));
        } else {
            if (player.getInventory().containsAtLeast(getBanknote(value), 1)) {
                player.getInventory().removeItem(getBanknote(value));
                player.updateInventory();
                user.setCoins(user.getCoins() + value, false);
            } else {
                player.sendMessage(color("&cYou don't have this banknote."));
            }
        }
    }

    public static ItemStack getBanknote(Integer value) {
        return new ItemstackFactory(Material.PAPER).setDisplayName("&5$" + value + " Banknote").addLoreLine("&7You can use this to buy items in the stores.").addLoreLine("&7You can chance this to virtual coins at a bank.");
    }
}
