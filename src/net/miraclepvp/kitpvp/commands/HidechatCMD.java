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

public class HidechatCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 1){
            sender.sendMessage(color("&cPlease use /hidechat <guild" + (sender.hasPermission("miraclepvp.staffchat.send") ? "/staff" : "") + ">"));
            return true;
        }
        User user = Data.getUser(Bukkit.getOfflinePlayer(((Player)sender).getUniqueId()));
        if(args[0].equalsIgnoreCase("guild")){
            if(user.getHiddenChats().contains(Chatmode.GUILD)){
                user.getHiddenChats().remove(Chatmode.GUILD);
                sender.sendMessage(color("&aThe guild has been unmuted!"));
            } else{
                user.getHiddenChats().add(Chatmode.GUILD);
                sender.sendMessage(color("&aThe guild has been muted!"));
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("staff")){
            if(!sender.hasPermission("miraclepvp.staffchat.send")){
                sender.sendMessage(color("&cPlease use /hidechat <guild" + (sender.hasPermission("miraclepvp.staffchat.send") ? "/staff" : "") + ">"));
                return true;
            }
            if(user.getHiddenChats().contains(Chatmode.STAFF)){
                user.getHiddenChats().remove(Chatmode.STAFF);
                sender.sendMessage(color("&aThe staff has been unmuted!"));
            } else{
                user.getHiddenChats().add(Chatmode.STAFF);
                sender.sendMessage(color("&aThe staff has been muted!"));
            }
            return true;
        }
        return true;
    }
}
