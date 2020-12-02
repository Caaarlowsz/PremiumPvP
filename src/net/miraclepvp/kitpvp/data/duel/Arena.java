package net.miraclepvp.kitpvp.data.duel;

import net.miraclepvp.kitpvp.bukkit.FileManager;
import org.bukkit.*;

import java.util.UUID;

public class Arena {

    public UUID
            uuid;
    public String
            locationA,
            locationB;
    public Boolean
            enabled;

    public Arena(){
        this.uuid = UUID.randomUUID();
        this.locationA = null;
        this.locationB = null;
        this.enabled = false;
    }

    public void setLocationA(Location location){
        locationA = FileManager.serialize(location);
    }

    public void setLocationB(Location location){
        locationB = FileManager.serialize(location);
    }

    public Location getLocationA(){
        return FileManager.deSerialize(locationA);
    }

    public Location getLocationB(){
        return FileManager.deSerialize(locationB);
    }
}
