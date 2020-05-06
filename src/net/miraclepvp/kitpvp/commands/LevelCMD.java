package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class LevelCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (player == null) {
                sender.sendMessage(Text.color("&cThis player doesn't exist."));
                return true;
            }
            User target = Data.getUser(player);
            try {
                Integer value = Integer.parseInt(args[1])-1;
                target.setLevel(value);
                Integer calc = 1 + value;
                Integer calc2 = 25 * value;
                Integer finalcalc = calc2 * calc;
                target.setExperience(finalcalc, false);
                sender.sendMessage(Text.color("&aYou have set " + player.getName() + "'s level to " + target.getLevel() + "."));
                return true;
            } catch (Exception ex) {
                sender.sendMessage(Text.color("&cSomething went wrong, check your command."));
                return true;
            }
        } catch (NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
            return true;
        }
    }
}
