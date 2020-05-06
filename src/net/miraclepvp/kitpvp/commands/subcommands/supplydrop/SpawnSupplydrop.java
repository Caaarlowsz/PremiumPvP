package net.miraclepvp.kitpvp.commands.subcommands.supplydrop;

import net.miraclepvp.kitpvp.objects.Supplydrop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class SpawnSupplydrop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("miraclepvp.supplydrop.spawn")) {
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if(args.length != 2){
            sender.sendMessage(color("&cPlease use /supplydrop spawn <normal/vote>"));
            return true;
        }

        switch(args[1].toLowerCase()){
            case "normal":
                Supplydrop.Crate normalcrate = new Supplydrop.Crate(Supplydrop.DropType.NORMAL);
                normalcrate.spawn();
                break;
            case "vote":
                Supplydrop.Crate votecrate = new Supplydrop.Crate(Supplydrop.DropType.VOTE);
                votecrate.spawn();
                break;
            default:
                sender.sendMessage(color("&cPlease use /supplydrop spawn <normal/vote>"));
                break;
        }
        return true;
    }
}
