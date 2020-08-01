package net.miraclepvp.kitpvp.commands.subcommands.trail;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpTrail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fTrail"));
        player.sendMessage(color("&7Manage the trails in the server."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/trail add <name> <particle> <icon> <price>"));
        player.sendMessage(color("&f/trail delete <name>"));
        player.sendMessage(color("&f/trail give <player> <name>"));
        player.sendMessage(color("&f/trail rename <name> <new_name>"));
        player.sendMessage(color("&f/trail price <name> <new_price>"));
        player.sendMessage(color("&f/trail sellprice <name> <new_sellprice>"));
        player.sendMessage(color("&f/trail icon <name> <icon>"));
        player.sendMessage(color("&f/trail list"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
