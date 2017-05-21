package ca.damocles.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.PlayerSetup;
import ca.damocles.accountsystem.Rank;
import ca.damocles.utils.Message;

public class Heal implements CommandExecutor{
	
	PlayerSetup playersetup = new PlayerSetup();
	Plugin plugin;
	
	public Heal(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		ca.damocles.accountsystem.Character character = account.getLoadedCharacter();
		if(cmd.getName().equalsIgnoreCase("heal")){
			if(character.getRank().equals(Rank.GAMEMASTER)){
				if(args.length == 0){
					character.setHealth(character.getMaxHealth());
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have been healed!");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}else if(args.length == 1){
					try{
						ca.damocles.accountsystem.Character target = playersetup.getCharacterFromUsername(args[0]);
						target.setHealth(target.getMaxHealth());
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have healed " + target.getUsername() + " !");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(target.getPlayer(), ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(target.getPlayer(), ChatColor.BOLD + "Help");
						Message.sendCenteredMessage(target.getPlayer(), ChatColor.YELLOW+"You have been healed!");
						Message.sendCenteredMessage(target.getPlayer(), ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}catch(Exception e){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Player " + args[0] + " does not exist or is offline.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}else{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Improper command usage, use /heal <Player>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
			}else{
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Only GameMasters can use this command!");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
		}
		return true;
	}
}
