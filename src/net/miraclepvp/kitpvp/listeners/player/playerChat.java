package net.miraclepvp.kitpvp.listeners.player;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.miraclepvp.kitpvp.commands.FreezeCMD;
import net.miraclepvp.kitpvp.commands.StaffchatCMD;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.kit.Editting;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.KitEditGUI;
import net.miraclepvp.kitpvp.objects.Board;
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

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerChat implements Listener {

    private static ArrayList<String> domainContains = new ArrayList<>();
    private static ArrayList<String> domainEnds = new ArrayList<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = Data.getUser(player);

        Board.updatePlayerListName(player);

        if(FreezeCMD.frozenList.contains(player.getUniqueId())){ //If the player is frozen
            event.setCancelled(true); //Cancel the message
            player.sendMessage(color("&4[&cFROZEN&4] &f" + event.getPlayer().getName() + "&8: &7" + event.getMessage())); //Send the message to this player
            for(Player staff : Bukkit.getOnlinePlayers()) {
                if (staff.hasPermission("miraclepvp.freeze.readchat"))
                    staff.sendMessage(color("&4[&cFROZEN&4] &f" + event.getPlayer().getName() + "&8: &7" + event.getMessage())); //Send the message to all staff with the needed permissions
            }
            return;
        }

        if (KitEditGUI.editting.containsKey(player)) { //If the player is editing a kit
            event.setCancelled(true); //Cancel the message

            if (event.getMessage().equalsIgnoreCase("cancel")) { //If the player cancels the edit
                KitEditGUI.editting.remove(player); //Remove the player from the list
                KitEditGUI.k_editting.remove(player); //Remove the player from the list
                player.sendMessage(color("&aYou've cancelled the editting.")); //Send a confirmation
                return;
            }

            if (KitEditGUI.k_editting.get(player) == null) return; //Return if there is no kit known
            if (KitEditGUI.editting.get(player).equals(Editting.NAME)) //If the player is editing the name
                KitEditGUI.k_editting.get(player).setName(event.getMessage()); //Change the name
            if (KitEditGUI.editting.get(player).equals(Editting.DESCRIPTION)) //If the player is editing the description
                KitEditGUI.k_editting.get(player).setDescription(event.getMessage()); //Change the description
            if (KitEditGUI.editting.get(player).equals(Editting.PRICE)) { //If the player is editing the price
                try {
                    KitEditGUI.k_editting.get(player).setPrice(Integer.valueOf(event.getMessage())); //Set the price
                } catch (Exception ex) {
                    player.sendMessage(color("&aThe given amount is not a number")); //Send feedback if the message is not a number
                    return;
                }
            }
            if (KitEditGUI.editting.get(player).equals(Editting.ICON)) { //If the player is editing the icon
                Material material = Material.getMaterial(event.getMessage()); //Get the material
                if (material == null || material.equals(Material.AIR)) { //If the material is air or not existing
                    player.sendMessage(color("&aThe given item is not valid")); //Send feedback
                    return;
                }
                KitEditGUI.k_editting.get(player).setIcon(material); //Change the icon
            }
            player.sendMessage(color("&aYou've changed the " + KitEditGUI.editting.get(player).getName() + " to " + event.getMessage() + ".")); //Send feedback
            player.openInventory(KitEditGUI.getMainInventory(KitEditGUI.k_editting.get(player).getName())); //Open the edit GUI
            KitEditGUI.editting.remove(player); //Remove the player from the list
            KitEditGUI.k_editting.remove(player); //Remove the player from the list
            return;
        }

        Integer level = user.getLevel(); //Get the players level
        net.md_5.bungee.api.ChatColor levelColor = net.md_5.bungee.api.ChatColor.getByChar( //Get the color for the level
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
                                                                                                                                        '5' : 'd' : '1' : '3' : '9' : 'a' : '4' : 'c' : '6' : 'e' : '2' : 'a' : '0' : 'f' : '8' : '7'
        );

        final String[] words = ChatColor.stripColor(event.getMessage()).split(" "); //Get all words in a array

        ArrayList<String> antiAds = new ArrayList();
        antiAds.addAll(domainContains);
        antiAds.addAll(domainEnds);
        antiAds.forEach(word -> {
            if(event.getMessage().toLowerCase().contains(word)) { //If the message contains the forbidden word
                Integer start=event.getMessage().toLowerCase().indexOf(word), finale=start+word.length(); //Get the first and last characters position (needed for replace)
                String found=(String)event.getMessage().subSequence(start,finale); //Get the message which we need to replace
                StringBuilder replacement=new StringBuilder(); //Make a string builder
                for(int i=1;i<found.length();i++)replacement.append("*"); //Place a * for every character in the text
                event.setMessage(event.getMessage().replaceFirst(found, "."+replacement.toString())); //Set the word to the modified version
            }
        });

        Arrays.stream(words).forEach(word -> {
            if (Config.getCurseWords().contains(word.toLowerCase())) {
                StringBuilder replacement = new StringBuilder();
                for (int i = 0; i < word.length(); i++) replacement.append("*");
                event.setMessage(event.getMessage().replaceFirst(word, replacement.toString()));
            }
        });

        switch (user.getChatmode()) {
            case ALL:
                if (!player.hasPermission("premiumpvp.donator.spambypass")) {
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
                if (guild.getSlow() && !guild.getMaster().equals(player.getUniqueId())) {
                    CooldownUtil.Cooldown cooldown = CooldownUtil.getCooldown(player, "gc" + guild.getUuid());
                    if (cooldown == null || !cooldown.hasTimeLeft()) {
                        CooldownUtil.Cooldown newCooldown = CooldownUtil.prepare(player, "gc" + guild.getUuid(), 5);
                        newCooldown.start();
                    } else {
                        player.sendMessage(color("&cWait " + cooldown.getSecondsLeft() + " seconds before sending a new guild message!"));
                        return;
                    }
                }
                guild.sendMessage(Bukkit.getOfflinePlayer(player.getUniqueId()), ChatColor.stripColor(event.getMessage()));
                break;
            case STAFF:
                event.setCancelled(true);
                StaffchatCMD.sendMessage(player.getUniqueId(), ChatColor.stripColor(event.getMessage()));
                break;
        }

        if (!event.isCancelled())
            for (Player target : Bukkit.getOnlinePlayers()) {
                User duser = Data.getUser(target);

                TextComponent mainComponent = new TextComponent(net.md_5.bungee.api.ChatColor.DARK_GRAY + "["); //[
                TextComponent levelText = new TextComponent(level.toString()); //[
                levelText.setColor(levelColor); //[
                mainComponent.addExtra(levelText); //[x
                mainComponent.addExtra(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                        "&8] " + (user.getPrefix() == null ? "" : Data.getPrefix(user.getPrefix()).getName().equalsIgnoreCase("default") ? "" : Data.getPrefix(user.getPrefix()).getPrefix() + " "))
                ); //[x] Prefix
                TextComponent playerName = new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                        user.getActiveNamecolor() == null ? net.md_5.bungee.api.ChatColor.GRAY + player.getName() : "" + Data.getNamecolor(user.getActiveNamecolor()).getColor().toString() + player.getName()
                ));
                if (!target.getUniqueId().equals(player.getUniqueId()) && Config.getChatNameHover())
                    playerName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                            "&5" + player.getName() + " VS " + target.getName() + "\n" +
                                    "&5> &7You have:\n" +
                                    "&7- " + duser.getbKills().get(player.getUniqueId()) + " kills on this player\n" +
                                    "&7- " + duser.getbAssists().get(player.getUniqueId()) + " assists on this player\n" +
                                    "&7- " + duser.getbDeaths().get(player.getUniqueId()) + " deaths by this player\n" +
                                    "\n" +
                                    "&5> &7This player has:\n" +
                                    "&7- " + user.getbKills().get(target.getUniqueId()) + " kills on you\n" +
                                    "&7- " + user.getbAssists().get(target.getUniqueId()) + " assists on you\n" +
                                    "&7- " + user.getbDeaths().get(target.getUniqueId()) + " deaths by you"
                    ).replaceAll("null", "0")).create()));
                mainComponent.addExtra(playerName); //[x] Prefix Name
                mainComponent.addExtra(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                        (user.getActiveSuffix() == null ? "" : " " + Data.getSuffix(user.getActiveSuffix()).getSuffix())
                                + "&8:&7 ")); //[x] Prefix Name Suffix:
                mainComponent.addExtra(event.getMessage()); //[x] Prefix Name: Message

                char chatcolor = '7';
                if(user.getActiveChatcolor() != null)
                    chatcolor = Data.getChatcolor(user.getActiveChatcolor()).getColor().getChar();

                mainComponent.setColor(net.md_5.bungee.api.ChatColor.getByChar(chatcolor));
                target.spigot().sendMessage(mainComponent);
            }

        event.setCancelled(true);
        return;
    }

    public static void loadChatfilter() {
        domainContains.add(".1337srv");
        domainContains.add(".g-s");
        domainContains.add(".mcnetwork");
        domainContains.add(".mcpro");
        domainContains.add(".mcserv");
        domainContains.add(".my-serv");
        domainContains.add(".mygs");
        domainContains.add(".mymcserver");
        domainContains.add(".myserver");
        domainContains.add(".serv");
        domainContains.add(".aternos");

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
