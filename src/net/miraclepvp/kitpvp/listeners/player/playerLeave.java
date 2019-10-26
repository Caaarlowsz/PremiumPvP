package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class playerLeave implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if(!(Main.getInstance().combatTimer.containsKey(event.getPlayer()))) return;
        if(Main.getInstance().combatTimer.get(event.getPlayer()) <= 0) return;
        playerJoin.handleSpawn(event.getPlayer());
        User user = Data.getUser(event.getPlayer());
        user.setDeaths(user.getDeaths()+1);
        user.setKillstreak(0);
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&l!&8] &c" + event.getPlayer().getName() + "&7 left the server while in combat!"));
    }
}
