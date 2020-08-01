package net.miraclepvp.kitpvp.commands.subcommands.crate;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.Crate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class RemoveCrate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 4){
            sender.sendMessage(color("&cPlease use /crate remove <player> <crate> <amount>"));
            return true;
        }
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player == null) return true;
            User target = Data.getUser(player);
            if (target == null) return true;
            try {
                Crate crate = Crate.valueOf(args[2].toUpperCase());
                Integer value = Integer.parseInt(args[3]);
                if((!target.getCrates().containsKey(crate)) || target.getCrates().get(crate)<value){
                    sender.sendMessage(color("&cThis person does not have " + value + " " + crate.toString() + " crates."));
                    return true;
                }
                target.getCrates().put(crate, target.getCrates().get(crate)-value);
                sender.sendMessage(Text.color("&aYou have removed " + value + " " + crate.toString() + " crates from " + player.getName() + "'s account."));
                return true;
            } catch (Exception ex) {
                sender.sendMessage(Text.color("&cSomething went wrong, check your command."));
                return true;
            }
        } catch (NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
