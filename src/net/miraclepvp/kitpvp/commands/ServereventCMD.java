package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.serverevent.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ServereventCMD implements CommandExecutor {

    private HelpServerevent helpStaffevent = new HelpServerevent();
    private StartServerevent startServerevent = new StartServerevent();
    private StopServerevent stopServerevent = new StopServerevent();
    private OpenServerevent openServerevent = new OpenServerevent();
    private CloseServerevent closeServerevent = new CloseServerevent();
    private BroadcastServerevent broadcastServerevent = new BroadcastServerevent();
    private SetlocationServerevent setlocationServerevent = new SetlocationServerevent();
    private JoinServerevent joinServerevent = new JoinServerevent();
    private LeaveServerevent leaveServerevent = new LeaveServerevent();
    private SetkitServerevent setkitServerevent = new SetkitServerevent();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            helpStaffevent.onCommand(sender, cmd, label, args);
            return true;
        }

        switch (args[0].toLowerCase()){
            case "start":
                startServerevent.onCommand(sender, cmd, label, args);
                break;
            case "stop":
                stopServerevent.onCommand(sender, cmd, label, args);
                break;
            case "open":
                openServerevent.onCommand(sender, cmd, label, args);
                break;
            case "close":
                closeServerevent.onCommand(sender, cmd, label, args);
                break;
            case "broadcast":
                broadcastServerevent.onCommand(sender, cmd, label, args);
                break;
            case "setlocation":
                setlocationServerevent.onCommand(sender, cmd, label, args);
                break;
            case "join":
                joinServerevent.onCommand(sender, cmd, label, args);
                break;
            case "leave":
               leaveServerevent.onCommand(sender, cmd, label, args);
                break;
            case "setkit":
                setkitServerevent.onCommand(sender, cmd, label, args);
                break;
            default:
                helpStaffevent.onCommand(sender, cmd, label, args);
                break;
        }
        return true;
    }
}
