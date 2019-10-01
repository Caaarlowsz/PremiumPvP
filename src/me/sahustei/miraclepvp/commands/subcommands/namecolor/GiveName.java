package me.sahustei.miraclepvp.commands.subcommands.namecolor;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.chatcolor.Chatcolor;
import me.sahustei.miraclepvp.data.namecolor.Namecolor;
import me.sahustei.miraclepvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class GiveName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /namecolor give <player> <name>"));
            return true;
        }
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player == null) return true;
            User target = Data.getUser(player);
            if (target == null) return true;
            if (Data.getNamecolor(args[2]) == null) {
                sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
                return true;
            }
            try {
                Namecolor namecolor = Data.getNamecolor(args[2]);
                if (target.getNamecolorsList().contains(namecolor.getUuid())) {
                    sender.sendMessage(Text.color("&cThis player already has this namecolor!"));
                    return true;
                }
                sender.sendMessage(Text.color("&aYou have succesfully given " + player.getName() + " the namecolor " + namecolor.getName() + "!"));
                target.addNameColor(namecolor.getUuid());
                return true;
            } catch (NoSuchElementException ex) {
                sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
                return true;
            }
        } catch (NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
