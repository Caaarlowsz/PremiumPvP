package me.sahustei.miraclepvp.commands.subcommands.namecolor;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.chatcolor.Chatcolor;
import me.sahustei.miraclepvp.data.namecolor.Namecolor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class IconName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /namecolor icon <name> <icon>"));
            return true;
        }
        try {
            if (Data.getNamecolor(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
                return true;
            } else {
                if (Material.getMaterial(args[2].toUpperCase()) != null) {
                    Material icon = Material.getMaterial(args[2].toUpperCase());
                    if (!icon.equals(Material.AIR)) {
                        Namecolor namecolor = Data.getNamecolor(args[1]);
                        sender.sendMessage(Text.color("&aYou have set the icon of the namecolor " + namecolor.getName() + " to " + args[2].toUpperCase() + "!"));
                        namecolor.setIcon(icon);
                        return true;
                    } else {
                        sender.sendMessage(Text.color("&cThe given material does not exist."));
                        return true;
                    }
                } else {
                    sender.sendMessage(Text.color("&cThe given material does not exist."));
                    return true;
                }
            }
        } catch (NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
            return true;
        }
    }
}
