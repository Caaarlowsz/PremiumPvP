package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!event.getEntity().hasMetadata("kit") && !event.getEntity().hasMetadata("event")) {
            event.setCancelled(true);
            return;
        }
        if(event.getDamager() instanceof Player) {
            if (event.getDamager().hasMetadata("vanished")) {
                event.getDamager().sendMessage(color("&cYou are still vanished, unvanish before getting into a battle!"));
                event.setCancelled(true);
                return;
            }
            if (event.getEntity().hasMetadata("vanished")) {
                event.getDamager().sendMessage(color("&cThis player is vanished."));
                event.setCancelled(true);
                return;
            }
            if (event.getDamager().hasMetadata("build")) {
                event.getDamager().sendMessage(color("&cYou are still in buildmode, turn buildmode off before getting into a battle!"));
                event.setCancelled(true);
                return;
            }
            if (event.getEntity().hasMetadata("build")) {
                event.getDamager().sendMessage(color("&cThis player is in buildmode."));
                event.setCancelled(true);
                return;
            }
            if (!event.getDamager().hasMetadata("kit") && !event.getDamager().hasMetadata("event")){
                event.setCancelled(true);
                return;
            }
        }

        if (event.isCancelled()) return;
    }
}
