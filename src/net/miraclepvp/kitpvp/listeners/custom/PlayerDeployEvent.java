package net.miraclepvp.kitpvp.listeners.custom;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDeployEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private Player player;
    private Boolean customLayout, spawnAbilities;

    public PlayerDeployEvent(Player player, Boolean customLayout, Boolean spawnAbilities){
        this.player = player;
        this.customLayout = customLayout;
        this.spawnAbilities = spawnAbilities;
    }

    public Player getPlayer() {
        return player;
    }

    public Boolean getCustomLayout() {
        return customLayout;
    }

    public Boolean getSpawnAbilities() {
        return spawnAbilities;
    }

    public User getUser() {
        return Data.getUser(player);
    }
}
