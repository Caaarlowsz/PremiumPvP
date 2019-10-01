package me.sahustei.miraclepvp.commands.subcommands.prefix;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.prefix.Prefix;
import me.sahustei.miraclepvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class SetPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /prefix set <player> <name>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[2]) == null) {
                sender.sendMessage(color("&cThere is no prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        try{
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player == null) return true;
            User target = Data.getUser(player);
            if (target == null) return true;
            Prefix prefix = Data.getPrefix(args[2]);
            target.setPrefix(prefix);
            sender.sendMessage(color("&aYou've set the prefix of " + player.getName() + " to " + prefix.getPrefix() + "."));
            return true;
        } catch (Exception ex) {
            sender.sendMessage(Text.color("&cSomething went wrong, check your command and try again."));
            return true;
        }
    }
}
