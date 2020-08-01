package net.miraclepvp.kitpvp.commands.subcommands.arena;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Pos2Arena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cYou have to be a player to do this."));
            return true;
        }
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /arena pos2 <map> <number>"));
            return true;
        }
        try {
            if (Data.getMap(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no map with this name."));
                return true;
            }
        } catch (NoSuchElementException ex) {
        }
        try {
            Data.getMap(args[1]).getArena(Integer.parseInt(args[2])).setLocationB(((Player) sender).getLocation());
            sender.sendMessage(color("&aYou've succesfully changed position 2. Make sure to enable the arena if everything is configured by using: /arena toggle " + Data.getMap(args[1]).name + " " + args[2] + "."));
            return false;
        } catch (IndexOutOfBoundsException ex) {
            sender.sendMessage(color("&cThe given arena does not exist."));
            return true;
        }
    }
}
