package ca.damocles.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.PlayerSetup;
import ca.damocles.utils.Message;

public class Rank implements CommandExecutor{
	
	PlayerSetup playersetup = new PlayerSetup();
	Plugin plugin;
	
	public Rank(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("rank")){
			
			if(player.isOp() || (new Account(player).getLoadedCharacter().getRank().equals(ca.damocles.accountsystem.Rank.GAMEMASTER))){
			
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
				Message.sendCenteredMessage(player, " ");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank set <Player> <Rank>");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank get <Player>");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			if(args[0].equalsIgnoreCase("help")){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
				Message.sendCenteredMessage(player, " ");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank set <Player> <Rank>");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank get <Player>");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			if(args[0].equalsIgnoreCase("set")){
				if(args.length == 3){
					try{
						ca.damocles.accountsystem.Character chara = playersetup.getCharacterFromUsername(args[1]);
						try{
							ca.damocles.accountsystem.Rank rank = ca.damocles.accountsystem.Rank.valueOf(args[2].toUpperCase());
							chara.setRank(rank);
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have set "+args[1]+" to a "+args[2]+"!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						}catch(Exception e){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : Unknown Rank");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						}
					}catch(Exception e){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : Unknown Player");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}else{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : /Rank set <Player> <Rank>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("get")){
				if(args.length == 2){
					try{
						ca.damocles.accountsystem.Character target = playersetup.getCharacterFromUsername(args[1]);
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+args[1]+" is a "+ target.getRank()+"!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}catch(Exception e){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : Unknown Player");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}else{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguments : /Rank get <Player>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
			}
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"Unknown Rank command!");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			return true;
		}else{
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "Rank Help");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"You must be a GameMaster to use the Rank command!");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			return true;
		}
		}
		return true;
	}
}
