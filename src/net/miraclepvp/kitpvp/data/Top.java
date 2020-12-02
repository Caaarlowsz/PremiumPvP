package net.miraclepvp.kitpvp.data;

import net.miraclepvp.kitpvp.data.user.StatType;
import net.miraclepvp.kitpvp.data.user.User;

public class Top {

    public static void reload(){
        StatType.getStatTypes().forEach(statType -> {
            statType.getTop().clear();
            Data.users.stream()
                    .sorted((a1, a2) -> {
                        int val1 = statType.equals(StatType.LEVEL) ? a1.level :
                                statType.equals(StatType.STREAK) ? a1.bestkillstreak :
                                        statType.equals(StatType.DEATHS) ? a1.deaths : a1.kills;
                        int val2 = statType.equals(StatType.LEVEL) ? a2.level :
                                statType.equals(StatType.STREAK) ? a2.bestkillstreak :
                                        statType.equals(StatType.DEATHS) ? a2.deaths : a2.kills;
                        return val2 - val1;
                    }).limit(27).forEach(f ->
                    statType.getTop().add(f));
        });
    }

    public static User getTop(StatType statType, Integer position) {
        return statType.getTop().get(position-1);
    }
}
