package xyz.almia.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import xyz.almia.accountsystem.Account;
import xyz.almia.accountsystem.PlayerSetup;
import xyz.almia.accountsystem.Rank;
import xyz.almia.storagesystem.Treasury;
import xyz.almia.utils.Message;

public class Money implements CommandExecutor{
	
	PlayerSetup playersetup = new PlayerSetup();
	Plugin plugin;
	
	public Money(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		if(cmd.getName().equalsIgnoreCase("money")){
			
			if(!(character.getRank().equals(Rank.GAMEMASTER))){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Must be a GAMEMASTER to use /money");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Money Commands");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/money set <Player> <Amount>");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}else if(args.length == 3){
				if(args[0].equalsIgnoreCase("set")){
					try{
						xyz.almia.accountsystem.Character target = playersetup.getCharacterFromUsername(args[1]);
						try{
							int amount = Integer.valueOf(args[2]);
							
							new Treasury(target).setBalance(amount);
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Money");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ target.getUsername() + "'s balance has been set to " + args[2]);
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(target.getPlayer(), ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(target.getPlayer(), ChatColor.BOLD + "Money");
							Message.sendCenteredMessage(target.getPlayer(), ChatColor.YELLOW+ target.getUsername() + "'s balance has been set to " + args[2]);
							Message.sendCenteredMessage(target.getPlayer(), ChatColor.GREEN+"----------------------------------------------------");
							return true;
							
						}catch(Exception e){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Money");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[2] + " is not a number.");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						}
					}catch(Exception e) {
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Money");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[1] + " does not exist or is offline.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}else{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Improper command usage, use /money set <Player> <Amount>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
			}else{
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Improper command usage, use /money set <Player> <Amount>");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
		}
		return true;
	}
}
