package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.objects.NickManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class RealnameKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("miraclepvp.kitpvp.realname"))){
            sender.sendMessage(color("&cYou don't have the required permissions to execute this."));
            return true;
        }
        if(args.length!=2){
            sender.sendMessage(color("&cPlease use /kitpvp realname <name>"));
            return true;
        }
        try{
            Player target = Bukkit.getPlayerExact(args[1]);
            sender.sendMessage(color("&6" + target.getName() + "'s realname is " + NickManager.getRealName(target.getUniqueId())));
        }catch (Exception ex){
            sender.sendMessage(color("&cWe failed to find this player. Make sure you spelled the name correctly."));
            return true;
        }
        return true;
    }
}
