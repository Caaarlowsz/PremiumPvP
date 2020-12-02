package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.report.Reports;
import net.miraclepvp.kitpvp.inventories.ReportGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;
import static org.bukkit.Bukkit.getServer;

public class ReportCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cOnly players are allowed to execute this command."));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(color("&cPlease use /report <player>."));
            return true;
        }

        if(!Config.UseMySQL()) {
            sender.sendMessage(color("&4The report system is currently disabled."));
            return true;
        }

        Player target = getServer().getPlayer(args[0]);

        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("menu") && sender.hasPermission("miraclepvp.report.menu")) {
                    ((Player) sender).openInventory(ReportGUI.getStaffInventory());
                    return true;
                }
                if(target == null){
                    sender.sendMessage(color("&cThe player has to be online."));
                    return true;
                }
                ((Player) sender).openInventory(ReportGUI.getInventory(((Player) sender), target));
            break;
            case 2:
                if(target == null){
                    sender.sendMessage(color("&cThe player has to be online."));
                    return true;
                }
                String reason = args[1].toUpperCase().replaceAll("-", "_");
                try {
                    Reports.ReportCategory.Abusing.valueOf(reason);
                } catch (IllegalArgumentException a) {
                    try {
                        Reports.ReportCategory.Chat.valueOf(reason);
                    } catch (IllegalArgumentException b) {
                        try {
                            Reports.ReportCategory.Cheating.valueOf(reason);
                        } catch (IllegalArgumentException c) {
                            try {
                                Reports.ReportCategory.Others.valueOf(reason);
                            } catch (IllegalArgumentException d) {
                                sender.sendMessage(color("&cPlease use /report <player>."));
                                return true;
                            }
                        }
                    }
                }
                List<String> reasonList = new ArrayList<>();
                reasonList.add(reason);
                new Reports.Report(((Player) sender).getUniqueId(), target.getUniqueId(), reasonList);
                sender.sendMessage(color("&aYou've reported " + target.getName() + " for " + reason + "."));
                break;
            default:
                sender.sendMessage(color("&cPlease use /report <player>."));
                break;
        }
        return true;
    }
}
