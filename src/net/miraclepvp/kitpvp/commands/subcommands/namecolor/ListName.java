package net.miraclepvp.kitpvp.commands.subcommands.namecolor;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListName implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fNameColor");
        ChatCenterUtil.sendCenteredMessage(player, "&7A list of all namecolors.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        Data.namecolors.forEach(namecolor -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + namecolor.getName() + "&8: &7" + namecolor.getUuid()));
        });
        if(Data.namecolors.isEmpty())
            player.sendMessage(Text.color("&cNo namecolors added yet"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
