package me.sahustei.miraclepvp.commands.subcommands.prefix;

import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.prefix.Prefix;
import me.sahustei.miraclepvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class InfoPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(color("&cPlease use /prefix info <name>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[1]) == null) {
                sender.sendMessage(color("&cThere is no prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        //Prefix prefix = Data.getPrefix(args[1]);
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(sender, "&f" + Data.getPrefix(args[1]).getName());
        ChatCenterUtil.sendCenteredMessage(sender, "&7" + Data.getPrefix(args[1]).getUuid());
        sender.sendMessage(color("&f"));
        sender.sendMessage(color("&fName: &7" + Data.getPrefix(args[1]).getName()));
        sender.sendMessage(color("&fPrefix: &7" + Data.getPrefix(args[1]).getPrefix()));
        sender.sendMessage(color("&fWeight: &7" + Data.getPrefix(args[1]).getWeight()));
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        return true;
    }
}
