package net.miraclepvp.kitpvp.data.kit;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitEffects {

    public static PotionEffect deSerialize(String effectRaw){
        effectRaw = effectRaw.replaceAll("_", ".");
        String[] splittedEffectRaw = effectRaw.split(";");
        return new PotionEffect(
                PotionEffectType.getByName(splittedEffectRaw[0]),
                Integer.valueOf(splittedEffectRaw[1]),
                Integer.valueOf(splittedEffectRaw[2])
        );
    }

    public static String serialize(PotionEffect effect){
        String text = "";
        text += effect.getType().getName() + ";";
        text += effect.getDuration() + ";";
        text += effect.getAmplifier() + ";";
        return text;
    }
}
