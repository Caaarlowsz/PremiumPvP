package net.miraclepvp.kitpvp.commands.subcommands.map;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class TeleportMap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(color("&cOnly players can execute this command!"));
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage(color("&cPlease use /map teleport <name>"));
            return true;
        }
        try {
            ((Player) sender).teleport(new Location(Bukkit.getWorld(args[1].toLowerCase()), 0, 101, 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
            sender.sendMessage(color("&aYou've been teleported to " + args[1] + "."));
            return true;
        }catch (NoSuchElementException e){
            sender.sendMessage(Text.color("&cThere is no map with this name."));
            return true;
        }
    }
}
