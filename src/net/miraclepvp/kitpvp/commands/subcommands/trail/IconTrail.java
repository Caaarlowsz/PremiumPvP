package net.miraclepvp.kitpvp.commands.subcommands.trail;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.trail.Trail;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class IconTrail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /trail icon <name> <icon>"));
            return true;
        }
        try {
            if (Data.getTrail(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no trail with this name."));
                return true;
            } else {
                if (Material.getMaterial(args[2].toUpperCase()) != null) {
                    Material icon = Material.getMaterial(args[2].toUpperCase());
                    if (!icon.equals(Material.AIR)) {
                        Trail trail = Data.getTrail(args[1]);
                        sender.sendMessage(Text.color("&aYou have set the icon of the trail " + trail.getName() + " to " + args[2].toUpperCase() + "!"));
                        trail.setIcon(icon);
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
            sender.sendMessage(Text.color("&cThere is no trail with this name."));
            return true;
        }
    }
}
