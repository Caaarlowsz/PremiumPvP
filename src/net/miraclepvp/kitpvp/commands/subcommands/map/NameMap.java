package net.miraclepvp.kitpvp.commands.subcommands.map;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class NameMap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /map name <name> <new_name>"));
            return true;
        }
        Map map;
        try {
            map = Data.getMap(args[1]);
        }catch (NoSuchElementException e){
            sender.sendMessage(Text.color("&cThere is no map with this name."));
            return true;
        }
        if(map!=null){
            map.name = args[2];
            sender.sendMessage(color("&aYou've successfully changed the name of " + map.name + "!"));
        }
        return true;
    }
}
