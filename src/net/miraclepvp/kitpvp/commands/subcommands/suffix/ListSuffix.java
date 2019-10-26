package net.miraclepvp.kitpvp.commands.subcommands.suffix;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListSuffix implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fSuffix");
        ChatCenterUtil.sendCenteredMessage(player, "&7A list of all suffixes.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        Data.suffixes.forEach(suffix -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + suffix.getName() + "&8: &7" + suffix.getUuid()));
        });
        if(Data.suffixes.isEmpty())
            player.sendMessage(Text.color("&cNo suffixes added yet"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
