package net.miraclepvp.kitpvp.commands.subcommands.anvil;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.data.Config;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListAnvil implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fAnvil"));
        player.sendMessage(color("&7A list of all anvils."));
        player.sendMessage(color("&7Click on a location to teleport."));
        player.sendMessage(color(""));
        Config.getAnvils().forEach(anvil -> {
            Location anvilLoc = FileManager.deSerialize(anvil);
            pos++;
            IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                    .a(color("{text: \"&5" + pos + ". &f" + (anvilLoc == null ? "world not found" : anvilLoc.getWorld().getName()) + ", " + (Math.round(anvilLoc.getX() * 100.00) / 100.00) + ", " + (Math.round(anvilLoc.getY() * 100.00) / 100.00) + ", " + (Math.round(anvilLoc.getZ() * 100.00) / 100.00) + "\", clickEvent: {\"action\": \"run_command\" , value: \"/minecraft:tp " + player.getName() + " " + anvilLoc.getX() + " " + anvilLoc.getY() + " " + anvilLoc.getZ() + "\"}}"));
            PacketPlayOutChat ppoc = new PacketPlayOutChat(comp);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
        });

        if(Config.getAnvils().isEmpty())
            player.sendMessage(color("&cNo anvils added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
