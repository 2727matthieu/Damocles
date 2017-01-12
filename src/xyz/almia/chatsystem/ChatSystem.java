package xyz.almia.chatsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import mkremins.fanciful.FancyMessage;
import xyz.almia.accountsystem.Account;

public class ChatSystem implements Listener{
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		
		event.getPlayer().setDisplayName(new Account(event.getPlayer()).getLoadedCharacter().getTitle()+new Account(event.getPlayer()).getLoadedCharacter().getUsername());
		
		if(event.getMessage().contains("@hand")){
			if(event.getPlayer().getInventory().getItemInMainHand() != null){
				event.setCancelled(true);
				String message = event.getMessage();
				message = message.replace("@hand", "~");
				List<String> parts = new ArrayList<String>();
				parts = Arrays.asList(message.split("~"));
				for(Player player : Bukkit.getOnlinePlayers()){
					new FancyMessage(ChatColor.WHITE+"<"+event.getPlayer().getDisplayName()+ChatColor.WHITE+"> ")
					.then(parts.get(0))
					.color(ChatColor.WHITE)
					.then("SHOW")
					.color(ChatColor.YELLOW)
					.style(ChatColor.BOLD)
					.style(ChatColor.UNDERLINE)
					.itemTooltip(event.getPlayer().getInventory().getItemInMainHand())
					.then(parts.get(1))
					.color(ChatColor.WHITE)
					.send(player);
				}
			}
		}
		
		//Clans clan = Clan.getManager().getClan(event.getPlayer());
		//event.getPlayer().setDisplayName(
				//ChatColor.YELLOW+"[♚ "+ ChatColor.WHITE+clan.toString().toLowerCase().substring(0, 1).toUpperCase() + clan.toString().toLowerCase().substring(1)
				//+ ChatColor.YELLOW+"]["+ChatColor.GOLD+new PlayerData(event.getPlayer()).getLevel()+ChatColor.YELLOW+"] "+ChatColor.WHITE+event.getPlayer().getName());
	}
}
