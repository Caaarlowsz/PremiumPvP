package net.miraclepvp.kitpvp.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PlayerNickEvent extends Event implements Cancellable {

    private static HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private Player p;
    private String nickName, skinName;
    private UUID prefix;

    public PlayerNickEvent(Player p, String nickName, String skinName, UUID prefix) {
        this.p = p;

        this.nickName = nickName;
        this.skinName = skinName;

        this.prefix = prefix;
    }

    public Player getPlayer() {
        return p;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public UUID getPrefix() {
        return prefix;
    }

    public void setPrefix(UUID prefix) {
        this.prefix = prefix;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
