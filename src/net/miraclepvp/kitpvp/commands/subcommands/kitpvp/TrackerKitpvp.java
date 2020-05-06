package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class TrackerKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!sender.hasPermission("miraclepvp.kitpvp.gettracker")){
            sender.sendMessage(color("&cYou are not permitted to do this."));
            return true;
        }
        ((Player) sender).getInventory().addItem(new ItemstackFactory(Material.COMPASS).setDisplayName("&5Tracking nobody").addLoreLine("&7Player Tracker"));
        return true;
    }
}
