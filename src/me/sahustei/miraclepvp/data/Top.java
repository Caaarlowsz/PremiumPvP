package me.sahustei.miraclepvp.data;

import me.sahustei.miraclepvp.data.user.StatType;
import me.sahustei.miraclepvp.data.user.User;

public class Top {

    public static void reload(){
        StatType.getStatTypes().forEach(statType -> {
            statType.getTop().clear();
            Data.users.stream()
                    .sorted((a1, a2) -> {
                        int val1 = statType.equals(StatType.TOKENS) ? a1.tokens :
                                statType.equals(StatType.EXPERIENCE) ? a1.experience :
                                        statType.equals(StatType.LEVEL) ? a1.level :
                                                statType.equals(StatType.COINS) ? a1.coins :
                                                        statType.equals(StatType.STREAK) ? a1.bestkillstreak :
                                                                statType.equals(StatType.DEATHS) ? a1.deaths :
                                                                        a1.kills;
                        int val2 = statType.equals(StatType.TOKENS) ? a2.tokens :
                                statType.equals(StatType.EXPERIENCE) ? a2.experience :
                                        statType.equals(StatType.LEVEL) ? a2.level :
                                                statType.equals(StatType.COINS) ? a2.coins :
                                                        statType.equals(StatType.STREAK) ? a2.bestkillstreak :
                                                                statType.equals(StatType.DEATHS) ? a2.deaths :
                                                                        a2.kills;
                        return val2 - val1;
                    }).limit(100).forEach(f ->
                    statType.getTop().add(f));
        });
    }

    public static User getTop(StatType statType, Integer position) {
        return statType.getTop().get(position-1);
    }
}
