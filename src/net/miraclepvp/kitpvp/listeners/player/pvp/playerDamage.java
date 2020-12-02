package net.miraclepvp.kitpvp.listeners.player.pvp;

import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!event.getEntity().hasMetadata("kit") && !event.getEntity().hasMetadata("event")) {
            event.setCancelled(true);
            return;
        }
        if (event.getDamager().hasMetadata("vanished")) {
            event.getDamager().sendMessage(color("&cYou are still vanished, disable vanish before getting into a battle!"));
            event.setCancelled(true);
            return;
        }
        if (event.getEntity().hasMetadata("vanished")) {
            event.getDamager().sendMessage(color("&cThis player is vanished."));
            event.setCancelled(true);
            return;
        }
        if (event.getDamager().hasMetadata("build")) {
            event.getDamager().sendMessage(color("&cYou are still in build-mode, turn build-mode off before getting into a battle!"));
            event.setCancelled(true);
            return;
        }
        if (event.getEntity().hasMetadata("build")) {
            event.getDamager().sendMessage(color("&cThis player is in build-mode."));
            event.setCancelled(true);
            return;
        }
        if (!event.getDamager().hasMetadata("kit") && !event.getDamager().hasMetadata("event")) {
            event.setCancelled(true);
            return;
        }

        Guild guild = Data.getGuild(Data.getUser(((Player) event.getDamager())).getGuild());
        Guild guildTwo = Data.getGuild(Data.getUser(((Player) event.getEntity())).getGuild());
        if(guild.getUuid().equals(guildTwo.getUuid()) && !guild.getFriendlyfire() && Config.getToggleFriendlyFire()){
            event.getDamager().sendMessage(color("&cYour guild has friendlyfire disabled."));
            event.setCancelled(true);
        }
    }
}
