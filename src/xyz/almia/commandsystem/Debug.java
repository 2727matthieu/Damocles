package xyz.almia.commandsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Debug implements CommandExecutor{

	Plugin plugin;
	
	public Debug(Plugin plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			return true;
		}
		
		Player player = (Player) sender;
		//Account account = new Account(player);
		//xyz.almia.accountsystem.Character character = account.getLoadedCharacter();

		
		
		if(cmd.getName().equalsIgnoreCase("debuging")){
			
		}
		
		return true;
	}

}