package net.miraclepvp.kitpvp.commands.subcommands.crate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpCrate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fCrates"));
        player.sendMessage(color("&7COLOR, TRAIL, SUFFIX, GEAR, GEARMIRACLE"));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/crate add <player> <crate> <amount>"));
        player.sendMessage(color("&f/crate set <player> <crate> <amount>"));
        player.sendMessage(color("&f/crate remove <player> <crate> <amount>"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
