package net.miraclepvp.kitpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PingUtil {

    public static int pingPlayer(Player p) {
        try {
            String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftPlayer");
            Object handle = craftPlayer.getMethod("getHandle").invoke(p);
            Integer ping = (Integer) handle.getClass().getDeclaredField("ping").get(handle);
            return ping.intValue();
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            return -1;
        }
    }
}
