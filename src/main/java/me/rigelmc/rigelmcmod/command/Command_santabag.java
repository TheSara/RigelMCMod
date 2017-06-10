package me.rigelmc.rigelmcmod.command;

import me.rigelmc.rigelmcmod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.rigelmc.rigelmcmod.shop.ShopData;
import org.bukkit.ChatColor;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Gives you Santa's Bag", usage = "/<command>", aliases = "sb")
public class Command_santabag extends FreedomCommand
{
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
    	ShopData sd = plugin.sh.getData(playerSender);
    	if (!sd.issantabag())
    	{
    		msg("You have not yet purchased Santa's Bag from the shop!", ChatColor.RED);
    		return true;
    	}
    	playerSender.getInventory().addItem(plugin.cr.getCrescentRose());
    	msg("You have been given Santa's Bag!", ChatColor.GREEN);
        return true;
    }
}
