package net.miraclepvp.kitpvp.commands.subcommands.supplydrop;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpSupplydrop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fSupplydrop");
        ChatCenterUtil.sendCenteredMessage(player, "");
        player.sendMessage(color("&f/supplydrop addlocation <zone>"));
        player.sendMessage(color("&f/supplydrop removelocation <zone> <id>"));
        player.sendMessage(color("&f/supplydrop locationlist <zone>"));
        player.sendMessage(color("&f/supplydrop spawn <normal/vote> <random/here>"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
