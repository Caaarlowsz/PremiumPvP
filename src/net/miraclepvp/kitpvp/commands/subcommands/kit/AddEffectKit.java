package net.miraclepvp.kitpvp.commands.subcommands.kit;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.kit.KitEffects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AddEffectKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 5) {
            sender.sendMessage(color("&cPlease use /kit addeffect <name> <effect> <duration> <level>"));
            return true;
        }
        try {
            if (Data.getKit(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no kit with this name."));
                return true;
            } else {
                Kit kit = Data.getKit(args[1]);
                ArrayList<PotionEffectType> types = new ArrayList<>();
                kit.getEffects().forEach(effect -> types.add(KitEffects.deSerialize(effect).getType()));
                try {
                    PotionEffectType effectType = PotionEffectType.getByName(args[2].toUpperCase());
                    if(types.contains(effectType)){
                        sender.sendMessage(color("&cThis kit already has this effect."));
                        return true;
                    }
                    try {
                        Integer duration = Integer.parseInt(args[3])*20;
                        try {
                            Integer level = Integer.parseInt(args[4])-1;
                            try {
                                PotionEffect effect = new PotionEffect(effectType, duration, level);
                                kit.addEffect(effect);
                                sender.sendMessage(color("&aYou've added the " + effectType.getName() + " effect on the " + kit.getName() + " kit."));
                                return true;
                            } catch (Exception ex) {
                                sender.sendMessage(color("&cSomething whent wrong creating the effect. Check your arguments."));
                                return true;
                            }
                        } catch (NumberFormatException ex) {
                            sender.sendMessage(color("&cThe given level is not a valid number."));
                            return true;
                        }
                    } catch (NumberFormatException ex) {
                        if(args[3].equalsIgnoreCase("i")){
                            try {
                                Integer level = Integer.parseInt(args[4])-1;
                                try {
                                    PotionEffect effect = new PotionEffect(effectType, 999999, level);
                                    kit.addEffect(effect);
                                    sender.sendMessage(color("&aYou've added the " + effectType.getName() + " effect on the " + kit.getName() + " kit."));
                                    return true;
                                } catch (Exception e) {
                                    sender.sendMessage(color("&cSomething whent wrong creating the effect. Check your arguments."));
                                    return true;
                                }
                            } catch (NullPointerException e) {
                                sender.sendMessage(color("&cThe given level is not a valid number."));
                                return true;
                            }
                        }
                        sender.sendMessage(color("&cThe given duration is not a valid number or infinite (i)."));
                        return true;
                    }
                } catch (NoSuchElementException ex) {
                    sender.sendMessage(color("&cThis effect type is not valid."));
                    return true;
                }
            }
        } catch (NoSuchElementException ex) {
            sender.sendMessage(Text.color("&cThere is no kit with this name."));
            return true;
        }
    }
}
