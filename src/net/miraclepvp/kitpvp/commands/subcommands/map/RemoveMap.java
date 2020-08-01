package net.miraclepvp.kitpvp.commands.subcommands.map;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class RemoveMap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(color("&cPlease use /map remove <name>"));
            return true;
        }
        try {
            Data.maps.remove(Data.getMap(args[1]));
            sender.sendMessage(color("&aThe map " + args[1] + " is successfully deleted."));
            return true;
        }catch (NoSuchElementException e){
            sender.sendMessage(Text.color("&cThere is no map with this name."));
            return true;
        }
    }
}
