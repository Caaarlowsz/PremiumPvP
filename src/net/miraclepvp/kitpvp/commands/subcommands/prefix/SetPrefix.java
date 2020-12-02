package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.Board;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class SetPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /prefix set <player> <name>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[2]) == null) {
                sender.sendMessage(Text.color("&cThere is no prefix with this name."));
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
            sender.sendMessage(Text.color("&aYou've set the prefix of " + player.getName() + " to " + prefix.getPrefix() + "."));
            return true;
        } catch (Exception ex) {
            sender.sendMessage(Text.color("&cSomething went wrong, check your command and try again."));
            return true;
        }
    }
}
