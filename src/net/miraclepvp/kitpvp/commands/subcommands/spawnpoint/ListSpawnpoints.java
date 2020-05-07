package net.miraclepvp.kitpvp.commands.subcommands.spawnpoint;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.chat.BaseComponentSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
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
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fSpawnpoints");
        ChatCenterUtil.sendCenteredMessage(player, "&7A list of all spawnpoints.");
        ChatCenterUtil.sendCenteredMessage(player, "&7Click on a location to teleport.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        Config.getSpawnpoints().forEach(spawnpoint -> {
            pos++;
            IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                    .a(color("{text: \"&5" + pos + ". &f" + (spawnpoint == null ? "world not found" : spawnpoint.getWorld().getName()) + ", " + (Math.round(spawnpoint.getX() * 100.00) / 100.00) + ", " + (Math.round(spawnpoint.getY() * 100.00) / 100.00) + ", " + (Math.round(spawnpoint.getZ() * 100.00) / 100.00) + "\", clickEvent: {\"action\": \"run_command\" , value: \"/minecraft:tp " + player.getName() + " " + spawnpoint.getX() + " " + spawnpoint.getY() + " " + spawnpoint.getZ() + "\"}}"));
            PacketPlayOutChat ppoc = new PacketPlayOutChat(comp);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
        });

        if(Config.getSpawnpoints().isEmpty())
            player.sendMessage(color("&cNo spawnpoints added yet"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        pos=0;
        return true;
    }
}
