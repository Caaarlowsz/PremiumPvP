package me.sahustei.miraclepvp.data.prefix;

import java.util.UUID;

public class Prefix {

    private UUID uuid;
    private String name,prefix;
    private Integer weight;

    public Prefix(String name, String prefix) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.prefix = prefix;
        this.weight = 999;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
