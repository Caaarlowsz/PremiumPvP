package net.miraclepvp.kitpvp.commands.subcommands.duel;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AcceptDuel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 1){
            sender.sendMessage(color("&cPlease use /duel accept <player>"));
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
                if(Duel.isIngame(player)){
                    sender.sendMessage(color("&cThis player is already in a game."));
                    return true;
                }

                User user = Data.getUser(((Player) sender));
                if(Duel.getDuel(player).bid>user.getCoins()){
                    sender.sendMessage(color("&cYou dont have enough coins to afford this."));
                    return true;
                }

                Duel.joinGame((Player)sender, Duel.getDuel(player));
                return true;
            }
        } catch (Exception e) {
            sender.sendMessage(color("&cThe given player is not online."));
            return true;
        }
    }
}
