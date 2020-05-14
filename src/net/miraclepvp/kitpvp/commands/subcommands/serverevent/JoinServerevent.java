package net.miraclepvp.kitpvp.commands.subcommands.serverevent;

import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.objects.ServerEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class JoinServerevent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!(ServerEvent.started && ServerEvent.open)){
            sender.sendMessage(color("&cThe serverevent is not open for players at the moment."));
            return true;
        }
        if(Duel.isIngame(((Player) sender)) || Duel.isSpectator(((Player) sender)) || ((Player) sender).hasMetadata("kit") || ((Player) sender).hasMetadata("event")) {
            sender.sendMessage(color("&cYou can only use this command at spawn."));
            return true;
        }
        if(ServerEvent.players.contains(((Player) sender).getUniqueId())){
            sender.sendMessage(color("&cYou are already in the event."));
            return true;
        }
        ServerEvent.join(((Player) sender));
        return true;
    }
}
