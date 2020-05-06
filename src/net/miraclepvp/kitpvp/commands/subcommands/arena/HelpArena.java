package net.miraclepvp.kitpvp.commands.subcommands.arena;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpArena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(sender, "&fArena");
        ChatCenterUtil.sendCenteredMessage(sender, "&7Manage the arenas in the server.");
        ChatCenterUtil.sendCenteredMessage(sender, "");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/arena create <map>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/arena remove <map> <number>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/arena list <map>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/arena pos1 <map> <number>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/arena pos2 <map> <number>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/arena toggle <map> <number>");
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        return true;
    }
}
