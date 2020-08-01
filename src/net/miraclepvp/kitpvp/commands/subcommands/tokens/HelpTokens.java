package net.miraclepvp.kitpvp.commands.subcommands.tokens;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpTokens implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fCosmoTokens"));
        player.sendMessage(color("&7Tokens to buy cosmetics."));
        player.sendMessage(color("&7Aliases: tokens - cosmo - ct."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/cosmotokens add <player> <amount>"));
        player.sendMessage(color("&f/cosmotokens set <player> <amount>"));
        player.sendMessage(color("&f/cosmotokens remove <player> <amount>"));
        player.sendMessage(color("&f/cosmotokens balance"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
