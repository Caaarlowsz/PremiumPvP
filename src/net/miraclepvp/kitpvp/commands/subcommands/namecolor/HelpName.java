package net.miraclepvp.kitpvp.commands.subcommands.namecolor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fNameColor"));
        player.sendMessage(color("&7Manage the namecolors in the server."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/namecolor add <name> <namecolor> <icon> <price>"));
        player.sendMessage(color("&f/namecolor delete <name>"));
        player.sendMessage(color("&f/namecolor give <player> <name>"));
        player.sendMessage(color("&f/namecolor rename <name> <new_name>"));
        player.sendMessage(color("&f/namecolor price <name> <new_price>"));
        player.sendMessage(color("&f/namecolor sellprice <name> <new_sellprice>"));
        player.sendMessage(color("&f/namecolor icon <name> <icon>"));
        player.sendMessage(color("&f/namecolor list"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
