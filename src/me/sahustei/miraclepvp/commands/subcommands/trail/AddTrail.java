package me.sahustei.miraclepvp.commands.subcommands.trail;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.trail.Trail;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class AddTrail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 5){
            sender.sendMessage(Text.color("&cPlease use /trail add <name> <particle> <icon> <price>"));
            return true;
        }
        EnumParticle enumParticle;
        Integer price;
        try{
            enumParticle = EnumParticle.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException ex){
            sender.sendMessage(Text.color("&cThe given particle does not exist."));
            return true;
        }
        try{
            price = Integer.parseInt(args[4]);
        } catch (IllegalArgumentException ex){
            sender.sendMessage(Text.color("&cThe given price is not a valid amount."));
            return true;
        }
        try {
            if (Data.getTrail(args[1]) != null) {
                sender.sendMessage(Text.color("&cThere is already a trail with this name."));
                return true;
            } else  {
                if (Material.getMaterial(args[3].toUpperCase()) != null) {
                    Material icon = Material.getMaterial(args[3].toUpperCase());
                    if (!icon.equals(Material.AIR)) {
                        Trail trail = new Trail(args[1], enumParticle, icon, price, price/2);
                        Data.trails.add(trail);
                        sender.sendMessage(Text.color("&aYou have succesfully added the trail " + trail.getName() + "!"));
                        return true;
                    } else {
                        sender.sendMessage(Text.color("&cThe given material does not exist."));
                        return true;
                    }
                } else {
                    sender.sendMessage(Text.color("&cThe given material does not exist."));
                    return true;
                }
            }
        } catch(NoSuchElementException ex){
            if (Material.getMaterial(args[3].toUpperCase()) != null) {
                Material icon = Material.getMaterial(args[3].toUpperCase());
                if (!icon.equals(Material.AIR)) {
                    Trail trail = new Trail(args[1], enumParticle, icon, price, price/2);
                    Data.trails.add(trail);
                    sender.sendMessage(Text.color("&aYou have succesfully added the trail " + trail.getName() + "!"));
                    return true;
                } else {
                    sender.sendMessage(Text.color("&cThe given material does not exist."));
                    return true;
                }
            } else {
                sender.sendMessage(Text.color("&cThe given material does not exist."));
                return true;
            }
        }
    }
}
