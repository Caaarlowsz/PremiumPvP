package net.miraclepvp.kitpvp.objects;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.listeners.custom.PlayerSpawnEvent;
import net.miraclepvp.kitpvp.listeners.player.movement.playerJoin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;
import static org.bukkit.Bukkit.getServer;

public class ServerEvent {

    public static Location location = null;
    public static boolean started = false;
    public static boolean open = true;
    public static Kit usedKit = null;
    public static List<UUID> players = new ArrayList<>();

    public static void sendBroadcast(String message){
        players.forEach(player -> {
            if(Bukkit.getOfflinePlayer(player).isOnline())
                Bukkit.getPlayer(player).sendMessage(color("&8[&5E-BC&8] &f" + message));
        });
    }

    public static void join(Player player){
        player.setMetadata("event", new inEvent());

        players.add(player.getUniqueId());
        sendBroadcast("&6" + player.getName() + " joined the event.");

        player.getInventory().clear();
        player.teleport(location);

        player.setAllowFlight(false);
        player.setFlying(false);

        if(usedKit!=null)
            Data.getUser(player).giveKit(usedKit.uuid, true, false);
    }

    public static void leave(Player player, Boolean leaveFromList){
        sendBroadcast("&6" + player.getName() + " left the event.");
        players.remove(player.getUniqueId());

        Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(player));

        player.removeMetadata("event", Main.getInstance());
    }

    public static void open(){
        open = true;
        getServer().dispatchCommand(getServer().getConsoleSender(), "broadcast The server event is open again, type /se join to join the event.");
    }

    public static void close(){
        open = false;
        getServer().dispatchCommand(getServer().getConsoleSender(), "broadcast The server event is closed, you can not join anymore.");
    }

    public static void start(){
        if(location == null)
            return;
        started = true;
        open = true;
        players.clear();
        getServer().dispatchCommand(getServer().getConsoleSender(), "broadcast The server event is starting soon, type /se join to join the event.");
    }

    public static void stop(){
        started = false;
        open = true;
        location = null;

        List<UUID> playerCopy = new ArrayList<>();
        for(UUID uuid : players)
            playerCopy.add(uuid);

        playerCopy.forEach(player -> {
            if(Bukkit.getOfflinePlayer(player).isOnline())
                leave(Bukkit.getPlayer(player), false);
        });

        getServer().dispatchCommand(getServer().getConsoleSender(), "broadcast The server event is ended, thank you all for participating in the event.");

        players.clear();
    }
}
