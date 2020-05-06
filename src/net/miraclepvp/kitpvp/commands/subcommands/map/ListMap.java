package net.miraclepvp.kitpvp.commands.subcommands.map;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListMap implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(sender, "&fMap");
        ChatCenterUtil.sendCenteredMessage(sender, "&7A list of all maps.");
        ChatCenterUtil.sendCenteredMessage(sender, "");
        Data.maps.forEach(map -> {
            pos++;
            sender.sendMessage(color("&5" + pos + ". &f" + map.name + "&8: &7" + map.uuid));
        });
        if(Data.maps.isEmpty())
            sender.sendMessage(color("&cNo maps added yet"));
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
