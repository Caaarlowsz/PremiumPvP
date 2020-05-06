package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.listeners.custom.PlayerNickEvent;
import net.miraclepvp.kitpvp.listeners.custom.PlayerStatusChangeEvent;
import net.miraclepvp.kitpvp.objects.NickManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerNick implements Listener {

    @EventHandler
    public void onNick(PlayerNickEvent event) {
        if(!(event.isCancelled())) {
            Player p = event.getPlayer();
            NickManager api = new NickManager(p);

            api.nickPlayer(event.getNickName(), event.getSkinName());

            p.sendMessage(color("&cYou are now disquised to " + event.getNickName() + ". Others won't see your own name and skin."));

            Bukkit.getPluginManager().callEvent(new PlayerStatusChangeEvent(p));
        }
    }
}
