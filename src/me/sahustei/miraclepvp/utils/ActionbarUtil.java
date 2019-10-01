package me.sahustei.miraclepvp.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionbarUtil {

    private PacketPlayOutChat packet;

    public ActionbarUtil(String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer
                .a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}"), (byte) 2);
        this.packet = packet;
    }

    public void sendToPlayer(Player p) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(this.packet);
    }
}
