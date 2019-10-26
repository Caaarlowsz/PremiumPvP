package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        User user = Data.getUser(event.getPlayer());
        event.setMessage(user.getActiveChatcolor() == null ? event.getMessage() : Data.getChatcolor(user.getActiveChatcolor()).getColor() + event.getMessage());

        Integer level = user.getLevel();
        String leveltext = color((level >= 25 ? level >= 100 ? level >= 250 ? level >= 500 ? level >= 750 ? level >= 1000 ? "&5" : "&d" : "&4" : "&b" : "&f" : "&e" : "&7")+level);

        event.setFormat(event.getFormat()
                .replaceAll("@miraclepvp_suffix", user.getActiveSuffix() == null ? "" : " " + Data.getSuffix(user.getActiveSuffix()).getSuffix())
                .replaceAll("@miraclepvp_level", leveltext)
                .replaceAll("@miraclepvp_namecolor", user.getActiveNamecolor() == null ? "" : "" + Data.getNamecolor(user.getActiveNamecolor()).getColor().toString()));
    }
}
