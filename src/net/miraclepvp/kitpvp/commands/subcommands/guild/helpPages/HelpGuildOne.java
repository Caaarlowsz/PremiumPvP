package net.miraclepvp.kitpvp.commands.subcommands.guild.helpPages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpGuildOne implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fGuild &7(1/2)"));
        player.sendMessage(color("&7Get help with our guild system."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/guild accept <guild name>"));
        player.sendMessage(color("&f/guild create <guild name>"));
        player.sendMessage(color("&f/guild disband"));
        player.sendMessage(color("&f/guild members"));
        player.sendMessage(color("&f/guild online"));
        player.sendMessage(color("&f/guild discord <discord link>"));
        player.sendMessage(color("&f/guild info"));
        player.sendMessage(color("&f/guild mute <player/everyone> <time>"));
        player.sendMessage(color("&f/guild rename <new name>"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
