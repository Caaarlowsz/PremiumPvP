package me.sahustei.miraclepvp.data.user;

import io.netty.channel.local.LocalEventLoopGroup;
import me.sahustei.miraclepvp.data.Top;

import java.util.ArrayList;

public enum StatType {

    TOKENS("Tokens"),
    KILLS("Kills"),
    DEATHS("Deaths"),
    STREAK("Streak"),
    KD("K/D Ratio"),
    COINS("Coins"),
    LEVEL("Level"),
    EXPERIENCE("Experience")
    ;

    private static ArrayList<StatType> statTypes = new ArrayList<>();

    private String name;
    private ArrayList<User> top;

    StatType(String name){
        this.name = name;
        this.top = new ArrayList<>();
    }

    public static void load(){
        statTypes.add(TOKENS);
        statTypes.add(KILLS);
        statTypes.add(DEATHS);
        statTypes.add(STREAK);
        statTypes.add(KD);
        statTypes.add(COINS);
        statTypes.add(LEVEL);
        statTypes.add(EXPERIENCE);

        Top.reload();
    }

    public static ArrayList<StatType> getStatTypes() {
        return statTypes;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getTop() {
        return top;
    }
}
