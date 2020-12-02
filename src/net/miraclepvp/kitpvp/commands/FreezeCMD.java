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

public class FreezeCMD implements CommandExecutor {

    public static List<UUID> frozenList = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!(sender.hasPermission("miraclepvp.freeze"))){
            sender.sendMessage(color("&4You don't have enough permissions to do this!"));
            return true;
        }
        if(args.length != 1){
            sender.sendMessage(color("&cPlease use /freeze <target>."));
            return true;
        }
        try{
            Player target = Bukkit.getPlayerExact(args[0]);

            if(target.hasPermission("miraclepvp.freeze.bypass")){
                sender.sendMessage(color("&cThe given player can not be frozen."));
                return true;
            }

            if(target.getName().equalsIgnoreCase(sender.getName())){
                sender.sendMessage(color("&cYou can not freeze yourself!"));
                return true;
            }

            if(frozenList.contains(target.getUniqueId())){
                frozenList.remove(target.getUniqueId());
                sender.sendMessage(color("&7You've unfrozen &c" + target.getName() + "&7!"));
                target.sendMessage(color("&7You are unfrozen by &c" + sender.getName() + "&7, thanks for your cooperation!"));
                return true;
            }
            frozenList.add(target.getUniqueId());
            sender.sendMessage(color("&7You've frozen &c" + target.getName() + "&7!"));
            target.sendMessage(color("&7You got frozen by &c" + sender.getName() + "&7."));

            new BukkitRunnable(){
                @Override
                public void run() {
                    if(!frozenList.contains(target.getUniqueId())) {
                        cancel();
                        return;
                    }
                    target.sendMessage(color("&8&m------------------------------------"));
                    target.sendMessage(color("&8&l  (&4&lWARNING&8&l)        (&4&lWARNING&8&l)"));
                    target.sendMessage(color(""));
                    target.sendMessage(color("&c&lYou're currently frozen!"));
                    target.sendMessage(color("&cJoin our discord:&f https://discord.miraclepvp.net/"));
                    target.sendMessage(color(""));
                    target.sendMessage(color("&8&l  (&4&lWARNING&8&l)        (&4&lWARNING&8&l)"));
                    target.sendMessage(color("&8&m------------------------------------"));
                }
            }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 600L);

            Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(target));
            return true;
        }catch (Exception ex){
            sender.sendMessage(color("&cThe given player is not online."));
            return true;
        }
    }
}
