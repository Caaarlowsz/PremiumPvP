package net.miraclepvp.kitpvp.commands.subcommands.chatcolor;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListChat implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fChatColor");
        ChatCenterUtil.sendCenteredMessage(player, "&7A list of all chatcolors.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        Data.chatcolors.forEach(chatcolor -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + chatcolor.getName() + "&8: &7" + chatcolor.getUuid()));
        });
        if(Data.chatcolors.isEmpty())
            player.sendMessage(Text.color("&cNo chatcolors added yet"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
