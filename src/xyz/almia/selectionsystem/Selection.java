package xyz.almia.selectionsystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.almia.accountsystem.Character;
import mkremins.fanciful.FancyMessage;
import xyz.almia.accountsystem.Account;
import xyz.almia.accountsystem.AccountStatus;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.clansystem.Clan;
import xyz.almia.clansystem.Clans;
import xyz.almia.utils.Message;

public class Selection implements Listener{
	
	Plugin plugin = Cardinal.getPlugin();
	
	public void promoteToKing(){
		new BukkitRunnable(){
			public void run(){
				
				for(Player player : Bukkit.getServer().getOnlinePlayers()){
					Account account = new Account(player);					
					if(account.getStatus().equals(AccountStatus.LOGGEDIN)){
						Character character = account.getLoadedCharacter();
						
						if(character.getLevel() >= 5){
							Clans clan = character.getClan();
							Clan clanProfile = new Clan(clan);
							
							if(clan.equals(Clans.UNCLANNED)){
								return;
							}else{
								if(clanProfile.getKing() == null){
									if(clanProfile.getProposed() == null){
										
										for(Character ch : clanProfile.getRejected()){
											if(ch.getUsername().equals(character.getUsername())){
												return;
											}
										}
										
										if(clanProfile.getRejected().contains(character)){
											return;
										}else{
											clanProfile.setProposed(character);
											
	        								if(clan.equals(Clans.COLORLESS)){
	        									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	        									Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
	        									Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have been randomly selected to become the "+ ChatColor.DARK_GRAY + clan.toString().toLowerCase().substring(0, 1).toUpperCase() + clan.toString().toLowerCase().substring(1) + ChatColor.YELLOW +" King.");
	        									new FancyMessage("Accept")
	        									.command("/clan accept")
	        									.color(ChatColor.GREEN)
	        									.then("     /     ")
	            								.then("Reject")
	            								.command("/clan reject")
	            								.color(ChatColor.RED)
	            								.send(player);
	        									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	        								}else{
	        									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	        									Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
	        									Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have been randomly selected to become the "+ ChatColor.valueOf(clan.toString().toUpperCase()) + clan.toString().toLowerCase().substring(0, 1).toUpperCase() + clan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King.");
	        									new FancyMessage("Accept")
	        									.command("/clan accept")
	        									.color(ChatColor.GREEN)
	        									.then("     /     ")
	        									.then("Reject")
	        									.command("/clan reject")
	        									.color(ChatColor.RED)
	        									.send(player);
	        									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	        								}
										}
									}
								}
							}
							
							
						}
						
						
					}
					
				}
				
			}
		}.runTaskTimer(plugin, 0, 20);
	}
	
}
