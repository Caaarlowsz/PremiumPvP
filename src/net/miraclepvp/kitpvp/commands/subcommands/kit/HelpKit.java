package net.miraclepvp.kitpvp.commands.subcommands.kit;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fKit");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit create <name> <icon> <price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit delete <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit rename <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit price <name> <price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit sellprice <name> <price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit edit <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit addeffect <name> <effect> <duration> <level>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit removeeffect <name> <effect>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit list");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit give <player> <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kit toggle <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
