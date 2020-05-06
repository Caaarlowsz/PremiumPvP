package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.inventories.FreezeGUI;
import net.miraclepvp.kitpvp.listeners.player.playerJoin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                sender.sendMessage(color("&aYou've unfrozen " + target.getName() + "!"));

                target.closeInventory();
                target.sendMessage(color("&4You are unfrozen, thanks for your cooperation!"));
                return true;
            }
            frozenList.add(target.getUniqueId());
            sender.sendMessage(color("&aYou've frozen " + target.getName() + "!"));

            playerJoin.handleSpawn(target);
            target.sendMessage(color("&4You got frozen by staff. Do not log out and join the support in the Discord."));
            target.openInventory(FreezeGUI.getInventory());
            return true;
        }catch (Exception ex){
            sender.sendMessage(color("&cThe given player is not online."));
            return true;
        }
    }
}
