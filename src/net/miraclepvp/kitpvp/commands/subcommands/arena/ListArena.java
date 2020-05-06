package net.miraclepvp.kitpvp.commands.subcommands.arena;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListArena implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&cYou have to be a player to do this."));
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(color("&cPlease use /arena list <map>"));
            return true;
        }
        try {
            ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
            ChatCenterUtil.sendCenteredMessage(sender, "&fArena");
            ChatCenterUtil.sendCenteredMessage(sender, "&7A list of all arenas of the map " + Data.getMap(args[1]).name + ".");
            ChatCenterUtil.sendCenteredMessage(sender, "");
            Data.getMap(args[1]).arenaList.forEach(arena -> {
                pos++;
                sender.sendMessage(color("&5" + pos + ": " + (arena.enabled ? "&aEnabled" : "&cDisabled")));
            });
            if (Data.getMap(args[1]).arenaList.isEmpty())
                sender.sendMessage(color("&cNo arenas added yet"));
            ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
            pos = 0;
            return true;
        }catch (NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no map with this name."));
            return true;
        }
    }
}
