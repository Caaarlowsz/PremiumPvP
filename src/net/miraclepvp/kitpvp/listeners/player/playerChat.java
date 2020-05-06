package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.commands.StaffchatCMD;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.kit.Editting;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.KitEditGUI;
import net.miraclepvp.kitpvp.utils.CooldownUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerChat implements Listener {

    private static ArrayList<String> domainContains = new ArrayList<>();
    private static ArrayList<String> domainStarts = new ArrayList<>();
    private static ArrayList<String> domainEnds = new ArrayList<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        User user = Data.getUser(player);

        if(event.getMessage().equals("1045ynbgrvfnu4b6tr875vnh78gbv434giytb687tb78v436vb5t874ybv98vtv3b7vvnj5678hvno76itvb47vbv56o4iv564i3uv53iub4v6758i4v6b3587v3i4b65v7843v534v6tb487v5684bv563485vib8675v43v534")){
            event.setCancelled(true);
            return;
        }

        if(KitEditGUI.editting.containsKey(player)){
            event.setCancelled(true);
            if(event.getMessage().equalsIgnoreCase("cancel")){
                KitEditGUI.editting.remove(player);
                KitEditGUI.k_editting.remove(player);
                player.sendMessage(color("&aYou've cancelled the editting."));
                return;
            }
            if(KitEditGUI.k_editting.get(player) == null) return;
            if(KitEditGUI.editting.get(player).equals(Editting.NAME))
                KitEditGUI.k_editting.get(player).setName(event.getMessage());
            if(KitEditGUI.editting.get(player).equals(Editting.DESCRIPTION))
                KitEditGUI.k_editting.get(player).setDescription(event.getMessage());
            if (KitEditGUI.editting.get(player).equals(Editting.PRICE)) {
                try {
                    Integer.parseInt(event.getMessage());
                    KitEditGUI.k_editting.get(player).setPrice(Integer.valueOf(event.getMessage()));
                } catch (Exception ex) {
                    player.sendMessage(color("&aThe given amount is not a number"));
                    return;
                }
            }
            if(KitEditGUI.editting.get(player).equals(Editting.ICON)) {
                Material material = Material.getMaterial(event.getMessage());
                if(material == null || material.equals(Material.AIR)){
                    player.sendMessage(color("&aThe given item is not valid"));
                    return;
                }
                KitEditGUI.k_editting.get(player).setIcon(material);
            }
            player.sendMessage(color("&aYou've changed the " + KitEditGUI.editting.get(player).getName() + " to " + event.getMessage() + "."));
            player.openInventory(KitEditGUI.getMainInventory(KitEditGUI.k_editting.get(player).getName()));
            KitEditGUI.editting.remove(player);
            KitEditGUI.k_editting.remove(player);
            event.setCancelled(true);
            return;
        }

        event.setMessage(user.getActiveChatcolor() == null ? event.getMessage() : Data.getChatcolor(user.getActiveChatcolor()).getColor() + event.getMessage());

        Integer level = user.getLevel();
        String leveltext = color((
                level >= 5 ?
                level >= 10 ?
                level >= 15 ?
                level >= 20 ?
                level >= 25 ?
                level >= 30 ?
                level >= 35 ?
                level >= 40 ?
                level >= 45 ?
                level >= 50 ?
                level >= 60 ?
                level >= 70 ?
                level >= 80 ?
                level >= 90 ?
                level >= 100 ?
                "&5" : "&d" : "&1" : "&3" : "&9" : "&a" : "&4" : "&c" : "&6" : "&e" : "&2" : "&a" : "&0" : "&f" : "&8" : "&7"
        )+level);

        event.setFormat(color(event.getFormat()
                .replaceAll("@miraclepvp_prefix", user.getPrefix() == null ? "" : Data.getPrefix(user.getPrefix()).getPrefix() + " ")
                .replaceAll("@miraclepvp_suffix", user.getActiveSuffix() == null ? "" : " " + Data.getSuffix(user.getActiveSuffix()).getSuffix())
                .replaceAll("@miraclepvp_level", leveltext)
                .replaceAll("@miraclepvp_namecolor", user.getActiveNamecolor() == null ? "" : "" + Data.getNamecolor(user.getActiveNamecolor()).getColor().toString())));

        AtomicReference<Boolean> contain = new AtomicReference<>(false);
        AtomicReference<Boolean> end = new AtomicReference<>(false);

        final String[] words = ChatColor.stripColor(event.getMessage()).split(" ");

        Arrays.stream(words).forEach(word -> {
            if(Config.getCurseWords().contains(word)){
                String replacement = "";
                for(int i = 0; i<word.length(); i++)
                    replacement = replacement+"*";
                event.setMessage(event.getMessage().replaceFirst(word, replacement));
            }
        });

        ArrayList<String> stoppedWords = new ArrayList<>();
        domainContains.stream().filter(word -> !contain.get()).filter(words[0]::contains).forEach(word -> {
            words[0] = words[0].replaceFirst(word, "");
            stoppedWords.add(word);
            contain.set(true);
        });
       domainEnds.stream().filter(word -> !end.get()).filter(words[0]::contains).forEach(word -> {
           words[0] = words[0].replaceFirst(word, "");
           stoppedWords.add(word);
           end.set(true);
       });

        switch (user.getChatmode()){
            case ALL:
                if(!player.hasPermission("miraclepvp.donator.spambypass")) {
                    CooldownUtil.Cooldown chatCooldown = CooldownUtil.getCooldown(player, "chat");
                    if (chatCooldown == null || !chatCooldown.hasTimeLeft()) {
                        CooldownUtil.Cooldown newCooldown = CooldownUtil.prepare(player, "chat", 3);
                        newCooldown.start();
                    } else {
                        player.sendMessage(color("&cWait " + chatCooldown.getSecondsLeft() + " seconds before sending a new chat message!"));
                        event.setCancelled(true);
                        return;
                    }
                }
                break;
            case GUILD:
                event.setCancelled(true);
                Guild guild = Data.getGuild(user.getGuild());
                if(guild.getSlow() && !guild.getMaster().equals(player.getUniqueId())){
                    CooldownUtil.Cooldown cooldown = CooldownUtil.getCooldown(player, "gc" + guild.getUuid());
                    if(cooldown == null || !cooldown.hasTimeLeft()){
                        CooldownUtil.Cooldown newCooldown = CooldownUtil.prepare(player, "gc" + guild.getUuid(), 5);
                        newCooldown.start();
                    } else {
                        player.sendMessage(color("&cWait " + cooldown.getSecondsLeft() + " seconds before sending a new guild message!"));
                        return;
                    }
                }
                String[] args = event.getMessage().split(" ");
                String message = "";
                for(int i = 0; i<args.length; i++){
                    message = message + args[i] + " ";
                }
                guild.sendMessage(Bukkit.getOfflinePlayer(player.getUniqueId()), ChatColor.stripColor(message));
                break;
            case STAFF:
                event.setCancelled(true);
                StaffchatCMD.sendMessage(player.getUniqueId(), ChatColor.stripColor(event.getMessage()));
                break;
        }
    }

    public static void loadChatfilter(){
        domainContains.add(".1337srv.");
        domainContains.add(".g-s.");
        domainContains.add(".mcnetwork.");
        domainContains.add(".mcpro.");
        domainContains.add(".mcserv.");
        domainContains.add(".my-serv.");
        domainContains.add(".mygs.");
        domainContains.add(".mymcserver.");
        domainContains.add(".myserver.");
        domainContains.add(".serv.");
        domainContains.add(".aternos.");

        domainEnds.add(".net");
        domainEnds.add(".eu");
        domainEnds.add(".co.uk");
        domainEnds.add(".com");
        domainEnds.add(".uk");
        domainEnds.add(".us");
        domainEnds.add(".nl");
        domainEnds.add(".co");
        domainEnds.add(".org");
        domainEnds.add(".app");
        domainEnds.add(".io");
        domainEnds.add(".ai");
        domainEnds.add(".tech");
        domainEnds.add(".me");
        domainEnds.add(".dev");
        domainEnds.add(".online");
        domainEnds.add(".shop");
        domainEnds.add(".site");
        domainEnds.add(".club");
        domainEnds.add(".info");
        domainEnds.add(".am");
        domainEnds.add(".com.ar");
        domainEnds.add(".as");
        domainEnds.add(".at");
        domainEnds.add(".com.au");
        domainEnds.add(".id.au");
        domainEnds.add(".net.au");
        domainEnds.add(".org.au");
        domainEnds.add(".be");
        domainEnds.add(".best");
        domainEnds.add(".bet");
        domainEnds.add(".bid");
        domainEnds.add(".bio");
        domainEnds.add(".biz");
        domainEnds.add(".black");
        domainEnds.add(".blog");
        domainEnds.add(".blue");
        domainEnds.add(".br.com");
        domainEnds.add(".brussels");
        domainEnds.add(".build");
        domainEnds.add(".builders");
        domainEnds.add(".cloud");
        domainEnds.add(".community");
        domainEnds.add(".cx");
        domainEnds.add(".cz");
        domainEnds.add(".de");
        domainEnds.add(".dk");
        domainEnds.add(".diamonds");
        domainEnds.add(".direct");
        domainEnds.add(".domains");
        domainEnds.add(".earth");
        domainEnds.add(".ec");
        domainEnds.add(".eco");
        domainEnds.add(".es");
        domainEnds.add(".eu.com");
        domainEnds.add(".fan");
        domainEnds.add(".fans");
        domainEnds.add(".farm");
        domainEnds.add(".fi");
        domainEnds.add(".fm");
        domainEnds.add(".fr");
        domainEnds.add(".fun");
        domainEnds.add(".fund");
        domainEnds.add(".fyi");
        domainEnds.add(".games");
        domainEnds.add(".gd");
        domainEnds.add(".gr");
        domainEnds.add(".gs");
        domainEnds.add(".gy");
        domainEnds.add(".hn");
        domainEnds.add(".host");
        domainEnds.add(".ht");
        domainEnds.add(".id");
        domainEnds.add(".jp");
        domainEnds.add(".lc");
        domainEnds.add(".li");
        domainEnds.add(".lt");
        domainEnds.add(".lv");
        domainEnds.add(".mn");
        domainEnds.add(".ms");
        domainEnds.add(".mu");
        domainEnds.add(".mx");
        domainEnds.add(".nu");
        domainEnds.add(".pl");
        domainEnds.add(".pm");
        domainEnds.add(".pro");
        domainEnds.add(".pt");
        domainEnds.add(".pw");
        domainEnds.add(".qa");
        domainEnds.add(".re");
        domainEnds.add(".ru");
        domainEnds.add(".sc");
        domainEnds.add(".se");
        domainEnds.add(".sg");
        domainEnds.add(".sh");
        domainEnds.add(".sx");
        domainEnds.add(".tc");
        domainEnds.add(".tf");
        domainEnds.add(".tk");
        domainEnds.add(".tl");
        domainEnds.add(".to");
        domainEnds.add(".tw");
        domainEnds.add(".uk");
        domainEnds.add(".us");
        domainEnds.add(".vc");
        domainEnds.add(".tv");
        domainEnds.add(".vg");
        domainEnds.add(".wf");
        domainEnds.add(".ws");
        domainEnds.add(".xyz");
    }
}
