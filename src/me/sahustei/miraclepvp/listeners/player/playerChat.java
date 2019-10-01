package me.sahustei.miraclepvp.listeners.player;

import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class playerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        User user = Data.getUser(event.getPlayer());
        event.setMessage(user.getActiveChatcolor() == null ? event.getMessage() : Data.getChatcolor(user.getActiveChatcolor()).getColor() + event.getMessage());
        event.setFormat(event.getFormat()
                .replaceAll("@miraclepvp_suffix", user.getActiveSuffix() == null ? "" : " " + Data.getSuffix(user.getActiveSuffix()).getSuffix())
                .replaceAll("@miraclepvp_namecolor", user.getActiveNamecolor() == null ? "" : "" + Data.getNamecolor(user.getActiveNamecolor()).getColor().toString()));
    }
}
