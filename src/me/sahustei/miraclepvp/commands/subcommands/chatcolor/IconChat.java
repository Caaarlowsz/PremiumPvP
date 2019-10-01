package me.sahustei.miraclepvp.commands.subcommands.chatcolor;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.chatcolor.Chatcolor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class IconChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /chatcolor icon <name> <icon>"));
            return true;
        }
        try {
            if (Data.getChatcolor(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no chatcolor with this name."));
                return true;
            } else {
                if (Material.getMaterial(args[2].toUpperCase()) != null) {
                    Material icon = Material.getMaterial(args[2].toUpperCase());
                    if (!icon.equals(Material.AIR)) {
                        Chatcolor chatcolor = Data.getChatcolor(args[1]);
                        sender.sendMessage(Text.color("&aYou have set the icon of the chatcolor " + chatcolor.getName() + " to " + args[2].toUpperCase() + "!"));
                        chatcolor.setIcon(icon);
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
            sender.sendMessage(Text.color("&cThere is no chatcolor with this name."));
            return true;
        }
    }
}
