package net.miraclepvp.kitpvp.listeners.custom;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.listeners.player.pvp.playerDamageRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSpawnEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private Player player;

    public PlayerSpawnEvent(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public User getUser() {
        return Data.getUser(player);
    }
}
