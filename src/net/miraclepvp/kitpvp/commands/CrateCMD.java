package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CrateCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("miraclepvp.crate"))) return true;
        if(args.length < 5){
            sender.sendMessage(color("&cPlease use /crate give <player> <gear/cosmetic> <common/miracle> <amount>"));
            return true;
        }
        try{
            Player target = Bukkit.getPlayerExact(args[1]);
            User user = Data.getUser(target);
            Integer amount = Integer.parseInt(args[4]);
            if(args[2].equalsIgnoreCase("gear")){
                if(args[3].equalsIgnoreCase("common")) {
                    user.setGearcommon(user.getGearcommon() + amount);
                    sender.sendMessage(color("&aYou gave " + target.getName() + " " + amount + " Common Gear crates."));
                    target.sendMessage(color("&aYou received " + amount + " Common Gear crates."));
                    return true;
                }
                if(args[3].equalsIgnoreCase("miracle")){
                    user.setGearmiracle(user.getGearmiracle() + amount);
                    sender.sendMessage(color("&aYou gave " + target.getName() + " " + amount + " Miracle Gear crates."));
                    target.sendMessage(color("&aYou received " + amount + " Miracle Gear crates."));
                    return true;
                }
            }
            if(args[2].equalsIgnoreCase("cosmetic")){
                if(args[3].equalsIgnoreCase("common")) {
                    user.setCosmeticcommon(user.getCosmeticcommon() + amount);
                    sender.sendMessage(color("&aYou gave " + target.getName() + " " + amount + " Common Cosmetic crates."));
                    target.sendMessage(color("&aYou received " + amount + " Common Cosmetic crates."));
                    return true;
                }
                if(args[3].equalsIgnoreCase("miracle")){
                    user.setCosmeticmiracle(user.getCosmeticmiracle() + amount);
                    sender.sendMessage(color("&aYou gave " + target.getName() + " " + amount + " Miracle Cosmetic crates."));
                    target.sendMessage(color("&aYou received " + amount + " Miracle Cosmetic crates."));
                    return true;
                }
            }
        }catch (Exception ex) {
        }
        sender.sendMessage(color("&cPlease use /crate give <player> <gear/cosmetic> <common/miracle> <amount>"));
        return true;
    }
}
