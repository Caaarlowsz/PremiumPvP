package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.listeners.custom.PlayerStatusChangeEvent;
import net.miraclepvp.kitpvp.listeners.custom.PlayerUnnickEvent;
import net.miraclepvp.kitpvp.objects.NickManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerUnnick implements Listener {

    @EventHandler
    public void onUnnick(PlayerUnnickEvent e) {
        if(!(e.isCancelled())) {
            Player p = e.getPlayer();

            new NickManager(p).unnickPlayer();

            p.sendMessage(color("&cYou are now undisguised. Others will see your own name and skin again."));

            Bukkit.getPluginManager().callEvent(new PlayerStatusChangeEvent(p));
        }
    }
}
