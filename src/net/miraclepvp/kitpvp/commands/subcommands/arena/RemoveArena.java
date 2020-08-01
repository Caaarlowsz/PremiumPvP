package net.miraclepvp.kitpvp.commands.subcommands.arena;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class RemoveArena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cYou have to be a player to do this."));
            return true;
        }
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /arena remove <map> <number>"));
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
            Integer.parseInt(args[2]);
        } catch (Exception ex) {
            sender.sendMessage(color("&cPlease make sure the arena number is an actual number"));
            return true;
        }
        try {
            Data.getMap(args[1]).removeArena(Integer.parseInt(args[2]));
            sender.sendMessage(color("&aThe arena is successfully deleted."));
            return true;
        } catch (IndexOutOfBoundsException ex) {
            sender.sendMessage(color("&cThe given arena does not exist."));
            return true;
        }
    }
}
