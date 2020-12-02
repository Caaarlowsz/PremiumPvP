package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.commands.subcommands.namecolor.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NameColorCMD implements CommandExecutor {

    HelpName helpName = new HelpName();
    AddName addName = new AddName();
    DeleteName deleteName = new DeleteName();
    GiveName giveName = new GiveName();
    RenameName renameName = new RenameName();
    PriceName priceName = new PriceName();
    SellpriceName sellpriceName = new SellpriceName();
    IconName iconName = new IconName();
    ListName listName = new ListName();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.cosmetic.namecolor")){
            sender.sendMessage(Text.color("&4You don't have enough permissions to do this!"));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Text.color("&cPlease use /namecolor help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                helpName.onCommand(sender, cmd, label, args);
                break;
            case "add":
                addName.onCommand(sender, cmd, label, args);
                break;
            case "delete":
                deleteName.onCommand(sender, cmd, label, args);
                break;
            case "give":
                giveName.onCommand(sender, cmd, label, args);
                break;
            case "rename":
                renameName.onCommand(sender, cmd, label, args);
                break;
            case "price":
                priceName.onCommand(sender, cmd, label, args);
                break;
            case "sellprice":
                sellpriceName.onCommand(sender, cmd, label, args);
                break;
            case "icon":
                iconName.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listName.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&cPlease use /namecolor help for more information."));
                break;
        }
        return true;
    }
}
