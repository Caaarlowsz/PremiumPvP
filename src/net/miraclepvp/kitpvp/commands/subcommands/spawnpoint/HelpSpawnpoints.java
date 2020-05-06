package net.miraclepvp.kitpvp.commands.subcommands.spawnpoint;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpSpawnpoints implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fSpawnpoints");
        ChatCenterUtil.sendCenteredMessage(player, "&7Manage the spawnpoints in the map.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/spawnpoints add");
        ChatCenterUtil.sendCenteredMessage(player, "&f/spawnpoints delete <id>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/spawnpoints list");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
