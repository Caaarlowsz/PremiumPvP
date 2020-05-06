package net.miraclepvp.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class StaffchatCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(color("&cConsole cant use the staffchat."));
            return true;
        }
        if(!sender.hasPermission("miraclepvp.staffchat.send")){
            sender.sendMessage(color("&4You don't have the needed permissions to do this."));
            return true;
        }
        if(args.length<1){
            sender.sendMessage(color("&cPlease give a message."));
            return true;
        }
        String txt = "";
        for(int i = 0; i<args.length; i++)
            txt = txt+args[i]+" ";
        sendMessage(((Player) sender).getUniqueId(), txt);
        return true;
    }

    public static void sendMessage(UUID uuid, String message){
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("miraclepvp.staffchat.receive"))
                .forEach(player -> player.sendMessage(color("&f[&4SC&f] " + Bukkit.getOfflinePlayer(uuid).getName() + "&8: &f" + message)));
    }
}