package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.commands.FreezeCMD;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.FreezeGUI;
import net.miraclepvp.kitpvp.listeners.custom.PlayerUnnickEvent;
import net.miraclepvp.kitpvp.objects.NickManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerLeave implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if(FreezeCMD.frozenList.contains(event.getPlayer().getUniqueId())){
            FreezeCMD.frozenList.remove(event.getPlayer().getUniqueId());
            Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("miraclepvp.freeze.alert")).forEach(player -> player.sendMessage(color("&6" + event.getPlayer().getName() + " left the server while frozen!")));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tempban " + event.getPlayer().getName() + " 30d -s Unfair Advantage | Disconnected while frozen");
        }
        if(!(Main.getInstance().combatTimer.containsKey(event.getPlayer()))) return;
        if(Main.getInstance().combatTimer.get(event.getPlayer()) <= 0) return;
        if(event.getPlayer().hasMetadata("event"))
            event.getPlayer().performCommand("serverevent leave");
        playerJoin.handleSpawn(event.getPlayer());
        event.getPlayer().setHealth(0);
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&l!&8] &c" + event.getPlayer().getName() + "&7 left the server while in combat!"));
    }
}
