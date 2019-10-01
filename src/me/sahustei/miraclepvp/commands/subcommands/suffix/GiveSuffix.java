package me.sahustei.miraclepvp.commands.subcommands.suffix;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.suffix.Suffix;
import me.sahustei.miraclepvp.data.trail.Trail;
import me.sahustei.miraclepvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class GiveSuffix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Text.color("&cPlease use /suffix give <player> <name>"));
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
                    if(target.getSuffixesList().contains(suffix.getUuid())){
                        sender.sendMessage(Text.color("&cThis player already has this suffix!"));
                        return true;
                    }
                    sender.sendMessage(Text.color("&aYou have succesfully given " + player.getName() + " the suffix " + suffix.getName() + "!"));
                    target.addSuffix(suffix.getUuid());
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
