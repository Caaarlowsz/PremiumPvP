package net.miraclepvp.kitpvp.commands.subcommands.tokens;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class SetTokens implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /cosmotokens set <player> <amount>"));
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
            try {
                Integer value = Integer.parseInt(args[2]);
                target.setTokens(value);
                sender.sendMessage(Text.color("&aYou have set " + player.getName() + "'s tokens to " + target.getTokens() + "."));
                return true;
            } catch (Exception ex) {
                sender.sendMessage(Text.color("&cSomething went wrong, check your command."));
                return true;
            }
        } catch (
                NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
