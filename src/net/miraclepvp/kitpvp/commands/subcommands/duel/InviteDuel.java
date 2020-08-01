package net.miraclepvp.kitpvp.commands.subcommands.duel;

import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.inventories.DuelGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class InviteDuel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("miraclepvp.duel.invite"))){
            sender.sendMessage(color("&cYou don't have the required permissions."));
            return true;
        }
        if(args.length == 1){
            sender.sendMessage(color("&cPlease use /duel invite <player>"));
            return true;
        }
        try {
            if(args[1].toLowerCase().equalsIgnoreCase(sender.getName().toLowerCase())){
                sender.sendMessage(color("&cYou can not duel yourself."));
                return true;
            }
            Duel duel = new Duel(((Player)sender).getUniqueId());
            duel.host = ((Player) sender).getUniqueId();
            Duel.duels.add(duel);
            ((Player)sender).openInventory(DuelGUI.getMainInventory(duel, Bukkit.getPlayerExact(args[1])));
            return true;
        } catch (Exception e) {
            sender.sendMessage(color("&cThe given player is not online."));
            return true;
        }
    }
}
