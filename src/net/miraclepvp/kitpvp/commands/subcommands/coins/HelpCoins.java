package net.miraclepvp.kitpvp.commands.subcommands.coins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpCoins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fCoins"));
        player.sendMessage(color("&7Coins to buy kits."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/coins add <player> <amount>"));
        player.sendMessage(color("&f/coins set <player> <amount>"));
        player.sendMessage(color("&f/coins remove <player> <amount>"));
        player.sendMessage(color("&f/coins balance"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
