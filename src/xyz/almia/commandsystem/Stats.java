package xyz.almia.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.almia.accountsystem.Account;
import xyz.almia.accountsystem.PlayerSetup;
import xyz.almia.menu.PlayerMenu;
import xyz.almia.utils.Message;

public class Stats implements CommandExecutor{
	
	PlayerSetup playersetup = new PlayerSetup();
	Plugin plugin;
	
	public Stats(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		if(cmd.getName().equalsIgnoreCase("stats")){
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + character.getUsername() + " Stats");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Your current Level is " + ChatColor.GOLD + character.getLevel());
			Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Your current xp is " + ChatColor.GOLD + character.getExp()+ ChatColor.YELLOW+ " / "+ ChatColor.GOLD + (character.getLevel() * 1028));
			Message.sendCenteredMessage(player, ChatColor.YELLOW+ "You currently belong to the "+ playersetup.getClan(character).toString().toLowerCase().substring(0, 1).toUpperCase() + playersetup.getClan(character).toString().toLowerCase().substring(1) + " clan.");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+ "your position is " + ChatColor.GRAY + playersetup.getClanRank(character).toString().toLowerCase().substring(0, 1).toUpperCase() + playersetup.getClanRank(character).toString().toLowerCase().substring(1));	
			Message.sendCenteredMessage(player, " ");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "No Active Bonus' !");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			player.openInventory(PlayerMenu.createMenu(player));
			return true;
		}
		return true;
	}
}
