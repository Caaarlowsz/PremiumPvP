package net.miraclepvp.kitpvp.commands.subcommands.kit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fKit"));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/kit create <name> <icon> <price>"));
        player.sendMessage(color("&f/kit delete <name>"));
        player.sendMessage(color("&f/kit rename <name>"));
        player.sendMessage(color("&f/kit price <name> <price>"));
        player.sendMessage(color("&f/kit sellprice <name> <price>"));
        player.sendMessage(color("&f/kit edit <name>"));
        player.sendMessage(color("&f/kit addeffect <name> <effect> <duration> <level>"));
        player.sendMessage(color("&f/kit removeeffect <name> <effect>"));
        player.sendMessage(color("&f/kit list"));
        player.sendMessage(color("&f/kit give <player> <name>"));
        player.sendMessage(color("&f/kit toggle <name>"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
