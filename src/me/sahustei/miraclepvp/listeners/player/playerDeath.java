package me.sahustei.miraclepvp.listeners.player;

import me.sahustei.miraclepvp.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class playerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(Main.getInstance().combatTimer.get(event.getEntity()) == null) return;
        if(!(Main.getInstance().combatTimer.containsKey(event.getEntity()))) return;
        Main.getInstance().combatTimer.remove(event.getEntity());
    }
}
