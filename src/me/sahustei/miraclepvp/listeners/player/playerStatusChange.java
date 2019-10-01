package me.sahustei.miraclepvp.listeners.player;

import me.sahustei.miraclepvp.listeners.custom.PlayerStatusChangeEvent;
import me.sahustei.miraclepvp.objects.Board;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class playerStatusChange implements Listener {

    @EventHandler
    public void onChange(PlayerStatusChangeEvent event){
        Board.showScoreboard(event.getPlayer());
    }
}
