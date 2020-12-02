package net.miraclepvp.kitpvp.commands.subcommands.anvil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpAnvil implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fAnvil"));
        player.sendMessage(color("&7Manage the working anvils."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/anvil add"));
        player.sendMessage(color("&f/anvil remove [id]"));
        player.sendMessage(color("&f/anvil list"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
