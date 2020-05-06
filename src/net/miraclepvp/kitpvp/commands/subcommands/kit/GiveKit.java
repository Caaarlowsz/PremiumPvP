package net.miraclepvp.kitpvp.commands.subcommands.kit;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class GiveKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /kit give <player> <name>"));
            return true;
        }
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player == null) return true;
            User target = Data.getUser(player);
            if (target == null) return true;
            if (Data.getKit(args[2]) == null) {
                sender.sendMessage(Text.color("&cThere is no kit with this name."));
                return true;
            }
            try {
                Kit kit = Data.getKit(args[2]);
                if (target.getKitsList().contains(kit.getUuid())) {
                    sender.sendMessage(Text.color("&cThis player already has this kit!"));
                    return true;
                }
                sender.sendMessage(Text.color("&aYou have succesfully given " + player.getName() + " the kit " + kit.getName() + "!"));
                target.getKitsList().add(kit.getUuid());
                return true;
            } catch (NoSuchElementException ex) {
                sender.sendMessage(Text.color("&cThere is no kit with this name."));
                return true;
            }
        } catch (NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
