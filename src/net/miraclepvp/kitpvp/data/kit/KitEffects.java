package net.miraclepvp.kitpvp.data.kit;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitEffects {

    public static PotionEffect deSerialize(String effectRaw){
        effectRaw = effectRaw.replaceAll("_", ".");
        String[] splittedEffectRaw = effectRaw.split(";");

//        if(splittedEffectRaw[0].equalsIgnoreCase("FIRE_RESISTANCE")){
//            return new PotionEffect(
//                    PotionEffectType.,
//                    Integer.valueOf(splittedEffectRaw[1]),
//                    Integer.valueOf(splittedEffectRaw[2])
//            );
//        } else {
            return new PotionEffect(
                    PotionEffectType.getById(Integer.valueOf(splittedEffectRaw[0])),
                    Integer.valueOf(splittedEffectRaw[1]),
                    Integer.valueOf(splittedEffectRaw[2])
            );
        //}
    }

    public static String serialize(PotionEffect effect){
        String text = "";
        text += effect.getType().getId() + ";";
        text += effect.getDuration() + ";";
        text += effect.getAmplifier() + ";";
        return text;
    }
}
