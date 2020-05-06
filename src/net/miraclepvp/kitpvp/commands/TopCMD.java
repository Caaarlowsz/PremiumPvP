package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.data.Top;
import net.miraclepvp.kitpvp.data.user.StatType;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class TopCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        StatType statType = null;
        try {
            statType = StatType.valueOf(args[0].toUpperCase());
        }catch (Exception ex){
            sender.sendMessage(color("&cPlease give a valid argument: kills, deaths, level, streak"));
            return true;
        }
        Boolean compact = false;
        if(statType.equals(StatType.KILLS) || statType.equals(StatType.DEATHS))
            compact = true;
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(sender, "&fTop 10 - " + statType.getName());
        ChatCenterUtil.sendCenteredMessage(sender, "");
        for(int i = 1; i<10;i++){
            try {
                User local = Top.getTop(statType, i);
                sender.sendMessage(color("&f" + i + ". &7" + local.getPlayer().getName() + ": " + local.getStat(statType, compact)));
            }catch (Exception ex){
            }
        }
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        return true;
    }
}
