package net.miraclepvp.kitpvp.commands.subcommands.serverevent;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpServerevent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fServer Event"));
        player.sendMessage(color(""));
        if(player.hasPermission("miraclepvp.serverevent")) {
            player.sendMessage(color("&f/serverevent start"));
            player.sendMessage(color("&f/serverevent stop"));
            player.sendMessage(color("&f/serverevent open"));
            player.sendMessage(color("&f/serverevent close"));
            player.sendMessage(color("&f/serverevent broadcast"));
            player.sendMessage(color("&f/serverevent setlocation"));
            player.sendMessage(color("&f/serverevent setkit <kitname>"));
        }
        player.sendMessage(color("&f/serverevent join"));
        player.sendMessage(color("&f/serverevent leave"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
