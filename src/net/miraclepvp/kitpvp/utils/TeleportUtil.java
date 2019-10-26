package net.miraclepvp.kitpvp.utils;

import net.miraclepvp.kitpvp.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TeleportUtil {

    private static ArrayList<Material> banned = new ArrayList<>();

    public static void getTeleport(Player player){
        Location location = getLocation();
        while(!isLocationSafe(location)){
            location = getLocation();
        }
        player.teleport(new Location(location.getWorld(), location.getBlockX()+0.5, location.getBlockY()+0.1, location.getBlockZ()+0.5));
    }

    private static Location getLocation(){
        ArrayList<Player> avaiblePlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(players -> {
            if(players.hasMetadata("kit") && !players.hasMetadata("vanished") && !players.hasMetadata("build"))
                avaiblePlayers.add(players);
        });
        Random rand = new Random();
        Location loc;
        Integer x, z, topX, topY, topZ;
        topY=65;
        if(avaiblePlayers.size() > 0) {
            Collections.shuffle(avaiblePlayers);
            x = rand.nextInt((50 - 25)+1) +25;
            z = rand.nextInt((50 - 25)+1) +25;
            loc = avaiblePlayers.get(0).getLocation();
            if (x < 25)
                topX = loc.getBlockX()-x;
            else
                topX = loc.getBlockX()+x;
            if (z < 25)
                topZ = loc.getBlockZ()-z;
            else
                topZ = loc.getBlockZ()+z;
        } else {
            x = rand.nextInt(500);
            z = rand.nextInt(500);
            loc = Config.getGameLoc();
            if (x < 250)
                topX = loc.getBlockX() - x;
            else
                topX = loc.getBlockX() + x;
            if (z < 250)
                topZ = loc.getBlockZ() - z;
            else
                topZ = loc.getBlockZ() + z;
        }
        Location location = new Location(loc.getWorld(), topX, topY, topZ);
        topY = loc.getWorld().getHighestBlockYAt(location);


        return new Location(loc.getWorld(), topX, topY, topZ);
    }

    private static Boolean isLocationSafe(Location location){
        banned.add(Material.AIR);
        banned.add(Material.LAVA);
        banned.add(Material.LEAVES);
        banned.add(Material.LEAVES_2);
        banned.add(Material.WATER);
        banned.add(Material.STAINED_GLASS);
        banned.add(Material.THIN_GLASS);
        banned.add(Material.WEB);
        banned.add(Material.GLASS);
        banned.add(Material.STAINED_GLASS_PANE);
        banned.add(Material.STONE);
        banned.add(Material.SOIL);

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block block = location.getWorld().getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y-1, z);
        Block above = location.getWorld().getBlockAt(x, y+1, z);

        return !(banned.contains(below.getType()) || (block.getType().isSolid()) || (above.getType().isSolid()));
    }
}
