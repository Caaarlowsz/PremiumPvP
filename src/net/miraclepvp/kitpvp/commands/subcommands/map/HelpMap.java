package net.miraclepvp.kitpvp.commands.subcommands.map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpMap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fMap"));
        player.sendMessage(color("&7Manage the maps in the server."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/map create <name>"));
        player.sendMessage(color("&f/map remove <name>"));
        player.sendMessage(color("&f/map teleport <name>"));
        player.sendMessage(color("&f/map list"));
        player.sendMessage(color("&f/map icon <name> <icon>"));
        player.sendMessage(color("&f/map name <name> <new_name>"));
        player.sendMessage(color("&f/map description <name> <description>"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
