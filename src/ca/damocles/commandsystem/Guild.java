package ca.damocles.commandsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;

public class Guild implements CommandExecutor{
	Plugin plugin;
	
	public Guild(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		@SuppressWarnings("unused")
		ca.damocles.accountsystem.Character character = account.getLoadedCharacter();
		
		return true;
	}
}
