package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class PromoteGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(color("&cPlease use /guild promote <name>"));
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

        if(!userPerms.contains(PermissionType.PROMOTE) && !guild.getMaster().equals(uuid)){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }

        try {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if (Data.getUser(target).getGuild() == null || !Data.getUser(target).getGuild().equals(guild.getUuid())) {
                sender.sendMessage(color("&cThis player is not in the guild."));
                return true;
            }

            if(!guild.getMembers().contains(target.getUniqueId())){
                sender.sendMessage(color("&cThis player is not a Member."));
                return true;
            }

            guild.getMembers().remove(target.getUniqueId());
            guild.getOfficers().add(target.getUniqueId());

            guild.sendBroadcast(sender.getName() + " promoted " + target.getName() + " to Officer.");
            return true;
        } catch (Exception ex){
            sender.sendMessage(color("&cThis player doesn't exist."));
            return true;
        }
    }
}
