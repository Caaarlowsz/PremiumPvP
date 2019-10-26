package net.miraclepvp.kitpvp.commands.subcommands.trail;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.trail.Trail;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class DeleteTrail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(Text.color("&cPlease use /trail delete <name>"));
            return true;
        }
        try {
            if (Data.getTrail(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no trail with this name."));
                return true;
            } else {
                Trail trail = Data.getTrail(args[1]);
                sender.sendMessage(Text.color("&aYou have succesfully deleted the trail " + trail.getName() + "!"));
                Data.trails.remove(trail);
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no trail with this name."));
            return true;
        }
    }
}
