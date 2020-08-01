package net.miraclepvp.kitpvp.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class playerTeleport implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && !event.getPlayer().hasMetadata("kit")) {
            event.setCancelled(true);
            //TODO Enderpearl van vorige leven kan nog tp'en als je optijd respawned. Mss leven-id's gebruiken?
        }
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE))
            event.setCancelled(true);
    }
}
