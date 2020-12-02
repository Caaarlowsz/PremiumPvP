package net.miraclepvp.kitpvp.commands.subcommands.arena;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Arena;
import net.miraclepvp.kitpvp.data.duel.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CreateArena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cYou have to be a player to do this."));
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(color("&cPlease use /arena create <map>"));
            return true;
        }
        try {
            if (Data.getMap(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no map with this name."));
                return true;
            }
        }catch (NoSuchElementException ex){
        }
        Data.getMap(args[1]).addArena(new Arena());
        sender.sendMessage(color("&aThe arena is successfully created. Make sure to configure everything. (/arena pos1 " + args[1] + " " + Data.getMap(args[1]).arenaList.size() + ")"));
        return true;
    }
}
