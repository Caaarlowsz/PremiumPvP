package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.commands.subcommands.suffix.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SuffixCMD implements CommandExecutor {

    HelpSuffix helpSuffix = new HelpSuffix();
    AddSuffix addSuffix = new AddSuffix();
    DeleteSuffix deleteSuffix = new DeleteSuffix();
    GiveSuffix giveSuffix = new GiveSuffix();
    RenameSuffix renameSuffix = new RenameSuffix();
    PriceSuffix priceSuffix = new PriceSuffix();
    SellpriceSuffix sellpriceSuffix = new SellpriceSuffix();
    IconSuffix iconSuffix = new IconSuffix();
    ListSuffix listSuffix = new ListSuffix();
    RemoveSuffix removeSuffix = new RemoveSuffix();
    BuyableSuffix buyableSuffix = new BuyableSuffix();
    SetSuffix setSuffix = new SetSuffix();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.cosmetic.suffix")){
            sender.sendMessage(Text.color("&4You don't have enough permissions to do this!"));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Text.color("&cPlease use /suffix help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                helpSuffix.onCommand(sender, cmd, label, args);
                break;
            case "add":
                addSuffix.onCommand(sender, cmd, label, args);
                break;
            case "delete":
                deleteSuffix.onCommand(sender, cmd, label, args);
                break;
            case "give":
                giveSuffix.onCommand(sender, cmd, label, args);
                break;
            case "rename":
                renameSuffix.onCommand(sender, cmd, label, args);
                break;
            case "price":
                priceSuffix.onCommand(sender, cmd, label, args);
                break;
            case "sellprice":
                sellpriceSuffix.onCommand(sender, cmd, label, args);
                break;
            case "icon":
                iconSuffix.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listSuffix.onCommand(sender, cmd, label, args);
                break;
            case "remove":
                removeSuffix.onCommand(sender, cmd, label, args);
                break;
            case "buyable":
                buyableSuffix.onCommand(sender, cmd, label, args);
                break;
            case "setsuffix":
                setSuffix.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&cPlease use /suffix help for more information."));
                break;
        }
        return true;
    }
}
