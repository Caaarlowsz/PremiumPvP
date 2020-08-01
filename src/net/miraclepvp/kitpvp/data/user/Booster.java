package net.miraclepvp.kitpvp.data.user;

import net.miraclepvp.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Booster {

    public static List<ActiveBooster> activeBoosters = new ArrayList();
    public static HashMap<UUID, ActiveBooster> activePersonalBoosters = new HashMap<>();
    public static Integer
            coinBoost = 0,
            experienceBoost = 0;

    public enum BoosterType{

        COINS("Coins"),
        EXPERIENCE("Experience");

        private String name;

        BoosterType(String name){
            this.name = name;
        }
    }

    public BoosterType boosterType;
    public Integer value;
    public Boolean personal;

    public Booster(BoosterType boosterType, Integer value, Boolean personal){
        this.boosterType = boosterType;
        this.value = value;
        this.personal = personal;
    }

    public static String serialize(Booster booster) {
        String text = "";
        text += booster.boosterType.toString() + ";";
        text += booster.value.toString() + ";";
        text += booster.personal.toString() + ";";
        return text;
    }

    public static String serialize(BoosterType type, Integer value, Boolean personal) {
        String text = "";
        text += type.toString() + ";";
        text += value.toString() + ";";
        text += personal.toString() + ";";
        return text;
    }

    public static Booster deSerialize(String boosterRaw) {
        boosterRaw = boosterRaw.replaceAll("_", ".");
        String[] splittedBoosterRaw = boosterRaw.split(";");
        return new Booster(
                BoosterType.valueOf(splittedBoosterRaw[0]),
                Integer.valueOf(splittedBoosterRaw[1]),
                Boolean.valueOf(splittedBoosterRaw[2])
        );
    }

    public static class ActiveBooster{

        public UUID
                uuid,
                owner;
        public BoosterType boosterType;
        public Boolean personal;
        public Integer
                percentage,
                timeInSeconds;
        public Date momentOfStart;

        public ActiveBooster(UUID uuid, BoosterType boosterType, Boolean personal, Integer percentage, Integer timeInSeconds){
            this.uuid = UUID.randomUUID();
            this.owner = uuid;
            this.boosterType = boosterType;
            this.percentage = percentage;
            this.personal = personal;
            Date date = new Date();
            this.momentOfStart = date;
            this.timeInSeconds = timeInSeconds;

            if(personal)
                activePersonalBoosters.put(uuid, this);
            else {
                activeBoosters.add(this);

                if (boosterType.equals(BoosterType.COINS))
                    coinBoost += percentage;
                if (boosterType.equals(BoosterType.EXPERIENCE))
                    experienceBoost += percentage;
            }

            forceRunnable();
        }

        public void forceRunnable(){
            new BukkitRunnable(){
                @Override
                public void run() {
                    delete();
                }
            }.runTaskLater(Main.getInstance(), timeInSeconds*20);
        }

        public void delete(){
            if(personal) {
                try {
                    activePersonalBoosters.entrySet().stream().forEach(uuidActiveBoosterEntry -> {
                        UUID playerID = uuidActiveBoosterEntry.getKey();
                        if (activePersonalBoosters.get(playerID).uuid.equals(this.uuid)) {
                            activePersonalBoosters.remove(playerID);
                            if (Bukkit.getOfflinePlayer(playerID).isOnline())
                                Bukkit.getPlayer(playerID).sendMessage(color("&cYour personal booster has expired."));
                        }
                    });
                }catch (ConcurrentModificationException ex){

                }
            } else {
                if (boosterType.equals(BoosterType.COINS))
                    coinBoost = (coinBoost-percentage);
                if (boosterType.equals(BoosterType.EXPERIENCE))
                    experienceBoost = (experienceBoost-percentage);
                activeBoosters.remove(this);
            }
        }
    }
}
