package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class DiscordGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        User user = Data.getUser(player);
        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }

        Guild guild = Data.getGuild(user.getGuild());

        UUID uuid = player.getUniqueId();
        ArrayList<PermissionType> userPerms = new ArrayList<>();

        if(guild.getMembers().contains(uuid))
            userPerms = guild.getMemberPerms();
        if(guild.getOfficers().contains(uuid))
            userPerms = guild.getOfficerPerms();

        if(args.length != 2){
            player.sendMessage(color("&5&m-----------------------------------------------------"));
            player.sendMessage(color("&f" + guild.getName()));
            player.sendMessage(color(" "));
            player.sendMessage(color("&fDISCORD"));
            player.sendMessage(color("&7" + guild.getDiscord()));
            if(userPerms.contains(PermissionType.DISCORD) || guild.getMaster().equals(uuid))
                sender.sendMessage(color("&7To change the invite link, use &5/guild discord <discord link>&7 or &5/guild discord reset &7to remove it"));
            player.sendMessage(color("&5&m-----------------------------------------------------"));
            return true;
        }

        if(!userPerms.contains(PermissionType.DISCORD) && !guild.getMaster().equals(uuid)){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }

        if(!args[1].toLowerCase().startsWith("https://discord.gg/")){
            if(args[1].toLowerCase().equalsIgnoreCase("reset")){
                guild.setDiscord("Set the discord using /guild discord <discord>");
                guild.sendBroadcast(player.getName() + " removed the Guild Discord invite link.");
                return true;
            }
            sender.sendMessage(color("&cThe given url is not a discord link."));
            return true;
        }

        guild.setDiscord(args[1]);
        guild.sendBroadcast(player.getName() + " changed the Guild Discord invite link.");
        return true;
    }
}
