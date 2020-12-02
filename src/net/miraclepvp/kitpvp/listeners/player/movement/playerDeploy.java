package net.miraclepvp.kitpvp.listeners.player.movement;

import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.listeners.custom.PlayerDeployEvent;
import net.miraclepvp.kitpvp.listeners.custom.PlayerSpawnEvent;
import net.miraclepvp.kitpvp.objects.Board;
import net.miraclepvp.kitpvp.objects.hasKit;
import net.miraclepvp.kitpvp.utils.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerDeploy implements Listener {

    @EventHandler
    public void onDeploy(PlayerDeployEvent event){
        Player player = event.getPlayer();
        User user = event.getUser();
        if(Duel.invites.containsKey(player.getUniqueId())){
            player.sendMessage(color("&cYou can't deploy while your invite is still open."));
            return;
        }
        TeleportUtil.getTeleport(player);
        if(user.giveKit(user.getPreviousKit(), event.getCustomLayout(), event.getSpawnAbilities())){
            player.setMetadata("kit", new hasKit());
            player.setAllowFlight(false);
            player.setFlying(false);
        }else {
            player.sendMessage(color("&cCouldn't set your kit, something went wrong."));
            Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(player));
        }
    }
}
