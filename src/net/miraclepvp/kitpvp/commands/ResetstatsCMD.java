package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ResetstatsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("miraclepvp.resetstats"))) {
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if(args.length<1){
            sender.sendMessage(color("&cPlease use /resetstats <playerName/all>"));
            return true;
        }
        if(args[0].equalsIgnoreCase("all")){
            for(User user : Data.users)
                user.createStats(user.getUuid(), true);

            Data.save(false);
            Data.save(true);
            return true;
        }
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (player == null) return true;
            User target = Data.getUser(player);
            if (target == null) return true;
            target.createStats(player.getUniqueId(), true);
            sender.sendMessage(color("&aYou've successfully resetted " + player.getName() + "'s Stats!"));
            return true;
        } catch (NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
