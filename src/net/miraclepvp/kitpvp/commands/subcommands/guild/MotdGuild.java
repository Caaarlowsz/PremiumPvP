package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class MotdGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 2){
            sender.sendMessage(color("&cPlease use /guild motd <new motd>"));
            return true;
        }
        UUID uuid = ((Player) sender).getUniqueId();

        User user = Data.getUser(((Player)sender));

        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }
        Guild guild = Data.getGuild(user.getGuild());

        ArrayList<PermissionType> userPerms = new ArrayList<>();

        if(guild.getMembers().contains(uuid))
            userPerms = guild.getMemberPerms();
        if(guild.getOfficers().contains(uuid))
            userPerms = guild.getOfficerPerms();

        if(!userPerms.contains(PermissionType.MOTD) && !guild.getMaster().equals(uuid)){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }

        String message = "";
        for(int i = 1; i<args.length; i++){
            message = message + args[i] + " ";
        }

        if(message.contains("&")){
            sender.sendMessage(color("&cThe motd can not contain the & symbol."));
            return true;
        }

        guild.sendBroadcast(sender.getName() + " updated the guild motd.");
        guild.setMotd(message);
        return true;
    }
}
