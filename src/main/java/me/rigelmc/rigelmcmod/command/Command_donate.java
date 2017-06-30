package me.rigelmc.rigelmcmod.command;

import me.rigelmc.rigelmcmod.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.NON_OP, source = SourceType.BOTH)
@CommandParameters(description = "Shows how to donate to the server", usage = "/<command>", aliases = "don")
public class Command_donate extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        msg("If you wanna donate read below:", ChatColor.YELLOW);
        msg("Hey you, you want to donate?", ChatColor.AQUA);
        msg("LightWarp has to keep the server up, so your donation would be helpful.", ChatColor.DARK_AQUA);
        msg(" - Go to http://rigelmc.ga/donate/ to Donate. You can donate any amount!", ChatColor.DARK_AQUA);
        msg(" - Be sure to leave your Minecraft name in the Payment notes text field, so we know that you donated!", ChatColor.DARK_AQUA);
        msg(" - After sending the payment, DM LightWarp#5690 on Discord or wait for him to to look at his Paypal account.", ChatColor.AQUA);
        msg(" - He will give you a special donator rank.", ChatColor.AQUA);
        return true;
    }
}
