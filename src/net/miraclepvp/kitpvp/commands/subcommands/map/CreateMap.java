package net.miraclepvp.kitpvp.commands.subcommands.map;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.bukkit.WorldManager;
import net.miraclepvp.kitpvp.commands.subcommands.kitpvp.BuildmodeKitpvp;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Arena;
import net.miraclepvp.kitpvp.data.duel.Map;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CreateMap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cYou have to be a player to do this."));
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage(color("&cPlease use /map create <name>"));
            return true;
        }
        try {
            if (Data.getMap(args[1]) != null) {
                sender.sendMessage(Text.color("&cThere is already a map with this name."));
                return true;
            }
        }catch (NoSuchElementException ex){
        }
        Data.maps.add(new Map(args[1]));

        WorldManager.loadEmptyWorld(World.Environment.NORMAL, args[1].toLowerCase());
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.getWorld(args[1].toLowerCase()).getBlockAt(new Location(Bukkit.getWorld(args[1]), 0, 100, 0)).setType(Material.BEDROCK);
                new BuildmodeKitpvp().onCommand(sender, cmd, label, args);
                ((Player) sender).teleport(new Location(Bukkit.getWorld(args[1]), 0, 101, 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                sender.sendMessage(color("&aThe map " + args[1] + " is successfully created. Make sure to configure everything. (/map help)"));
            }
        }.runTaskLater(Main.getInstance(), 20L);
        return true;
    }
}
