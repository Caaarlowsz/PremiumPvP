package me.sahustei.miraclepvp.commands.subcommands.kit;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class ToggleKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(Text.color("&cPlease use /kit toggle <name>"));
            return true;
        }
        try {
            if (Data.getKit(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no kit with this name."));
                return true;
            } else {
                Kit kit = Data.getKit(args[1]);
                kit.setEnabled(!kit.isEnabled());
                sender.sendMessage(Text.color("&aYou have succesfully " + (kit.isEnabled() ? "enabled" : "disabled") + " the kit " + kit.getName() + "!"));
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no kit with this name."));
            return true;
        }
    }
}
