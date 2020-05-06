package net.miraclepvp.kitpvp.commands.subcommands.duel;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpDuel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fDuel");
        ChatCenterUtil.sendCenteredMessage(player, "&7Get help with our Duel system.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/duel invite <player>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/duel spectate <player>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/duel accept <player>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/duel decline <player>");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }

}
