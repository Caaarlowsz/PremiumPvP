package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.commands.subcommands.guild.helpPages.HelpGuildOne;
import net.miraclepvp.kitpvp.commands.subcommands.guild.helpPages.HelpGuildTwo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpGuild implements CommandExecutor {

    private HelpGuildOne helpGuildOne = new HelpGuildOne();
    private HelpGuildTwo helpGuildTwo = new HelpGuildTwo();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Integer page = 1;
        if(args.length == 1) {
            helpGuildOne.onCommand(sender, cmd, label, args);
            return true;
        }
        try{
            page = Integer.parseInt(args[1]);
        } catch (IllegalArgumentException ex){
            helpGuildOne.onCommand(sender, cmd, label, args);
        }
        switch (page){
            case 2:
                helpGuildTwo.onCommand(sender, cmd, label, args);
                break;
            default:
                helpGuildOne.onCommand(sender, cmd, label, args);
                break;
        }
        return true;
    }
}
