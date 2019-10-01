package me.sahustei.miraclepvp.listeners.player;

import me.sahustei.miraclepvp.Main;
import me.sahustei.miraclepvp.objects.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class playerDrop implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(!Main.getInstance().buildMode.contains(event.getPlayer()) || event.getPlayer().hasMetadata("vanished")) {
            event.setCancelled(true);
            return;
        }
        Item.types.forEach(item -> {
            if(item.getItem().equals(event.getItemDrop().getItemStack()))
                event.setCancelled(true);
        });
    }
}
