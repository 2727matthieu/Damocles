package xyz.almia.commandsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.almia.accountsystem.Account;
import xyz.almia.storagesystem.Equips;

public class Debug implements CommandExecutor{

	Plugin plugin;
	
	public Debug(Plugin plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			return true;
		}
		
		Player player = (Player) sender;
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();

		
		
		if(cmd.getName().equalsIgnoreCase("debuging")){
			
			player.openInventory(new Equips(character).getMenu());
		}
		
		return true;
	}

}
