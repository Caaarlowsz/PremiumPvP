package net.miraclepvp.kitpvp.data.user;

import net.miraclepvp.kitpvp.data.Top;

import java.util.ArrayList;

public enum StatType {

    KILLS("Kills"),
    DEATHS("Deaths"),
    STREAK("Streak"),
    LEVEL("Level")
    ;

    private static ArrayList<StatType> statTypes = new ArrayList<>();

    private String name;
    private ArrayList<User> top;

    StatType(String name){
        this.name = name;
        this.top = new ArrayList<>();
    }

    public static void load(){
        statTypes.add(KILLS);
        statTypes.add(DEATHS);
        statTypes.add(STREAK);
        statTypes.add(LEVEL);

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
