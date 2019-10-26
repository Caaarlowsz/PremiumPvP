package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.commands.subcommands.trail.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TrailCMD implements CommandExecutor {

    HelpTrail helpTrail = new HelpTrail();
    AddTrail addTrail = new AddTrail();
    DeleteTrail deleteTrail = new DeleteTrail();
    RenameTrail renameTrail = new RenameTrail();
    PriceTrail priceTrail = new PriceTrail();
    SellpriceTrail sellpriceTrail = new SellpriceTrail();
    GiveTrail giveTrail = new GiveTrail();
    IconTrail iconTrail = new IconTrail();
    ListTrail listTrail = new ListTrail();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.cosmetic.trail")){
            sender.sendMessage(Text.color("&4You don't have enough permissions to do this!"));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Text.color("&cPlease use /trail help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                this.helpTrail.onCommand(sender, cmd, label, args);
                break;
            case "add":
                this.addTrail.onCommand(sender, cmd, label, args);
                break;
            case "delete":
                this.deleteTrail.onCommand(sender, cmd, label, args);
                break;
            case "rename":
                this.renameTrail.onCommand(sender, cmd, label, args);
                break;
            case "price":
                this.priceTrail.onCommand(sender, cmd, label, args);
                break;
            case "sellprice":
                this.sellpriceTrail.onCommand(sender, cmd, label, args);
                break;
            case "give":
                this.giveTrail.onCommand(sender, cmd, label, args);
                break;
            case "icon":
                this.iconTrail.onCommand(sender, cmd, label, args);
                break;
            case "list":
                this.listTrail.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&cPlease use /trail help for more information."));
                break;
        }
        return true;
    }
}
