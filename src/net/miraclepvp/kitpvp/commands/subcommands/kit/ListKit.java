package net.miraclepvp.kitpvp.commands.subcommands.kit;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListKit implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fKit");
        ChatCenterUtil.sendCenteredMessage(player, "&7A list of all kits.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        Data.kits.forEach(kit -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + kit.getName() + "&8: &7" + kit.getUuid()));
        });
        if(Data.kits.isEmpty())
            player.sendMessage(Text.color("&cNo kits added yet"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
