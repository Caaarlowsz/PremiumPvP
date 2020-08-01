package net.miraclepvp.kitpvp.commands.subcommands.duel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpDuel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fDuel"));
        player.sendMessage(color("&7Get help with our Duel system."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/duel invite <player>"));
        player.sendMessage(color("&f/duel spectate <player>"));
        player.sendMessage(color("&f/duel accept <player>"));
        player.sendMessage(color("&f/duel decline <player>"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }

}
