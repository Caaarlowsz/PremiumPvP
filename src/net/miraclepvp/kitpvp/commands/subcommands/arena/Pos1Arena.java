package net.miraclepvp.kitpvp.commands.subcommands.arena;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Pos1Arena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cYou have to be a player to do this."));
            return true;
        }
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /arena pos1 <map> <number>"));
            return true;
        }
        try {
            if (Data.getMap(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no map with this name."));
                return true;
            }
        }catch (NoSuchElementException ex){
        }
        try {
            Data.getMap(args[1]).getArena(Integer.parseInt(args[2])).setLocationA(((Player) sender).getLocation());
            sender.sendMessage(color("&aYou've succesfully changed position 1. Make sure to set position 2 by using: /arena pos2 " + Data.getMap(args[1]).name + " " + args[2] + "."));
            return false;
        }catch (IndexOutOfBoundsException ex){
            sender.sendMessage(color("&cThe given arena does not exist."));
            return true;
        }
    }
}
