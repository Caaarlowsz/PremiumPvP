package net.miraclepvp.kitpvp.commands.subcommands.spawnpoint;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpSpawnpoints implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fSpawnpoints"));
        player.sendMessage(color("&7Manage the spawnpoints in the map."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/spawnpoints add"));
        player.sendMessage(color("&f/spawnpoints delete <id>"));
        player.sendMessage(color("&f/spawnpoints list"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
