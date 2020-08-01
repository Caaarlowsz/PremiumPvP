package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.Chatmode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ChatCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command label, String cmd, String[] args) {
        if(args.length < 1){
            sender.sendMessage(color("&cPlease use /chat <guild/all" + (sender.hasPermission("miraclepvp.staffchat.send") ? "/staff" : "") + ">"));
            return true;
        }
        User user = Data.getUser(Bukkit.getOfflinePlayer(((Player)sender).getUniqueId()));
        if(args[0].equalsIgnoreCase("guild")){
            if(user.getGuild() == null){
                sender.sendMessage(color("&4You are not in a guild."));
                return true;
            }
            user.setChatmode(Chatmode.GUILD);
            sender.sendMessage(color("&aChatmode has been set to guild chat!"));
            return true;
        }
        if(args[0].equalsIgnoreCase("all")){
            user.setChatmode(Chatmode.ALL);
            sender.sendMessage(color("&aChatmode has been set to public chat!"));
            return true;
        }
        if(args[0].equalsIgnoreCase("staff")){
            if(!sender.hasPermission("miraclepvp.staffchat.send")){
                sender.sendMessage(color("&cPlease use /chat <guild/all" + (sender.hasPermission("miraclepvp.staffchat.send") ? "/staff" : "") + ">"));
                return true;
            }
            user.setChatmode(Chatmode.STAFF);
            sender.sendMessage(color("&aChatmode has been set to staff chat!"));
            return true;
        }
        return true;
    }
}
