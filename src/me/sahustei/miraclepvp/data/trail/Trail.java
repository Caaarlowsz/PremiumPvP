package me.sahustei.miraclepvp.data.trail;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;

import java.util.UUID;

public class Trail {

    private UUID uuid;
    private Integer cost,sell;
    private String name;
    private EnumParticle particle;
    private Material icon;

    public Trail(String name, EnumParticle particle, Material icon, int cost, int sell) {
        this.uuid = UUID.randomUUID();
        this.icon = icon;
        this.name = name;
        this.particle = particle;
        this.cost = cost;
        this.sell = sell;
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

    public Material getIcon()
    {
        return this.icon;
    }

    public void setIcon(Material icon)
    {
        this.icon = icon;
    }

    public int getCost()
    {
        return this.cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public int getSell()
    {
        return this.sell;
    }

    public void setSell(int sell)
    {
        this.sell = sell;
    }

    public EnumParticle getParticle() {
        return particle;
    }

    public void setParticle(EnumParticle particle) {
        this.particle = particle;
    }

}
