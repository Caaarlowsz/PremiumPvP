package me.sahustei.miraclepvp.commands.subcommands.trail;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.trail.Trail;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class RenameTrail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Text.color("&cPlease use /trail rename <name> <new_name>"));
            return true;
        }
        try {
            if (Data.getTrail(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no trail with this name."));
                return true;
            } else {
                Trail trail = Data.getTrail(args[1]);
                sender.sendMessage(Text.color("&aYou have succesfully renamed the trail " + trail.getName() + "!"));
                trail.setName(args[2]);
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no trail with this name."));
            return true;
        }
    }
}
