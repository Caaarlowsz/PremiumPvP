package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.data.zone.Zone;
import net.miraclepvp.kitpvp.objects.Board;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CreateGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("miraclepvp.guild.create")) {
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(color("&cPlease use /guild create <name>"));
            return true;
        }
        User user = Data.getUser(((Player) sender));
        if (user.getGuild() != null) {
            sender.sendMessage(color("&cYou are already in a guild."));
            return true;
        }

        try {
            if (Data.getZone(args[1]) != null) {
                sender.sendMessage(color("&cThere is already a guild with this name."));
                return true;
            }
        } catch (NoSuchElementException ex) {
            if (args[1].length() < 5 || args[1].length() > 16) {
                sender.sendMessage(color("&cThe guild name has to be 5-16 characters long."));
                return true;
            }

            if(args[1].contains("&")){
                sender.sendMessage(color("&cThe guild name can not contain the & symbol."));
                return true;
            }

            Guild guild = new Guild(args[1], ((Player) sender).getUniqueId());
            Data.guilds.add(guild);
            user.setGuild(guild.getUuid());
            sender.sendMessage(color("&aThe guild " + guild.getName() + " has been created."));
            Board.updatePlayerListName(((Player) sender));
            return true;
        }
        return true;
    }
}
