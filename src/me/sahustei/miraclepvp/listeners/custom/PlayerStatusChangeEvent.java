package me.sahustei.miraclepvp.listeners.custom;

import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStatusChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;

    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public PlayerStatusChangeEvent(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public User getUser(){
        return Data.getUser(player);
    }
}
