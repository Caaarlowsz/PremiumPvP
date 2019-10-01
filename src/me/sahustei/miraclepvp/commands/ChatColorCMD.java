package me.sahustei.miraclepvp.commands;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.commands.subcommands.chatcolor.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatColorCMD implements CommandExecutor {

    HelpChat helpColor = new HelpChat();
    AddChat addColor = new AddChat();
    DeleteChat deleteColor = new DeleteChat();
    GiveChat giveColor = new GiveChat();
    RenameChat renameColor = new RenameChat();
    PriceChat priceColor = new PriceChat();
    SellpriceChat sellpriceColor = new SellpriceChat();
    IconChat iconColor = new IconChat();
    ListChat listColor = new ListChat();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.cosmetic.chatcolor")){
            sender.sendMessage(Text.color("&4You don't have enough permissions to do this!"));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Text.color("&cPlease use /chatcolor help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                helpColor.onCommand(sender, cmd, label, args);
                break;
            case "add":
                addColor.onCommand(sender, cmd, label, args);
                break;
            case "delete":
                deleteColor.onCommand(sender, cmd, label, args);
                break;
            case "give":
                giveColor.onCommand(sender, cmd, label, args);
                break;
            case "rename":
                renameColor.onCommand(sender, cmd, label, args);
                break;
            case "price":
                priceColor.onCommand(sender, cmd, label, args);
                break;
            case "sellprice":
                sellpriceColor.onCommand(sender, cmd, label, args);
                break;
            case "icon":
                iconColor.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listColor.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&cPlease use /chatcolor help for more information."));
                break;
        }
        return true;
    }
}
