package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fPrefix"));
        player.sendMessage(color("&7Manage the prefixes in the server."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/prefix add <name> <prefix>"));
        player.sendMessage(color("&f/prefix delete <name>"));
        player.sendMessage(color("&f/prefix set <player> <name>"));
        player.sendMessage(color("&f/prefix rename <name> <new_name>"));
        player.sendMessage(color("&f/prefix setprefix <name> <prefix>"));
        player.sendMessage(color("&f/prefix setweight <name> <weight>"));
        player.sendMessage(color("&f/prefix clear <player>"));
        player.sendMessage(color("&f/prefix info <name>"));
        player.sendMessage(color("&f/prefix list"));
        player.sendMessage(color( "&5&m-----------------------------------------------------"));
        return true;
    }
}
