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

public class TransferGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 2){
            sender.sendMessage(color("&cPlease use /guild transfer <name>"));
            return true;
        }
        UUID uuid = ((Player) sender).getUniqueId();

        User user = Data.getUser(((Player)sender));

        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }
        Guild guild = Data.getGuild(user.getGuild());

        if(!guild.getMaster().equals(uuid)){
            sender.sendMessage(color("&4You have to be the master to do this."));
            return true;
        }

        try{
            Player target = Bukkit.getPlayerExact(args[1]);

            if (Data.getUser(target).getGuild() == null || guild.equals(guild.getUuid())) {
                sender.sendMessage(color("&cThis player is not in the guild."));
                return true;
            }

            if(!target.hasPermission("miraclepvp.guild.create")){
                sender.sendMessage(color("&cThe given player doesn't have enough permissions to become a guild master."));
                return true;
            }

            if(guild.getMembers().contains(target.getUniqueId()))
                guild.getMembers().remove(target.getUniqueId());
            if(guild.getOfficers().contains(target.getUniqueId()))
                guild.getOfficers().remove(target.getUniqueId());
            guild.setMaster(target.getUniqueId());
            guild.getOfficers().add(uuid);

            guild.sendBroadcast(sender.getName() + " transfered the guild to " + target.getName() +  ".");
            return true;
        } catch (Exception ex){
            sender.sendMessage(color("&cThe given player has to be online."));
            return true;
        }
    }
}
