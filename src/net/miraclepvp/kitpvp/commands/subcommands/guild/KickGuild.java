package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class KickGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(color("&cPlease use /guild kick <name>"));
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

        if(!userPerms.contains(PermissionType.KICK) && !guild.getMaster().equals(uuid)){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }

        try {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if (Data.getUser(target).getGuild() == null || guild.equals(guild.getUuid())) {
                sender.sendMessage(color("&cThis player is not in the guild."));
                return true;
            }

            if(target.getUniqueId().equals(guild.getMaster())){
                sender.sendMessage(color("&cYou can't kick the Master!"));
                return true;
            }

            if(guild.getMembers().contains(target.getUniqueId()))
                guild.getMembers().remove(target.getUniqueId());
            if(guild.getOfficers().contains(target.getUniqueId()))
                guild.getOfficers().remove(target.getUniqueId());
            Data.getUser(target).setGuild(null);

            if(target.isOnline())
                Bukkit.getPlayer(target.getName()).sendMessage(color("&cYou've been kicked from the guild."));
            guild.sendBroadcast(sender.getName() + " kicked " + target.getName() + " from the guild.");
            return true;
        } catch (Exception ex){
            sender.sendMessage(color("&cThis player doesn't exist."));
            return true;
        }
    }
}
