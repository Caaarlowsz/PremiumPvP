package net.miraclepvp.kitpvp.data.zone;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Zone {

    public UUID
            uuid;
    public String
            name;
    public List<String>
            supplydropLocations;

    public Zone(String name){
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.supplydropLocations = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSupplydropLocations() {
        return supplydropLocations;
    }
}
