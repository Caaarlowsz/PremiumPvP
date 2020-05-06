package net.miraclepvp.kitpvp.inventories;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.data.report.Reports;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ReportGUI {

    public static Inventory getStaffInventory(){
        Inventory inv = Bukkit.createInventory(null, 6*9, color("&8Manage Reports"));

        return inv;
    }

    public static Inventory getInventory(Player sender, Player target){
        Inventory inv = Bukkit.createInventory(null, 9, color("&8Report - Choose a category"));
        inv.setItem(1, new ItemstackFactory(Material.NAME_TAG).setDisplayName("&5Chat").addLoreLine("&7Report " + target.getName() + " for a chat related situation."));
        inv.setItem(3, new ItemstackFactory(Material.DIAMOND_SWORD).setDisplayName("&5Cheating").addLoreLine("&7Report " + target.getName() + " for cheating."));
        inv.setItem(5, new ItemstackFactory(Material.REDSTONE_TORCH_ON).setDisplayName("&5Abusing").addLoreLine("&7Report " + target.getName() + " for abusing a bug/glitch/exploit."));
        inv.setItem(7, new ItemstackFactory(Material.STRING).setDisplayName("&5Others").addLoreLine("&7Report " + target.getName() + " for a different situation."));
        return inv;
    }

    public static Inventory getFinalInventory(Player sender, Player target, Reports.ReportCategory category, List<Integer> enabled){
        Inventory inv = Bukkit.createInventory(null, 2*9, color("&8Report - Choose a reason"));
        switch(category){
            case CHAT:
                inv.addItem(new ItemstackFactory(Material.SIGN).setDisplayName("&5Advertising").addLoreLine("&7Report " + target.getName() + " for advertising."));
                inv.addItem(new ItemstackFactory(Material.BOW).setDisplayName("&5Spamming").addLoreLine("&7Report " + target.getName() + " for spamming."));
                inv.addItem(new ItemstackFactory(Material.BARRIER).setDisplayName("&5Cursing").addLoreLine("&7Report " + target.getName() + " for cursing."));
                break;
            case CHEATING:
                inv.addItem(new ItemstackFactory(Material.CHAINMAIL_BOOTS).setDisplayName("&5Speed").addLoreLine("&7Report " + target.getName() + " for speed."));
                inv.addItem(new ItemstackFactory(Material.DIAMOND_SWORD).setDisplayName("&5Killaura").addLoreLine("&7Report " + target.getName() + " for killaura."));
                inv.addItem(new ItemstackFactory(Material.FEATHER).setDisplayName("&5Fly").addLoreLine("&7Report " + target.getName() + " for flying."));
                inv.addItem(new ItemstackFactory(Material.RABBIT_FOOT).setDisplayName("&5Jump").addLoreLine("&7Report " + target.getName() + " for any jump related hacks."));
                inv.addItem(new ItemstackFactory(Material.BARRIER).setDisplayName("&5Velocity/Anti-KB").addLoreLine("&7Report " + target.getName() + " for velocity."));
                inv.addItem(new ItemstackFactory(SkullBuilder.CAMERA.getSkull()).setDisplayName("&5ESP").addLoreLine("&7Report " + target.getName() + " for ESP."));
                inv.addItem(new ItemstackFactory(Material.FISHING_ROD).setDisplayName("&5Reach").addLoreLine("&7Report " + target.getName() + " for Reach."));
                inv.addItem(new ItemstackFactory(Material.WOOD_BUTTON).setDisplayName("&5Autoclicker").addLoreLine("&7Report " + target.getName() + " for Autoclicker."));
                break;
            case ABUSING:
                inv.addItem(new ItemstackFactory(SkullBuilder.HEROBRINE.getSkull().getType(), 2).setDisplayName("&5Alt Abuse").addLoreLine("&7Report " + target.getName() + " for Alt Abuse."));
                inv.addItem(new ItemstackFactory(Material.BEDROCK).setDisplayName("&5Bugging").addLoreLine("&7Report " + target.getName() + " for abusing Bugs."));
                break;
            case OTHERS:
                inv.addItem(new ItemstackFactory(Material.NAME_TAG).setDisplayName("&5Inappropriate Name").addLoreLine("&7Report " + target.getName() + " for using a inappropriate name."));
                inv.addItem(new ItemstackFactory(SkullBuilder.getPlayerSkull(target.getName())).setDisplayName("&5Inappropriate Skin").addLoreLine("&7Report " + target.getName() + " for using a inappropriate skin."));
                inv.addItem(new ItemstackFactory(Material.GOLDEN_APPLE).setDisplayName("&5Teaming").addLoreLine("&7Report " + target.getName() + " for teaming."));
                break;
        }

        inv.setItem(8, enabled.size()>0?
                new ItemstackFactory(Material.EMERALD_BLOCK).setDisplayName("&aSend Report").addLoreLine("&7Click here to send this report to the staff.")
                :
                new ItemstackFactory(Material.REDSTONE_BLOCK).setDisplayName("&cSend Report").addLoreLine("&7Add at least one reason.")
        );
        inv.setItem(17, new ItemstackFactory(Material.PAPER).setDisplayName("&5Category").addLoreLine("&7" + category.toString()));

        for(int i = 0; i<8; i++) {
            boolean toggled = enabled.contains(i);
            if (inv.getItem(i) != null)
                inv.setItem(i + 9, getGlass(toggled?true:false));
        }
        return inv;
    }

    private static ItemStack getGlass(boolean activated){
        return new ItemstackFactory(Material.STAINED_GLASS_PANE, 1, activated?5:14).setDisplayName(activated?"&aTrue":"&cFalse").addLoreLine("&7Click the item above to " + (activated?"remove":"add") + " this reason");
    }
}
