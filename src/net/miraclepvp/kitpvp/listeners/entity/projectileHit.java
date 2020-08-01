package net.miraclepvp.kitpvp.listeners.entity;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class projectileHit implements Listener {

    @EventHandler
    public void hitEvent(ProjectileHitEvent e){
        if (e.getEntity() instanceof Arrow) {
            Arrow a = (Arrow) e.getEntity();
            a.remove();
        }
    }
}
