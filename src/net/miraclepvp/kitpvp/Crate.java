package net.miraclepvp.kitpvp;

public class Crate {

    private static void trash(){
        //                    try {
//                        //Als er geen prijs is
//                        if (inv.getItem(22).getType() == null || inv.getItem(22).getType().equals(Material.BARRIER) || inv.getItem(22).getType().equals(Material.AIR)) {
//                            player.closeInventory();
//                            return;
//                        }
//
//                        //Haal de UUID van het object op
//                        UUID uuid = UUID.fromString(ChatColor.stripColor(inv.getItem(22).getItemMeta().getLore().get(0)));
//
//                        User user = Data.getUser(player);
//
//                        //Als het een kit is
//                        try {
//                            Kit object = Data.getKit(uuid);
//                            if (user.getKitsList().contains(uuid)) {
//                                user.setCoins(user.getCoins() + object.getSellprice(), false);
//                                player.sendMessage(color("&cYou already have this kit, so you received " + object.getSellprice() + " coins!"));
//                            } else {
//                                player.sendMessage(color("&aYou won the " + object.getName() + " kit!"));
//                                user.addKit(uuid);
//                            }
//                        } catch (NoSuchElementException ex) {
//                            //Als het een namecolor is
//                            try {
//                                Namecolor object = Data.getNamecolor(uuid);
//                                if (user.getNamecolorsList().contains(uuid)) {
//                                    user.setTokens(user.getTokens() + object.getSell());
//                                    player.sendMessage(color("&cYou already have this namecolor, so you received " + object.getSell() + " tokens!"));
//                                } else {
//                                    player.sendMessage(color("&aYou won the " + object.getName() + " namecolor!"));
//                                    user.addNameColor(uuid);
//                                }
//                            } catch (NoSuchElementException ex1) {
//                                //Als het een chatcolor is
//                                try {
//                                    Chatcolor object = Data.getChatcolor(uuid);
//                                    if (user.getChatcolorsList().contains(uuid)) {
//                                        user.setTokens(user.getTokens() + object.getSell());
//                                        player.sendMessage(color("&cYou already have this chatcolor, so you received " + object.getSell() + " tokens!"));
//                                    } else {
//                                        player.sendMessage(color("&aYou won the " + object.getName() + " chatcolor!"));
//                                        user.addChatColor(uuid);
//                                    }
//                                } catch (NoSuchElementException ex2) {
//                                    //Als het een suffix is
//                                    try {
//                                        Suffix object = Data.getSuffix(uuid);
//                                        if (user.getSuffixesList().contains(uuid)) {
//                                            user.setTokens(user.getTokens() + object.getSell());
//                                            player.sendMessage(color("&cYou already have this suffix, so you received " + object.getSell() + " tokens!"));
//                                        } else {
//                                            player.sendMessage(color("&aYou won the " + object.getName() + " suffix!"));
//                                            user.addSuffix(uuid);
//                                        }
//                                    } catch (NoSuchElementException ex3) {
//                                        //Als het een trail is
//                                        try {
//                                            Trail object = Data.getTrail(uuid);
//                                            if (user.getTrailsList().contains(uuid)) {
//                                                user.setTokens(user.getTokens() + object.getSell());
//                                                player.sendMessage(color("&cYou already have this trail, so you received " + object.getSell() + " tokens!"));
//                                            } else {
//                                                player.sendMessage(color("&aYou won the " + object.getName() + " trail!"));
//                                                user.addTrail(uuid);
//                                            }
//                                        } catch (NoSuchElementException ex4) {
//                                            player.sendMessage(color("&4We failed to get this item."));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } catch (NullPointerException ex) {
//                        player.closeInventory();
//                        return;
//                    }



        //                //Play a sound
//                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 0.533F);
//
//                //Set the colored glass panes.. DISCO!!!!!!
//                for (int i = 0; i < inv.getSize(); i++)
//                    inv.setItem(i, new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, ((random.nextInt(14)) + 1)).setDisplayName(" "));
//
//                //Set the arrows
//                inv.setItem(21, arrow);
//                inv.setItem(23, arrow);
//
//                //Set the items
//                Arrays.stream(slots).forEach(value -> {
//                    inv.setItem(value, getItem(items, currentCount[0]));
//                    currentCount[0]++;
//                });
    }
}
