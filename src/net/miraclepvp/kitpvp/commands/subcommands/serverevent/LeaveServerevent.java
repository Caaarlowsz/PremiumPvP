package net.miraclepvp.kitpvp.commands.subcommands.serverevent;

import net.miraclepvp.kitpvp.objects.ServerEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class LeaveServerevent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!(ServerEvent.players.contains(((Player) sender).getUniqueId()))){
            sender.sendMessage(color("&cYou are not in the event."));
            return true;
        }
        ServerEvent.leave(((Player) sender), true);
        return true;
    }
}
