package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class MapKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length != 2) {
            if (!(sender.hasPermission("miraclepvp.kitpvp.map"))) {
                sender.sendMessage(color("&4You don't have enough permissions to do this!"));
                return true;
            }
            player.teleport(new Location(Bukkit.getWorld("ffa"), 0, 100, 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
            return true;
        }
        try{
            Player target = Bukkit.getPlayerExact(args[1]);
            target.teleport(new Location(Bukkit.getWorld("ffa"), 0, 100, 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
            target.sendMessage(color("&a" + player.getName() + " forced you to the map."));
            player.sendMessage(color("&aYou forced " + target.getName() + " to the map."));
            return true;
        } catch (NullPointerException ex){
            player.sendMessage(color("&cThe given player is not online."));
            return true;
        }
    }
}
