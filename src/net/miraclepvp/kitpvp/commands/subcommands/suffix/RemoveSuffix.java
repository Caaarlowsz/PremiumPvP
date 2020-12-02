package net.miraclepvp.kitpvp.commands.subcommands.suffix;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class RemoveSuffix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Text.color("&cPlease use /suffix remove <player> <name>"));
            return true;
        }
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player == null) return true;
            User target = Data.getUser(player);
            if (target == null) return true;
            try {
                if (Data.getSuffix(args[2]) == null) {
                    sender.sendMessage(Text.color("&cThere is no suffix with this name."));
                    return true;
                } else {
                    Suffix suffix = Data.getSuffix(args[2]);
                    if(!target.getSuffixesList().contains(suffix.getUuid())){
                        sender.sendMessage(Text.color("&cThis player does not have this suffix!"));
                        return true;
                    }
                    if(target.getActiveSuffix().equals(suffix.getUuid()))
                        target.setActiveSuffix(null);
                    sender.sendMessage(Text.color("&aYou have successfully taken the suffix " + suffix.getName() + " from " + player.getName() + "!"));
                    target.removeSuffixes(suffix.getUuid());
                    return true;
                }
            } catch (NoSuchElementException ex) {
                sender.sendMessage(Text.color("&cThere is no suffix with this name."));
                return true;
            }
        } catch (NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
