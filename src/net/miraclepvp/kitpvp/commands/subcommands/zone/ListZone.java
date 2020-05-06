package net.miraclepvp.kitpvp.commands.subcommands.zone;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListZone implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fZone");
        ChatCenterUtil.sendCenteredMessage(player, "&7A list of all zones.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        Data.zones.forEach(zone -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + zone.getName() + "&8: &7" + zone.getUuid()));
        });
        if(Data.zones.isEmpty())
            player.sendMessage(Text.color("&cNo zones added yet"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
