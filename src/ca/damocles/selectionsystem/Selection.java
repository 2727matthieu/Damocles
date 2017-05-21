package ca.damocles.selectionsystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.accountsystem.Character;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.clansystem.Clan;
import ca.damocles.clansystem.Clans;
import ca.damocles.utils.Message;
import mkremins.fanciful.FancyMessage;

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
