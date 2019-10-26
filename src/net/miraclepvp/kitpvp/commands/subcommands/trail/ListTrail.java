package net.miraclepvp.kitpvp.commands.subcommands.trail;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListTrail implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fTrail");
        ChatCenterUtil.sendCenteredMessage(player, "&7A list of all trails.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        Data.trails.forEach(trail -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + trail.getName() + "&8: &7" + trail.getUuid()));
        });
        if(Data.trails.isEmpty())
            player.sendMessage(Text.color("&cNo trails added yet"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
