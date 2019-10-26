package net.miraclepvp.kitpvp.commands.subcommands.namecolor;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AddName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 5) {
            sender.sendMessage(color("&cPlease use /namecolor add <name> <namecolor> <icon> <price>"));
            return true;
        }
        Integer price;
        ChatColor color;
        try{
            color = ChatColor.valueOf(args[2].toUpperCase());
        } catch(IllegalArgumentException ex){
            sender.sendMessage(color("&cThe given namecolor is not a valid amount."));
            return true;
        }
        try {
            price = Integer.parseInt(args[4]);
        } catch (IllegalArgumentException ex) {
            sender.sendMessage(color("&cThe given price is not a valid amount."));
            return true;
        }
        try {
            if (Data.getNamecolor(args[1]) != null) {
                sender.sendMessage(color("&cThere is already a namecolor with this name."));
                return true;
            } else {
                if (Material.getMaterial(args[3].toUpperCase()) != null) {
                    Material icon = Material.getMaterial(args[3].toUpperCase());
                    if (!icon.equals(Material.AIR)) {
                        Namecolor namecolor = new Namecolor(args[1], color, icon, price, price / 2);
                        Data.namecolors.add(namecolor);
                        sender.sendMessage(color("&aYou have succesfully added the namecolor " + namecolor.getName() + "!"));
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
                    Namecolor namecolor = new Namecolor(args[1], color, icon, price, price / 2);
                    Data.namecolors.add(namecolor);
                    sender.sendMessage(color("&aYou have succesfully added the namecolor " + namecolor.getName() + "!"));
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
