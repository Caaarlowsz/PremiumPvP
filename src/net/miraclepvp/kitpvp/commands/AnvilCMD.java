package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.anvil.AddAnvil;
import net.miraclepvp.kitpvp.commands.subcommands.anvil.HelpAnvil;
import net.miraclepvp.kitpvp.commands.subcommands.anvil.ListAnvil;
import net.miraclepvp.kitpvp.commands.subcommands.anvil.RemoveAnvil;
import net.miraclepvp.kitpvp.commands.subcommands.arena.ListArena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AnvilCMD implements CommandExecutor {

    private HelpAnvil helpAnvil = new HelpAnvil();
    private AddAnvil addAnvil = new AddAnvil();
    private RemoveAnvil removeAnvil = new RemoveAnvil();
    private ListAnvil listAnvil = new ListAnvil();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!(sender.hasPermission("miraclepvp.anvil"))){
            sender.sendMessage(color("&cYou don't have the required permissions to do this."));
            return true;
        }
        if(args.length == 0){
            helpAnvil.onCommand(sender, cmd, label, args);
            return true;
        }
        switch(args[0].toLowerCase()){
            case "add":
                addAnvil.onCommand(sender, cmd, label, args);
                break;
            case "remove":
                removeAnvil.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listAnvil.onCommand(sender, cmd, label, args);
                break;
            default:
                helpAnvil.onCommand(sender, cmd, label, args);
                break;
        }
        return true;
    }
}
