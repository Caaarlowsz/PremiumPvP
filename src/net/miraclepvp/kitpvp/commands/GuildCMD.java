package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.commands.subcommands.guild.*;
import net.miraclepvp.kitpvp.inventories.PermissionsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class GuildCMD implements CommandExecutor {

    private HelpGuild helpGuild = new HelpGuild();
    private InviteGuild inviteGuild = new InviteGuild();
    private AcceptGuild acceptGuild = new AcceptGuild();
    private CreateGuild createGuild = new CreateGuild();
    private DisbandGuild disbandGuild = new DisbandGuild();
    private MembersGuild membersGuild = new MembersGuild();
    private OnlineGuild onlineGuild = new OnlineGuild();
    private DiscordGuild discordGuild = new DiscordGuild();
    private InfoGuild infoGuild = new InfoGuild();
    private MuteGuild muteGuild = new MuteGuild();
    private RenameGuild renameGuild = new RenameGuild();
    private TagGuild tagGuild = new TagGuild();
    private ChatGuild chatGuild = new ChatGuild();
    private MotdGuild motdGuild = new MotdGuild();
    private KickGuild kickGuild = new KickGuild();
    private PromoteGuild promoteGuild = new PromoteGuild();
    private DemoteGuild demoteGuild = new DemoteGuild();
    private LeaveGuild leaveGuild = new LeaveGuild();
    private TransferGuild transferGuild = new TransferGuild();
    private SlowGuild slowGuild = new SlowGuild();
    private PermissionsGuild permissionsGuild = new PermissionsGuild();
    private TagcolorGuild tagcolorGuild = new TagcolorGuild();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage(Text.color("&cPlease use /guild help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "accept":
                acceptGuild.onCommand(sender, cmd, label, args);
                break;
            case "help":
                helpGuild.onCommand(sender, cmd, label, args);
                break;
            case "create":
                createGuild.onCommand(sender, cmd, label, args);
                break;
            case "disband":
                disbandGuild.onCommand(sender, cmd, label, args);
                break;
            case "members":
                membersGuild.onCommand(sender, cmd, label, args);
                break;
            case "online":
                onlineGuild.onCommand(sender, cmd, label, args);
                break;
            case "discord":
                discordGuild.onCommand(sender, cmd, label, args);
                break;
            case "info":
                infoGuild.onCommand(sender, cmd, label, args);
                break;
            case "mute":
                muteGuild.onCommand(sender, cmd, label, args);
                break;
            case "rename":
                renameGuild.onCommand(sender, cmd, label, args);
                break;
            case "slow":
                slowGuild.onCommand(sender, cmd, label, args);
                break;
            case "invite":
                inviteGuild.onCommand(sender, cmd, label, args);
                break;
            case "motd":
                motdGuild.onCommand(sender, cmd, label, args);
                break;
            case "transfer":
                transferGuild.onCommand(sender, cmd, label, args);
                break;
            case "promote":
                promoteGuild.onCommand(sender, cmd, label, args);
                break;
            case "demote":
                demoteGuild.onCommand(sender, cmd, label, args);
                break;
            case "kick":
                kickGuild.onCommand(sender, cmd, label, args);
                break;
            case "chat":
                chatGuild.onCommand(sender, cmd, label, args);
                break;
            case "permissions":
                permissionsGuild.onCommand(sender, cmd, label, args);
                break;
            case "leave":
                leaveGuild.onCommand(sender, cmd, label, args);
                break;
            case "tag":
                tagGuild.onCommand(sender, cmd, label, args);
                break;
            case "tagcolor":
                tagcolorGuild.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&cPlease use /guild help for more information."));
                break;
        }
        return true;
    }
}
