package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.objects.Board;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)) return;
        if(event.getDamager().hasMetadata("vanished")){
            event.getDamager().sendMessage(color("&cYou are still vanished, unvanish before getting into a battle!"));
            event.setCancelled(true);
            return;
        }
        if(event.getEntity().hasMetadata("vanished")){
            event.getDamager().sendMessage(color("&cThis player is vanished."));
            event.setCancelled(true);
            return;
        }
        if(event.getDamager().hasMetadata("build")){
            event.getDamager().sendMessage(color("&cYou are still in buildmode, turn buildmode off before getting into a battle!"));
            event.setCancelled(true);
            return;
        }
        if(event.getEntity().hasMetadata("build")){
            event.getDamager().sendMessage(color("&cThis player is in buildmode."));
            event.setCancelled(true);
            return;
        }
        if(event.isCancelled()) return;
        if(event.getDamage() <= 0) return;
        Main.getInstance().combatTimer.put((Player)event.getDamager(), 10);
        if(!(event.getEntity() instanceof Player)) return;
        Main.getInstance().combatTimer.put((Player)event.getEntity(), 10);
    }
}
