package me.sahustei.miraclepvp.commands.subcommands.kitpvp;

import me.sahustei.miraclepvp.Main;
import me.sahustei.miraclepvp.data.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class SetlobbyKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!(sender.hasPermission("miraclepvp.kitpvp.setlobby"))){
            sender.sendMessage(color("&4You don't have enough permissions to do this!"));
            return true;
        }
        sender.sendMessage(color("&aYou've succesfully set the lobby location to your current location!"));
        Config.setLobbyLoc(((Player) sender).getLocation());
        return true;
    }
}
