package net.miraclepvp.kitpvp.listeners.player.movement;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.objects.Board;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class playerTeleport implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && !event.getPlayer().hasMetadata("kit"))
            event.setCancelled(true); //Cancel enderpearl TP when player has no kit

        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE))
            event.setCancelled(true); //Disable the spectator teleport inventory
    }

    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent event){
        new BukkitRunnable(){
            @Override
            public void run() {
                Board.updatePlayerListName(event.getPlayer());
            }
        }.runTaskLater(Main.getInstance(), 1l);
    }
}
