package me.sahustei.miraclepvp.commands.subcommands.kitpvp;

import me.sahustei.miraclepvp.Main;
import me.sahustei.miraclepvp.objects.isBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class BuildmodeKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!(sender.hasPermission("miraclepvp.kitpvp.buildmode"))){
            sender.sendMessage(color("&4You don't have enough permissions to do this!"));
            return true;
        }
        if(((Player) sender).hasMetadata("build")){
            ((Player) sender).removeMetadata("build", Main.getInstance());
            sender.sendMessage(color("&aBuildmode is now disabled."));
            return true;
        } else {
            ((Player) sender).setMetadata("build", new isBuild());
            sender.sendMessage(color("&aBuildmode is now enabled."));
            return true;
        }
    }
}
