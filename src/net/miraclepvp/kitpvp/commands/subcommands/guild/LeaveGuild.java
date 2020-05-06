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

import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class LeaveGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(color("&cPlease use /guild leave"));
            return true;
        }
        UUID uuid = ((Player) sender).getUniqueId();

        User user = Data.getUser(((Player) sender));

        if (user.getGuild() == null) {
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }
        Guild guild = Data.getGuild(user.getGuild());

        if(guild.getMaster().equals(uuid)){
            sender.sendMessage(color("&cYou have to make someone else master before leaving."));
            return true;
        }

        if (guild.getMembers().contains(uuid))
            guild.getMembers().remove(uuid);
        if (guild.getOfficers().contains(uuid))
            guild.getOfficers().remove(uuid);
        user.setGuild(null);

        sender.sendMessage(color("&aYou left the guild!"));
        guild.sendBroadcast(sender.getName() + " left the guild.");
        return true;
    }
}
