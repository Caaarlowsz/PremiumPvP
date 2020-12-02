package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.listeners.custom.PlayerSpawnEvent;
import net.miraclepvp.kitpvp.listeners.player.movement.playerJoin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class SpawnCMD implements CommandExecutor {

    public static List<UUID> teleporting = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = ((Player)sender);
        if(Main.getInstance().combatTimer.get(player) != null) {
            player.sendMessage(color("&cYou have to be out of combat to execute this command."));
            return true;
        }

        player.sendMessage(color("&6Teleporting in 5 seconds.. Don't move!"));
        teleporting.add(player.getUniqueId());

        new BukkitRunnable(){
            @Override
            public void run() {
                if(teleporting.contains(player.getUniqueId())) {
                    teleporting.remove(player.getUniqueId());
                    if (Main.getInstance().combatTimer.get(player) != null) {
                        player.sendMessage(color("&cTeleportation cancelled because you are in combat."));
                        return;
                    }
                    Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(player));
                }
            }
        }.runTaskLater(Main.getInstance(), 100L);
        return true;
    }
}
