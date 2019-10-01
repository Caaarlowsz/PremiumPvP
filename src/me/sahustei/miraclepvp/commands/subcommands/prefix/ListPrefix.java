package me.sahustei.miraclepvp.commands.subcommands.prefix;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListPrefix implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fPrefix");
        ChatCenterUtil.sendCenteredMessage(player, "&7A list of all prefixes.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        Data.prefixes.forEach(prefix -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + prefix.getName() + "&8: &7" + prefix.getUuid()));
        });
        if(Data.prefixes.isEmpty())
            player.sendMessage(Text.color("&cNo prefixes added yet"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
