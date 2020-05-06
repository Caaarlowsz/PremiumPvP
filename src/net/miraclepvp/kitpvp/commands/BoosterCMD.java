package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.booster.GiveBooster;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.Booster;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.BoosterGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class BoosterCMD implements CommandExecutor {

    private GiveBooster giveBooster = new GiveBooster();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length>0)
            switch (args[0].toLowerCase()){
                case "give":
                    giveBooster.onCommand(sender, cmd, label, args);
                    break;
                default:
                    if(!(sender instanceof Player)){
                        sender.sendMessage(color("&cOnly players are allowed to execute this command."));
                        return true;
                    }
                    ((Player) sender).openInventory(BoosterGUI.getInventory(((Player)sender), BoosterGUI.FilterType.ALL));
                    break;
            }
        else {
            if(!(sender instanceof Player)){
                sender.sendMessage(color("&cOnly players are allowed to execute this command."));
                return true;
            }
            ((Player) sender).openInventory(BoosterGUI.getInventory(((Player)sender), BoosterGUI.FilterType.ALL));
        }
        return true;
    }
}
