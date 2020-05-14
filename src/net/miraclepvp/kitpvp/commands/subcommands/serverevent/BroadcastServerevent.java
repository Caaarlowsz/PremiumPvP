package net.miraclepvp.kitpvp.commands.subcommands.serverevent;

import net.miraclepvp.kitpvp.objects.ServerEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class BroadcastServerevent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.serverevent")){
            sender.sendMessage(color("&cYou don't have the required permissions to do this."));
            return true;
        }

        String message = args[1];
        for(int i=2;i<args.length;i++)message+=" "+args[i];
        ServerEvent.sendBroadcast(message);
        return true;
    }
}
