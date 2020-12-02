package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class TagcolorGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        UUID uuid = ((Player) sender).getUniqueId();

        User user = Data.getUser(((Player)sender));

        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }
        Guild guild = Data.getGuild(user.getGuild());

        if(args.length!=2){
            sender.sendMessage(color("&cPlease use /guild tagcolor <" + guild.getTagcolors().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "/") + ">"));
            return true;
        }

        ArrayList<PermissionType> userPerms = new ArrayList<>();

        if(guild.getMembers().contains(uuid))
            userPerms = guild.getMemberPerms();
        if(guild.getOfficers().contains(uuid))
            userPerms = guild.getOfficerPerms();

        if(!userPerms.contains(PermissionType.TAGCOLOR) && !guild.getMaster().equals(uuid)){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }

        if(args[1].length()>1 || ChatColor.getByChar(args[1]) == null){
            sender.sendMessage(color("&cThis is not a valid color."));
            return true;
        }

        if(!guild.getTagcolors().contains(args[1].toCharArray()[0])){
            Bukkit.broadcastMessage(Character.toString(args[1].toCharArray()[0]));
            sender.sendMessage(color("&cYour guild does not own this tag color."));
            return true;
        }

        guild.setActiveColor(args[1].toCharArray()[0]);
        guild.sendBroadcast(sender.getName() + " changed the tag color to " + ChatColor.getByChar(args[1]).name().toLowerCase().replaceAll("_", " ") + ".");
        return true;
    }
}
