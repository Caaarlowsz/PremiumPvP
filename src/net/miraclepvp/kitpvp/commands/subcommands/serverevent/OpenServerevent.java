package net.miraclepvp.kitpvp.commands.subcommands.serverevent;

import net.miraclepvp.kitpvp.objects.ServerEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class OpenServerevent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.serverevent")){
            sender.sendMessage(color("&cYou don't have the required permissions to do this."));
            return true;
        }
        if(ServerEvent.open){
            sender.sendMessage(color("&cThe event is already open."));
            return true;
        }
        ServerEvent.open();
        return true;
    }
}
