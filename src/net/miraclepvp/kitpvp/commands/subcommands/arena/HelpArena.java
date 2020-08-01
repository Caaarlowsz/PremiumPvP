package net.miraclepvp.kitpvp.commands.subcommands.arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpArena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fArena"));
        player.sendMessage(color("&7Manage the arenas in the server."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/arena create <map>"));
        player.sendMessage(color("&f/arena remove <map> <number>"));
        player.sendMessage(color("&f/arena list <map>"));
        player.sendMessage(color("&f/arena pos1 <map> <number>"));
        player.sendMessage(color("&f/arena pos2 <map> <number>"));
        player.sendMessage(color("&f/arena toggle <map> <number>"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
