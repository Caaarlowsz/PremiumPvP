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

public class RemoveEffectKit implements CommandExecutor {

    private PotionEffect potion = null;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /kit removeeffect <name> <effect>"));
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
                    PotionEffectType effectType = PotionEffectType.getByName(args[2]);
                    kit.getEffects().forEach(effect -> {
                        if(KitEffects.deSerialize(effect).getType().equals(effectType))
                            potion = KitEffects.deSerialize(effect);
                    });
                    if(!types.contains(effectType)){
                        sender.sendMessage(color("&cThis kit doesn't have this effect."));
                        return true;
                    }
                    kit.removeEffect(potion);
                    sender.sendMessage(color("&aYou've removed the " + potion.getType().getName() + " effect from the " + kit.getName() + " kit."));
                    return true;
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
