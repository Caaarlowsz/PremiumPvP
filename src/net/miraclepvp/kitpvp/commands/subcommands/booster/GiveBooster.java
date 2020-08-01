package net.miraclepvp.kitpvp.commands.subcommands.booster;

import com.google.gson.internal.$Gson$Types;
import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.Booster;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class GiveBooster implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 5) {
            sender.sendMessage(color("&cPlease use /booster give <player> <coins/experience> <personal/network> <percentage>"));
            return true;
        }
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player == null) return true;
            User target = Data.getUser(player);
            if (target == null) return true;
            try {
                Booster.BoosterType type = Booster.BoosterType.valueOf(args[2].toUpperCase());
                Integer value = Integer.parseInt(args[4]);
                Boolean personal;
                if(args[3].equalsIgnoreCase("personal"))
                    personal = true;
                else if(args[3].equalsIgnoreCase("network"))
                    personal = false;
                else {
                    sender.sendMessage(color("&cPlease use /booster give <player> <coins/experience> <personal/network> <percentage>"));
                    return true;
                }
                Booster booster = new Booster(type, value, personal);
                target.getBoosterList().add(Booster.serialize(booster));
                sender.sendMessage(color("&aYou have given " + player.getName() + " a " + booster.value + "% " + booster.boosterType.toString() + " " + (personal ? "Personal" : "Network") + " Booster"));
                return true;
            } catch (Exception ex) {
                sender.sendMessage(color("&cSomething went wrong, check your command."));
                return true;
            }
        } catch (NoSuchElementException ex) {
            sender.sendMessage(color("&cThis player doesn't exist."));
            return true;
        }
    }
}
