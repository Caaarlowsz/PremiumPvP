package net.miraclepvp.kitpvp.objects;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.PingUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

import static net.miraclepvp.kitpvp.Placeholders.replaceLine;
import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Tablist {

    private static boolean enabled = true;
    private static String
            top = "\n&5&lExampleServer\n&7     1.8.9 is recommended\n",
            bottom = "\n&7  Players: &5%server_players% &7 - TPS: &5%server_tps% &7 - Ping: &5%player_ping% ms&7  \n\n&7play.ExampleServer.net\n";

    public static void load() {
        FileManager.load(Main.getInstance(), "scoreboard.yml");
        FileConfiguration config = FileManager.get("scoreboard.yml");
        if (!config.getBoolean("tablist.enbabled")) return;
        top = config.getString("tablist.top");
        bottom = config.getString("tablist.bottom");
        Bukkit.getOnlinePlayers().forEach(player -> sendTab(player));
    }

    public static void sendTab(Player player) {
        if(!enabled) return;

        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;

        IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + color(replaceLine(top, Data.getUser(player))) + "\"}");
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
                aTPS[0] = (double)Math.round(MinecraftServer.getServer().recentTps[0]*100.0)/100.0;
                ping[0] = PingUtil.pingPlayer(player);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 600L);

        new BukkitRunnable(){
            @Override
            public void run() {
                IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + color(replaceLine(bottom, Data.getUser(player))) + "\"}");
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
