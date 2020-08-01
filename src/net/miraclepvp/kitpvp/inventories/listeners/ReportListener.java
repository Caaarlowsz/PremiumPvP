package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.report.Reports;
import net.miraclepvp.kitpvp.inventories.ReportGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ReportListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Report - "))) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (event.getCurrentItem().getItemMeta().getDisplayName().isEmpty()) return;
        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(" ")) return;
        if (event.getClickedInventory().getName().contains("category")) {
            Player player = ((Player) event.getWhoClicked());
            Player target = Bukkit.getPlayerExact(event.getInventory().getItem(1).getItemMeta().getLore().get(0).split(" ")[1]);
            //Choose category GUI
            List<Integer> toggled = new ArrayList<>();
            switch (event.getSlot()) {
                case 1:
                    player.openInventory(ReportGUI.getFinalInventory(player, target, Reports.ReportCategory.CHAT, toggled));
                    break;
                case 3:
                    player.openInventory(ReportGUI.getFinalInventory(player, target, Reports.ReportCategory.CHEATING, toggled));
                    break;
                case 5:
                    player.openInventory(ReportGUI.getFinalInventory(player, target, Reports.ReportCategory.ABUSING, toggled));
                    break;
                case 7:
                    player.openInventory(ReportGUI.getFinalInventory(player, target, Reports.ReportCategory.OTHERS, toggled));
                    break;
            }
        } else {
            //Final option GUI
            Player player = ((Player) event.getWhoClicked());
            Player target = Bukkit.getPlayerExact(event.getInventory().getItem(1).getItemMeta().getLore().get(0).split(" ")[1]);
            Reports.ReportCategory category = Reports.ReportCategory.valueOf(ChatColor.stripColor(event.getClickedInventory().getItem(17).getItemMeta().getLore().get(0)));

            List<Integer> enabled = new ArrayList<>();
            for (int i = 9; i < 16; i++) {
                if (event.getInventory().getItem(i) != null && event.getInventory().getItem(i).getItemMeta().getDisplayName() != null && ChatColor.stripColor(event.getInventory().getItem(i).getItemMeta().getDisplayName()).equalsIgnoreCase("true"))
                    enabled.add(i-9);
            }

            if (event.getSlot() == 8) {
                if(enabled.size()>0){
                    List<String> reasonList = new ArrayList<>();
                    enabled.forEach(slot -> reasonList.add(ChatColor.stripColor(event.getClickedInventory().getItem(slot).getItemMeta().getDisplayName()).toUpperCase()));
                    new Reports.Report(player.getUniqueId(), target.getUniqueId(), reasonList);
                    String reason = reasonList.toString().substring(1, reasonList.toString().length()-1);
                    player.sendMessage(color("&aYou've reported " + target.getName() + " for " + reason + "."));
                    player.closeInventory();
                    return;
                } else {
                    player.sendMessage(color("&cGive at least one reason."));
                    return;
                }
            }

            Boolean toggled = enabled.contains(event.getSlot());

            if (toggled)
                enabled.remove(enabled.indexOf(event.getSlot()));
            else
                enabled.add(event.getSlot());

            event.getWhoClicked().openInventory(ReportGUI.getFinalInventory(player, target, category, enabled));
        }
    }
}
