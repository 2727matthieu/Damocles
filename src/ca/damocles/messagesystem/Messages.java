package ca.damocles.messagesystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Profession;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.utils.Message;

public class Messages {
	
	Plugin plugin = Cardinal.getPlugin();
	
	public static void proflevelUp(Profession prof, Player player){
		int nextlvl = new Account(player).getLoadedCharacter().getPLevel(prof) + 1;
		int lvl = new Account(player).getLoadedCharacter().getPLevel(prof);
		Message.sendCenteredMessage(player, ChatColor.GREEN + "-----------------------------------------------------");
		Message.sendCenteredMessage(player, ChatColor.BOLD + prof.toString().toLowerCase().substring(0, 1).toUpperCase() + prof.toString().toLowerCase().substring(1) +" Summary");
		Message.sendCenteredMessage(player, ChatColor.YELLOW + "You are now level " + ChatColor.GOLD + lvl + ChatColor.YELLOW + "!");
		Message.sendCenteredMessage(player, ChatColor.YELLOW + "You need "+ ChatColor.GOLD + (nextlvl * 100) + " xp" + ChatColor.YELLOW + " to reach level " + ChatColor.GOLD + nextlvl);
		Message.sendCenteredMessage(player, " ");
		Message.sendCenteredMessage(player, ChatColor.BOLD + "No active xp boost!");
		Message.sendCenteredMessage(player, ChatColor.GREEN + "-----------------------------------------------------");
	}
	
	public static void levelUp(Player player){
		int nextlvl = new Account(player).getLoadedCharacter().getLevel() + 1;
		int lvl = new Account(player).getLoadedCharacter().getLevel();
		Message.sendCenteredMessage(player, ChatColor.GREEN + "-----------------------------------------------------");
		Message.sendCenteredMessage(player, ChatColor.BOLD + "Experience Summary");
		Message.sendCenteredMessage(player, ChatColor.YELLOW + "You are now level " + ChatColor.GOLD + lvl + ChatColor.YELLOW + "!");
		Message.sendCenteredMessage(player, ChatColor.YELLOW + "You need "+ ChatColor.GOLD + (nextlvl * 1028) + " xp" + ChatColor.YELLOW + " to reach level " + ChatColor.GOLD + nextlvl);
		Message.sendCenteredMessage(player, ChatColor.YELLOW +"You have "+ChatColor.GOLD+"4" +ChatColor.YELLOW+" available Ability Points!");
		Message.sendCenteredMessage(player, " ");
		Message.sendCenteredMessage(player, ChatColor.BOLD + "No active xp boost!");
		Message.sendCenteredMessage(player, ChatColor.GREEN + "-----------------------------------------------------");
	}
	
}
