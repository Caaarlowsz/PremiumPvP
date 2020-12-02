package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class InviteGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(color("&cPlease use /guild invite <name>"));
            return true;
        }
        UUID uuid = ((Player) sender).getUniqueId();

        User user = Data.getUser(((Player)sender));

        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }
        Guild guild = Data.getGuild(user.getGuild());

        ArrayList<PermissionType> userPerms = new ArrayList<>();

        if(guild.getMembers().contains(uuid))
            userPerms = guild.getMemberPerms();
        if(guild.getOfficers().contains(uuid))
            userPerms = guild.getOfficerPerms();

        if(!userPerms.contains(PermissionType.INVITE) && !guild.getMaster().equals(uuid)){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }

        if(guild.getPlayers().size() >= guild.getMaxPlayers()){
            sender.sendMessage(color("&cYour guild is full."));
        }

        Player target = Bukkit.getPlayer(args[1]);

        if(target == null){
            sender.sendMessage(color("&cThis player is not online."));
            return true;
        }

        if(Data.getUser(target).getGuild() != null && Data.getUser(target).getGuild().equals(guild.getUuid())){
            sender.sendMessage(color("&cThis player is already in the guild"));
            return true;
        }

        guild.getInvites().add(target.getUniqueId());

        guild.sendBroadcast(sender.getName() + " invited " + target.getName() + " to the guild.");

        target.sendMessage(color("&a" + sender.getName() + " invited you to join " + guild.getName() + "."));
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                .a(color("{text: \"&eClick here to join\", clickEvent: {\"action\": \"run_command\" , value: \"/guild accept " + guild.getName() + "\"}}"));
        PacketPlayOutChat ppoc = new PacketPlayOutChat(comp);
        ((CraftPlayer) target).getHandle().playerConnection.sendPacket(ppoc);
        return true;
    }
}
