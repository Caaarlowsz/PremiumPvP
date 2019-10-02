package me.sahustei.miraclepvp.utils;

import me.sahustei.miraclepvp.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class TeleportUtil {

    public static void getTeleport(Player player){
        Location resultLoc = null;

        //TODO Avoid void
        for(int i = 0; i < 100; i++){
            Location loc = getLocation();
            if(loc.getWorld().getHighestBlockAt((int)loc.getX(), (int)loc.getZ()) == null || loc.getWorld().getHighestBlockAt((int)loc.getX(), (int)loc.getZ()).getType().equals(Material.AIR)) {
                getLocation();
            } else if(loc.getY() < 10) {
                getLocation();
            } else {
                resultLoc = getLocation();
                break;
            }
        }
        if(resultLoc == null)
            player.sendMessage(color("&cWe failed to get a location for you, please try again!"));
        else
            player.teleport(resultLoc);
    }

    private static Location getLocation(){
        ArrayList<Player> avaiblePlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(players -> {
            if(players.hasMetadata("kit"))
                avaiblePlayers.add(players);
        });
        Random rand = new Random();
        Location loc;
        Integer x, z, topX, topY, topZ;
        if(avaiblePlayers.size() > 0) {
            Collections.shuffle(avaiblePlayers);
            x = rand.nextInt(20);
            z = rand.nextInt(20);
            loc = avaiblePlayers.get(0).getLocation();
            if (x < 50)
                topX = loc.getBlockX() - x;
            else
                topX = loc.getBlockX() + x;
            if (z < 50)
                topZ = loc.getBlockZ() - z;
            else
                topZ = loc.getBlockZ() + z;
        } else {
            x = rand.nextInt(50);
            z = rand.nextInt(50);
            loc = Config.getGameLoc();
            if (x < 100)
                topX = loc.getBlockX() - x;
            else
                topX = loc.getBlockX() + x;
            if (z < 100)
                topZ = loc.getBlockZ() - z;
            else
                topZ = loc.getBlockZ() + z;
        }
        topY = loc.getWorld().getHighestBlockYAt(topX, topZ)+2;
        return new Location(loc.getWorld(), topX, topY, topZ);
    }
}
