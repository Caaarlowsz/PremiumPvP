package net.miraclepvp.kitpvp.commands.subcommands.suffix;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AddSuffix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 5) {
            sender.sendMessage(color("&cPlease use /suffix add <name> <suffix> <icon> <price>"));
            return true;
        }
        Integer price;
        try {
            price = Integer.parseInt(args[4]);
        } catch (IllegalArgumentException ex) {
            sender.sendMessage(color("&cThe given price is not a valid amount."));
            return true;
        }
        try {
            if (Data.getSuffix(args[1]) != null) {
                sender.sendMessage(color("&cThere is already a suffix with this name."));
                return true;
            } else {
                if (Material.getMaterial(args[3].toUpperCase()) != null) {
                    Material icon = Material.getMaterial(args[3].toUpperCase());
                    if (!icon.equals(Material.AIR)) {
                        Suffix suffix = new Suffix(args[1], color(args[2]), icon, price, price / 2);
                        Data.suffixes.add(suffix);
                        sender.sendMessage(color("&aYou have succesfully added the suffix " + suffix.getName() + "!"));
                        return true;
                    } else {
                        sender.sendMessage(color("&cThe given material does not exist."));
                        return true;
                    }
                } else {
                    sender.sendMessage(color("&cThe given material does not exist."));
                    return true;
                }
            }
        } catch (NoSuchElementException ex) {
            if (Material.getMaterial(args[3].toUpperCase()) != null) {
                Material icon = Material.getMaterial(args[3].toUpperCase());
                if (!icon.equals(Material.AIR)) {
                    Suffix suffix = new Suffix(args[1], color(args[2]), icon, price, price / 2);
                    Data.suffixes.add(suffix);
                    sender.sendMessage(color("&aYou have succesfully added the suffix " + suffix.getName() + "!"));
                    return true;
                } else {
                    sender.sendMessage(color("&cThe given material does not exist."));
                    return true;
                }
            } else {
                sender.sendMessage(color("&cThe given material does not exist."));
                return true;
            }
        }
    }
}
