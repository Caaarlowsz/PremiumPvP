package net.miraclepvp.kitpvp.commands.subcommands.chatcolor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fChatColor"));
        player.sendMessage(color("&7Manage the chatcolors in the server."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/chatcolor add <name> <chatcolor> <icon> <price>"));
        player.sendMessage(color("&f/chatcolor delete <name>"));
        player.sendMessage(color("&f/chatcolor give <player> <name>"));
        player.sendMessage(color("&f/chatcolor rename <name> <new_name>"));
        player.sendMessage(color("&f/chatcolor price <name> <new_price>"));
        player.sendMessage(color("&f/chatcolor sellprice <name> <new_sellprice>"));
        player.sendMessage(color("&f/chatcolor icon <name> <icon>"));
        player.sendMessage(color("&f/chatcolor list"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
