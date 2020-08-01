package net.miraclepvp.kitpvp.listeners.custom;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.listeners.player.playerDamageRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private Player player;
    private User user;
    private playerDamageRegister.DamageProfile profile;

    public PlayerDeathEvent(Player player, playerDamageRegister.DamageProfile profile){
        this.player = player;
        this.user = Data.getUser(player);
        this.profile = profile;
    }

    public Player getPlayer() {
        return player;
    }

    public User getUser() {
        return user;
    }

    public playerDamageRegister.DamageProfile getProfile() {
        return profile;
    }
}
