package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.UpdatelogGUI;
import net.miraclepvp.kitpvp.objects.Board;
import net.miraclepvp.kitpvp.objects.Item;
import net.miraclepvp.kitpvp.objects.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class playerJoin implements Listener {

    @EventHandler
    public void onPreJoin(PlayerPreLoginEvent event){
        if(!Data.users.stream().anyMatch(u -> u.getUuid().equals(event.getUniqueId()))){
            User user = new User(event.getUniqueId());
            Data.users.add(user);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        handleJoin(event.getPlayer());
    }

    public static void handleJoin(Player player){
        User user = Data.getUser(player);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        if(!user.getLastVersion().equalsIgnoreCase(Main.getInstance().version)) {
            player.getInventory().addItem(UpdatelogGUI.getBook());
            user.setLastVersion(Main.getInstance().version);
        }
        user.setLastJoin(formatter.format(date));
        Tablist.sendTab(player);
        handleSpawn(player);
    }

    public static void handleSpawn(Player player){
        Board.showScoreboard(player);
        if(player.hasMetadata("vanished")) return;
        if(player.hasMetadata("build")) return;
        if(Config.getLobbyLoc().getY() > 0)
            player.teleport(Config.getLobbyLoc());
        player.removeMetadata("kit", Main.getInstance());
        player.getInventory().setArmorContents(null);
        player.getInventory().clear();
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        Item.types.forEach(item -> player.getInventory().setItem(item.getPosition(), item.getItem()));
    }
}
