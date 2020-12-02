package net.miraclepvp.kitpvp.commands.subcommands.suffix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpSuffix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fSuffix"));
        player.sendMessage(color("&7Manage the suffixes in the server."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/suffix add <name> <suffix> <icon> <price>"));
        player.sendMessage(color("&f/suffix delete <name>"));
        player.sendMessage(color("&f/suffix give <player> <name>"));
        player.sendMessage(color("&f/suffix remove <player> <name>"));
        player.sendMessage(color("&f/suffix buyable <name>"));
        player.sendMessage(color("&f/suffix setsuffix <name> <suffix>"));
        player.sendMessage(color("&f/suffix rename <name> <new_name>"));
        player.sendMessage(color("&f/suffix price <name> <new_price>"));
        player.sendMessage(color("&f/suffix sellprice <name> <new_sellprice>"));
        player.sendMessage(color("&f/suffix icon <name> <icon>"));
        player.sendMessage(color("&f/suffix list"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
