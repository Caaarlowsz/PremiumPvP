package me.sahustei.miraclepvp.listeners.player;

import me.sahustei.miraclepvp.Main;
import me.sahustei.miraclepvp.data.Config;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.user.User;
import me.sahustei.miraclepvp.inventories.UpdatelogGUI;
import me.sahustei.miraclepvp.objects.Board;
import me.sahustei.miraclepvp.objects.Item;
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
        handleSpawn(player);
    }

    public static void handleSpawn(Player player){
        Board.showScoreboard(player);
        if(player.hasMetadata("vanished")) return;
        if(Config.getLobbyLoc().getY() > 0)
            player.teleport(Config.getLobbyLoc());
        player.removeMetadata("kit", Main.getInstance());
        player.getInventory().clear();
        Item.types.forEach(item -> player.getInventory().setItem(item.getPosition(), item.getItem()));
    }
}
