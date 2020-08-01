package net.miraclepvp.kitpvp.objects;

import com.earth2me.essentials.Essentials;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.utils.PingUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

import static net.miraclepvp.kitpvp.bukkit.Text.color;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getServer;

public class Tablist {

    public static void sendTab(Player player) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;

        IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + color("\n&5&lMiraclePvP\n&7     1.8.9 is recommended\n") + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try{
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, headerJSON);
            headerField.setAccessible(!headerField.isAccessible());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Double[] aTPS = {20.00};
        final Integer[] ping = {0};
        new BukkitRunnable(){
            @Override
            public void run() {
                aTPS[0] = Math.round(Essentials.getPlugin(Essentials.class).getTimer().getAverageTPS() * 100.00) / 100.00;
                ping[0] = PingUtil.pingPlayer(player);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 600L);

        new BukkitRunnable(){
            @Override
            public void run() {
                IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + color("\n&7  Players: &5" + getOnlinePlayers().size() + "&7 - TPS: &5" + aTPS[0] + "&7 - Ping: &5" + ping[0] + "ms&7  \n\n&7play.miraclepvp.net\n") + "\"}");
                try {
                    Field footerField = packet.getClass().getDeclaredField("b");
                    footerField.setAccessible(true);
                    footerField.set(packet, footerJSON);
                    footerField.setAccessible(!footerField.isAccessible());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                connection.sendPacket(packet);
            }
        }.runTaskTimer(Main.getInstance(), 1L, 100L);

    }
}
