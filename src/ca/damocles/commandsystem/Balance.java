package ca.damocles.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.storagesystem.Treasury;
import ca.damocles.utils.Message;

public class Balance implements CommandExecutor{
	Plugin plugin;
	
	public Balance(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		ca.damocles.accountsystem.Character character = account.getLoadedCharacter();
		if(cmd.getName().equals("balance")){
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Balance");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Your balance is "+ChatColor.GREEN+"$"+new Treasury(character).getBalance());
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}else{
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Balance");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Error: Proper usage is /balance");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
		}
		return true;
	}
}
