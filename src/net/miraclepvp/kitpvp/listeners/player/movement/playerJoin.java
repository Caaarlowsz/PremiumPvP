package net.miraclepvp.kitpvp.listeners.player.movement;

import net.minecraft.server.v1_8_R3.*;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.UpdateChecker;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.listeners.custom.PlayerSpawnEvent;
import net.miraclepvp.kitpvp.objects.Board;
import net.miraclepvp.kitpvp.objects.Tablist;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerJoin implements Listener {

    @EventHandler
    public void onPreJoin(PlayerPreLoginEvent event) {
        if (!Data.users.stream().anyMatch(u -> u.getUuid().equals(event.getUniqueId()))) {
            User user = new User(event.getUniqueId());
            Data.users.add(user);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        handleJoin(event.getPlayer());
    }

    public static void handleJoin(Player player) {
        User user = Data.getUser(player);

        player.spigot().respawn();

        Board.updateScoreboard(player);

        Thread thread = new Thread(() -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            user.setLastJoin(formatter.format(date));
            Tablist.sendTab(player);

            if(player.isOp()) {
                UpdateChecker updater = new UpdateChecker(Main.getInstance(), 46196);
                try {
                    if (updater.checkForUpdates())
                        player.sendMessage(color("&5&lThere is a new update for PremiumPvP!"));
                    player.sendMessage(color("&fYou can download it from:&7 " + updater.getResourceUrl()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(player));

        new BukkitRunnable() {
            @Override
            public void run() {
                Board.showEveryoneInTab(player);
                Board.addPlayerInTab(player);
                Board.updatePlayerListName(player);
            }
        }.runTaskLater(Main.getInstance(), 2L);
    }
}
