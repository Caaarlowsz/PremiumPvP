package net.miraclepvp.kitpvp.commands.subcommands.coins;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCoins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fCoins");
        ChatCenterUtil.sendCenteredMessage(player, "&7Coins to buy kits.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/coins add <player> <amount>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/coins set <player> <amount>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/coins remove <player> <amount>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/coins balance");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
