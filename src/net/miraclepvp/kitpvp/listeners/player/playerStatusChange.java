package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.listeners.custom.PlayerStatusChangeEvent;
import net.miraclepvp.kitpvp.objects.Board;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class playerStatusChange implements Listener {

    @EventHandler
    public void onChange(PlayerStatusChangeEvent event){
        Board.showScoreboard(event.getPlayer());
    }
}
