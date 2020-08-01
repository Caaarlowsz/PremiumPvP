package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.crate.AddCrate;
import net.miraclepvp.kitpvp.commands.subcommands.crate.HelpCrate;
import net.miraclepvp.kitpvp.commands.subcommands.crate.RemoveCrate;
import net.miraclepvp.kitpvp.commands.subcommands.crate.SetCrate;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CrateCMD implements CommandExecutor {

    private HelpCrate helpCrate = new HelpCrate();
    private SetCrate setCrate = new SetCrate();
    private AddCrate addCrate = new AddCrate();
    private RemoveCrate removeCrate = new RemoveCrate();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("miraclepvp.crate"))) return true;
        if(args.length == 0){
            helpCrate.onCommand(sender, cmd, label, args);
            return true;
        }
        switch(args[0].toLowerCase()){
            case "add":
                addCrate.onCommand(sender, cmd, label, args);
                break;
            case "remove":
                removeCrate.onCommand(sender, cmd, label, args);
                break;
            case "set":
                setCrate.onCommand(sender, cmd, label, args);
                break;
            default:
                helpCrate.onCommand(sender, cmd, label, args);
                break;
        }
        return true;
    }
}
