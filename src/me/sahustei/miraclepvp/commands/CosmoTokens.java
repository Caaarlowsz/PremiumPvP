package me.sahustei.miraclepvp.commands;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.commands.subcommands.tokens.*;
import me.sahustei.miraclepvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmoTokens implements CommandExecutor {

    AddTokens addTokens = new AddTokens();
    HelpTokens helpTokens = new HelpTokens();
    SetTokens setTokens = new SetTokens();
    RemoveTokens removeTokens = new RemoveTokens();
    BalanceTokens balanceTokens = new BalanceTokens();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.cosmetic.cosmotokens")){
            sender.sendMessage(Text.color("&aYou have " + Data.getUser(((Player) sender)).getTokens() + " tokens."));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Text.color("&cPlease use /cosmotokens help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                this.helpTokens.onCommand(sender, cmd, label, args);
                break;
            case "add":
                this.addTokens.onCommand(sender, cmd, label, args);
                break;
            case "set":
                this.setTokens.onCommand(sender, cmd, label, args);
                break;
            case "remove":
                this.removeTokens.onCommand(sender, cmd, label, args);
                break;
            case "balance":
                this.balanceTokens.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&cPlease use /cosmotokens help for more information."));
                break;
        }
        return true;
    }
}
