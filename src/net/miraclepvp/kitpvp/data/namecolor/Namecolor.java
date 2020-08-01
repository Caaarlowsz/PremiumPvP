package net.miraclepvp.kitpvp.data.namecolor;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.UUID;

public class Namecolor {

    private UUID uuid;
    private String name;
    private ChatColor color;
    private Integer sell,cost;
    private Material icon;

    public Namecolor(String name, ChatColor color, Material icon, int cost, int sell) {
        this.uuid = UUID.randomUUID();
        this.icon = icon;
        this.name = name;
        this.color = color;
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

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }
}
