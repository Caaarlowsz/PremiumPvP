package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fKitPvP"));
        player.sendMessage(color("&7Aliases: kp."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/kitpvp info"));
        player.sendMessage(color("&f/kitpvp lobby <player>"));
        player.sendMessage(color("&f/kitpvp map <player>"));
        player.sendMessage(color("&f/kitpvp setlobby"));
        player.sendMessage(color("&f/kitpvp buildmode"));
        player.sendMessage(color("&f/kitpvp gettracker"));
        player.sendMessage(color("&f/kitpvp getswitcherball"));
        player.sendMessage(color("&f/kitpvp reload"));
        player.sendMessage(color("&f/kitpvp save"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
