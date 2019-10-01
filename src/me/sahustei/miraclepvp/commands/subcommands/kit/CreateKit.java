package me.sahustei.miraclepvp.commands.subcommands.kit;

import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.kit.Kit;
import me.sahustei.miraclepvp.data.namecolor.Namecolor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class CreateKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 4) {
            sender.sendMessage(color("&cPlease use /kit create <name> <icon> <price>"));
            return true;
        }
        Integer price;
        try {
            price = Integer.parseInt(args[3]);
        } catch (IllegalArgumentException ex) {
            sender.sendMessage(color("&cThe given price is not a valid amount."));
            return true;
        }
        try {
            if (Data.getKit(args[1]) != null) {
                sender.sendMessage(color("&cThere is already a kit with this name."));
                return true;
            } else {
                if (Material.getMaterial(args[2].toUpperCase()) != null) {
                    Material icon = Material.getMaterial(args[3].toUpperCase());
                    if (!icon.equals(Material.AIR)) {
                        List<ItemStack> itemStackArrayList = new ArrayList();
                        itemStackArrayList.clear();
                        PlayerInventory inv = ((Player)sender).getInventory();
                        ItemStack[] items = inv.getContents();
                        ItemStack[] armor = inv.getArmorContents();
                        Kit kit = new Kit(args[1], icon, price, items, armor);
                        Data.kits.add(kit);
                        sender.sendMessage(color("&aYou have succesfully created the kit " + kit.getName() + "!"));
                        return true;
                    } else {
                        sender.sendMessage(color("&cThe given material does not exist."));
                        return true;
                    }
                } else {
                    sender.sendMessage(color("&cThe given material does not exist."));
                    return true;
                }
            }
        } catch (NoSuchElementException ex) {
            if (Material.getMaterial(args[2].toUpperCase()) != null) {
                Material icon = Material.getMaterial(args[2].toUpperCase());
                if (!icon.equals(Material.AIR)) {
                    List<ItemStack> itemStackArrayList = new ArrayList();
                    itemStackArrayList.clear();
                    PlayerInventory inv = ((Player)sender).getInventory();
                    ItemStack[] items = inv.getContents();
                    ItemStack[] armor = inv.getArmorContents();
                    Kit kit = new Kit(args[1], icon, price, items, armor);
                    Data.kits.add(kit);
                    sender.sendMessage(color("&aYou have succesfully created the kit " + kit.getName() + "!"));
                    return true;
                } else {
                    sender.sendMessage(color("&cThe given material does not exist."));
                    return true;
                }
            } else {
                sender.sendMessage(color("&cThe given material does not exist."));
                return true;
            }
        }
    }
}
