package net.miraclepvp.kitpvp.data.duel;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Map {

    public UUID
            uuid;
    public String
            name,
            description;
    public Material
            icon;
    public List<Arena>
            arenaList;

    public Map(String name){
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.description = "The " + name + " arena.";
        this.icon = Material.GRASS;
        this.arenaList = new ArrayList<>();
    }

    public void addArena(Arena arena){
        arenaList.add(arena);
    }

    public void removeArena(Integer number){
        arenaList.remove(getArena(number));
    }

    public Arena getArena(UUID uuid){
        return arenaList.stream().filter(a -> a.uuid.equals(uuid)).findFirst().get();
    }

    public Arena getArena(Integer number){
        number--;
        return arenaList.get(number);
    }

    public Integer getAvailableArenas(){
        return arenaList.stream().filter(a -> a.enabled).toArray().length;
    }

    public Arena getAvailableArena(){
        return arenaList.stream().filter(a -> a.enabled).findFirst().get();
    }
}
