package me.sahustei.miraclepvp.commands.subcommands.suffix;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.suffix.Suffix;
import me.sahustei.miraclepvp.data.trail.Trail;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class IconSuffix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /suffix icon <name> <icon>"));
            return true;
        }
        try {
            if (Data.getSuffix(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no suffix with this name."));
                return true;
            } else {
                if (Material.getMaterial(args[2].toUpperCase()) != null) {
                    Material icon = Material.getMaterial(args[2].toUpperCase());
                    if (!icon.equals(Material.AIR)) {
                        Suffix suffix = Data.getSuffix(args[1]);
                        sender.sendMessage(Text.color("&aYou have set the icon of the suffix " + suffix.getName() + " to " + args[2].toUpperCase() + "!"));
                        suffix.setIcon(icon);
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
            sender.sendMessage(Text.color("&cThere is no suffix with this name."));
            return true;
        }
    }
}
