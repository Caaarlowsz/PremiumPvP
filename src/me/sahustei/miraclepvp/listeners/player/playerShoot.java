package me.sahustei.miraclepvp.listeners.player;

import me.sahustei.miraclepvp.Main;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class playerShoot implements Listener {

    @EventHandler
    public void onPlayerShootArrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() == null) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (!(event.getEntity() instanceof Arrow)) return;
        Player player = (Player) event.getEntity().getShooter();
        if (Main.getInstance().playerTrails.containsKey(player.getUniqueId()))
            Main.getInstance().playerTrails.get(player.getUniqueId()).addArrow((Arrow) event.getEntity());
    }
}
