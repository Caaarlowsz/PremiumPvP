package net.miraclepvp.kitpvp.bukkit.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class NMSUtilities {

    public static void sendPacket(Player player, Object packet) {
        try {
            if (packet == null) return;
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object connection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            connection.getClass().getMethod("sendPacket", NMS.Packet).invoke(connection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public static void sendTitle(Player player, String title, String subtitle, int fadein, int stay, int fadeout) {
        title = Text.color(title);
        subtitle = Text.color(subtitle);

        try {
            Object enumTitle = NMS.PacketPlayOutTitle.getDeclaredClasses()[0].getField("TITLE").get(null);
            Constructor<?> constructorTitle = NMS.PacketPlayOutTitle.getDeclaredConstructor(NMS.PacketPlayOutTitle.getDeclaredClasses()[0], NMS.ChatComponent, int.class, int.class, int.class);
            Object chatTitle = NMS.ChatSerializer.getMethod("a", String.class).invoke(NMS.ChatSerializer, "{\"text\": \"" + title + "\"}");
            Object packet = constructorTitle.newInstance(enumTitle, chatTitle, fadein, stay, fadeout);
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            Object enumSubtitle = NMS.PacketPlayOutTitle.getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            Constructor<?> constructorSubtitle = NMS.PacketPlayOutTitle.getDeclaredConstructor(NMS.PacketPlayOutTitle.getDeclaredClasses()[0], NMS.ChatComponent, int.class, int.class, int.class);
            Object chatSubtitle = NMS.ChatSerializer.getMethod("a", String.class).invoke(NMS.ChatSerializer, "{\"text\": \"" + subtitle + "\"}");
            Object packet = constructorSubtitle.newInstance(enumSubtitle, chatSubtitle, fadein, stay, fadeout);
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

    public static void sendActionBar(Player player, String message) {
        message = color(message);

        try {
            Constructor<?> ConstructorActionbar = NMS.PacketPlayOutChat.getDeclaredConstructor(NMS.ChatComponent, byte.class);
            Object actionbar = NMS.ChatSerializer.getMethod("a", String.class).invoke(NMS.ChatSerializer, "{\"text\": \"" + message + "\"}");
            Object packet = ConstructorActionbar.newInstance(actionbar, (byte) 2);
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
