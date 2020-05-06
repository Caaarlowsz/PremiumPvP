package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.listeners.custom.PlayerNickEvent;
import net.miraclepvp.kitpvp.listeners.custom.PlayerUnnickEvent;
import net.miraclepvp.kitpvp.objects.NickManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class NickCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(color("&cOnly players are allowed to execute this command."));
            return true;
        }
        if(!(sender.hasPermission("miraclepvp.donator.nick"))){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if(((Player) sender).hasMetadata("kit")) {
            sender.sendMessage(color("&cYou can only change your nickname at spawn."));
            return true;
        }

        if(NickManager.nickedPlayers.contains(((Player) sender).getUniqueId())){
            Bukkit.getPluginManager().callEvent(new PlayerUnnickEvent(((Player) sender)));
            return true;
        }

        if(args.length == 0){
            String name = NickManager.nickNames.get((new Random().nextInt(NickManager.nickNames.size())));
            boolean nickNameIsInUse = false;

            for (String nickName : NickManager.playerNicknames.values()) {
                if(nickName.toUpperCase().equalsIgnoreCase(name.toUpperCase()))
                    nickNameIsInUse = true;
            }

            while (nickNameIsInUse) {
                nickNameIsInUse = false;
                name = NickManager.nickNames.get((new Random().nextInt(NickManager.nickNames.size())));

                for (String nickName : NickManager.playerNicknames.values()) {
                    if(nickName.toUpperCase().equalsIgnoreCase(name.toUpperCase()))
                        nickNameIsInUse = true;
                }
            }

            Bukkit.getPluginManager().callEvent(new PlayerNickEvent(((Player) sender), name, name, null));
            return true;
        }

        try{
            Data.getUser(Bukkit.getOfflinePlayer(args[0])).getLastVersion();

            sender.sendMessage(color("&cYou are not allowed to nick to this player."));
            return true;
        }catch(Exception ex){
            Bukkit.getPluginManager().callEvent(new PlayerNickEvent(((Player) sender), args[0], args[0], null));
            return true;
        }
    }
}
