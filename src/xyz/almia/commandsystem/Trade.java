package xyz.almia.commandsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.almia.accountsystem.Account;

public class Trade implements CommandExecutor{
	Plugin plugin;
	
	public Trade(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		@SuppressWarnings("unused")
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		
		return true;
	}
}