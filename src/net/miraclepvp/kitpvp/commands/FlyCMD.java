package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class FlyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(color("&cOnly players are allowed to execute this command."));
            return true;
        }
        if(!(sender.hasPermission("miraclepvp.donator.fly"))){
            ((Player) sender).performCommand(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if(((Player) sender).hasMetadata("kit")) {
            sender.sendMessage(color("&cYou can only use flight at spawn."));
            return true;
        }
        User user = Data.getUser(((Player) sender));
        user.setFlyEnabled(!user.isFlyEnabled());
        sender.sendMessage(color("&aTurned " + (user.isFlyEnabled() ? "on" : "off") + " your flight."));
        return true;
    }
}
