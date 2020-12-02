package net.miraclepvp.kitpvp.commands.subcommands.duel;

import net.miraclepvp.kitpvp.data.duel.Duel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class DeclineDuel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1){
            sender.sendMessage(color("&cPlease use /duel decline <player>"));
            return true;
        }
        try {
            Player player = Bukkit.getPlayerExact(args[1]);
            if(args[1].toLowerCase().equalsIgnoreCase(sender.getName().toLowerCase())){
                sender.sendMessage(color("&cYou can not duel yourself."));
                return true;
            }
            if(!Duel.playerInvited(player, (Player)sender)){
                sender.sendMessage(color("&cThis player did not invite you for a duel, or the invite is already expired."));
                return true;
            } else {
                Duel.declineGame((Player)sender, Duel.getDuel(player));
                return true;
            }
        } catch (Exception e) {
            sender.sendMessage(color("&cThe given player is not online."));
            return true;
        }
    }
}
