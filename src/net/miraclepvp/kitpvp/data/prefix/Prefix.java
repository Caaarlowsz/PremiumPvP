package net.miraclepvp.kitpvp.data.prefix;

import java.util.UUID;

public class Prefix {

    private UUID uuid;
    private String name,prefix;
    private String weight;

    public Prefix(String name, String prefix) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.prefix = prefix;
        this.weight = "8";
    }

    public UUID getUuid()
    {
        return this.uuid;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
