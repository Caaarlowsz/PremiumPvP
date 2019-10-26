package net.miraclepvp.kitpvp.commands.subcommands.coins;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

public class BalanceCoins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Text.color("&aYou have " + Data.getUser(((Player) sender)).getCoins() + " coins."));
            return true;
        }
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player == null) {
                sender.sendMessage(Text.color("&cThis player doesn't exist."));
                return true;
            }
            User target = Data.getUser(player);
            if (target == null) {
                sender.sendMessage(Text.color("&cThis player doesn't exist."));
                return true;
            }
            sender.sendMessage(Text.color("&a" + player.getName() + " has " + target.getCoins() + " coins."));
            return true;
        } catch (
                NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
