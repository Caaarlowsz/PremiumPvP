package me.sahustei.miraclepvp.commands.subcommands.tokens;

import me.sahustei.miraclepvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpTokens implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fCosmoTokens");
        ChatCenterUtil.sendCenteredMessage(player, "&7Tokens to buy cosmetics.");
        ChatCenterUtil.sendCenteredMessage(player, "&7Aliases: tokens - cosmo - ct.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/cosmotokens add <player> <amount>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/cosmotokens set <player> <amount>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/cosmotokens remove <player> <amount>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/cosmotokens balance");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
