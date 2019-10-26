package net.miraclepvp.kitpvp.objects;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.utils.PingUtil;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Tablist {

    public static void sendTab(Player player) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;

        new BukkitRunnable(){
            @Override
            public void run() {
                double lag = Math.round((1.0D - Lag.getTPS() / 20.0D) * 100.0D);
                IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + color("\n&5&lMiraclePvP\n&7     1.8.9 is recommended\n") + "\"}");
                IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + color("\n&7  Players: &5" + Bukkit.getOnlinePlayers().size() + "&7 - Lag: &5" + lag + "%&7 - Ping: &5" + PingUtil.pingPlayer(player) + "ms&7  \n\n&7play.miraclepvp.net\n") + "\"}");
                PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
                try {
                    Field headerField = packet.getClass().getDeclaredField("a");
                    headerField.setAccessible(true);
                    headerField.set(packet, headerJSON);
                    headerField.setAccessible(!headerField.isAccessible());

                    Field footerField = packet.getClass().getDeclaredField("b");
                    footerField.setAccessible(true);
                    footerField.set(packet, footerJSON);
                    footerField.setAccessible(!footerField.isAccessible());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                connection.sendPacket(packet);
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }
}
