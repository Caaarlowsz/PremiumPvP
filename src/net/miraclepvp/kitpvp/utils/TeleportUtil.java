package net.miraclepvp.kitpvp.utils;

import net.miraclepvp.kitpvp.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TeleportUtil {

    private static ArrayList<Player> availablePlayers = new ArrayList<>();
    private static ArrayList<Location> shuffledLocs = Config.getSpawnpoints();

    private static Location loc;

    public static void getTeleport(Player player){
        Collections.shuffle(shuffledLocs);
        player.teleport(getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public static Location getLocation(){
        Random random = new Random();
        Bukkit.getOnlinePlayers().stream()
                .filter(players -> players.hasMetadata("kit") && !players.hasMetadata("vanished") && !players.hasMetadata("build")).forEach(players -> availablePlayers.add(players));

        loc =  shuffledLocs.get(random.nextInt(shuffledLocs.size()));

        if(availablePlayers.size() <= 0) return loc;

        boolean found = false;
        int count = 0;
        while(!found && count < shuffledLocs.size()){
            if(getPlayersNearby(shuffledLocs.get(count), 50)) {
                found = true;
                return shuffledLocs.get(count);
            } else
                count++;
        }
        shuffledLocs.forEach(location -> {
            if(getPlayersNearby(location, 50))
                loc = location;
        });
        return loc;
    }

    public static boolean getPlayersNearby(Location loc, int range) {
        for (Entity entity : loc.getWorld().getEntities())
            if (isInBorder(loc, entity.getLocation(), range))
                if(entity instanceof Player && availablePlayers.contains(entity))
                    return true;
        return false;
    }

    public static boolean isInBorder(Location center, Location notCenter, int range) {
        int x = center.getBlockX(), z = center.getBlockZ();
        int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();

        if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
            return false;
        } else if(Math.abs(Math.round(x) - Math.round(x1))<10){
            return false;
        }
        return true;
    }
}
