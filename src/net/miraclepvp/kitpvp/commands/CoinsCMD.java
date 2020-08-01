package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.commands.subcommands.coins.*;
import net.miraclepvp.kitpvp.commands.subcommands.tokens.*;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCMD implements CommandExecutor {

    private HelpCoins helpCoins = new HelpCoins();
    private AddCoins addCoins = new AddCoins();
    private SetCoins setCoins = new SetCoins();
    private RemoveCoins removeCoins = new RemoveCoins();
    private BalanceCoins balanceCoins = new BalanceCoins();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.coins")){
            sender.sendMessage(Text.color("&aYou have " + Data.getUser(((Player) sender)).getCoins() + " coins."));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Text.color("&aYou have " + Data.getUser(((Player) sender)).getCoins() + " coins."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                this.helpCoins.onCommand(sender, cmd, label, args);
                break;
            case "add":
                this.addCoins.onCommand(sender, cmd, label, args);
                break;
            case "set":
                this.setCoins.onCommand(sender, cmd, label, args);
                break;
            case "remove":
                this.removeCoins.onCommand(sender, cmd, label, args);
                break;
            case "balance":
                this.balanceCoins.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&aYou have " + Data.getUser(((Player) sender)).getCoins() + " coins."));
                break;
        }
        return true;
    }
}
