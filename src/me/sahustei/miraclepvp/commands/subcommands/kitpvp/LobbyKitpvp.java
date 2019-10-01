package me.sahustei.miraclepvp.commands.subcommands.kitpvp;

import me.sahustei.miraclepvp.listeners.player.playerJoin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class LobbyKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length != 2) {
            if (!(sender.hasPermission("miraclepvp.kitpvp.lobby"))) {
                sender.sendMessage(color("&4You don't have enough permissions to do this!"));
                return true;
            }
            playerJoin.handleSpawn(player);
            return true;
        }
        try{
            Player target = Bukkit.getPlayerExact(args[1]);
            playerJoin.handleSpawn(target);
            target.sendMessage(color("&a" + player.getName() + " forced you to the spawn."));
            player.sendMessage(color("&aYou forced " + target.getName() + " to the spawn."));
            return true;
        } catch (NullPointerException ex){
            player.sendMessage(color("&cThe given player is not online."));
            return true;
        }
    }
}
