package ca.damocles.accountsystem;

import org.bukkit.ChatColor;

public enum Rank {
	
	GAMEMASTER, MODERATOR, SUBSCRIBER, PLAYER;
	
	
	public static String getTitle(Rank rank){
		switch(rank){
		case GAMEMASTER:
			return ChatColor.RED+"GM"+ChatColor.WHITE+">";
		case MODERATOR:
			return ChatColor.YELLOW+"M"+ChatColor.WHITE+">";
		case PLAYER:
			return ChatColor.GRAY+"P"+ChatColor.WHITE+">";
		case SUBSCRIBER:
			return ChatColor.GREEN+"S"+ChatColor.WHITE+">";
		default:
			return ChatColor.GRAY+"P"+ChatColor.WHITE+">";
		}
	}
	
}
