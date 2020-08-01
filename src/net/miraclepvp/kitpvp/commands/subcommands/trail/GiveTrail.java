package net.miraclepvp.kitpvp.commands.subcommands.trail;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class GiveTrail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Text.color("&cPlease use /trail give <player> <name>"));
            return true;
        }
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player == null) return true;
            User target = Data.getUser(player);
            if (target == null) return true;
            try {
                if (Data.getTrail(args[2]) == null) {
                    sender.sendMessage(Text.color("&cThere is no trail with this name."));
                    return true;
                } else {
                    Trail trail = Data.getTrail(args[2]);
                    if(target.getTrailsList().contains(trail.getUuid())){
                        sender.sendMessage(Text.color("&cThis player already has this trail!"));
                        return true;
                    }
                    sender.sendMessage(Text.color("&aYou have succesfully given " + player.getName() + " the trail " + trail.getName() + "!"));
                    target.addTrail(trail.getUuid());
                    return true;
                }
            } catch (NoSuchElementException ex) {
                sender.sendMessage(Text.color("&cThere is no trail with this name."));
                return true;
            }
        } catch (NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
