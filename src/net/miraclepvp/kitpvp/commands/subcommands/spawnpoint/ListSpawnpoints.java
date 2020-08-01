package net.miraclepvp.kitpvp.commands.subcommands.spawnpoint;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.miraclepvp.kitpvp.data.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListSpawnpoints implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fSpawnpoints"));
        player.sendMessage(color("&7A list of all spawnpoints."));
        player.sendMessage(color("&7Click on a location to teleport."));
        player.sendMessage(color(""));
        Config.getSpawnpoints().forEach(spawnpoint -> {
            pos++;
            IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                    .a(color("{text: \"&5" + pos + ". &f" + (spawnpoint == null ? "world not found" : spawnpoint.getWorld().getName()) + ", " + (Math.round(spawnpoint.getX() * 100.00) / 100.00) + ", " + (Math.round(spawnpoint.getY() * 100.00) / 100.00) + ", " + (Math.round(spawnpoint.getZ() * 100.00) / 100.00) + "\", clickEvent: {\"action\": \"run_command\" , value: \"/minecraft:tp " + player.getName() + " " + spawnpoint.getX() + " " + spawnpoint.getY() + " " + spawnpoint.getZ() + "\"}}"));
            PacketPlayOutChat ppoc = new PacketPlayOutChat(comp);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
        });

        if(Config.getSpawnpoints().isEmpty())
            player.sendMessage(color("&cNo spawnpoints added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
