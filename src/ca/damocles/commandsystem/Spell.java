package ca.damocles.commandsystem;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.itemblueprints.SpellTome;
import ca.damocles.spellsystem.SpellBook;
import ca.damocles.spellsystem.Spell.Spells;
import ca.damocles.utils.Message;

public class Spell implements CommandExecutor{
	
	Plugin plugin;
	
	public Spell(Plugin plugin){
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("spell")){
			
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Spell Help");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Spell list "+ChatColor.GOLD+": for a list of all spells in the game.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Spell <Spell> <level>");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Spell book "+ChatColor.GOLD+": gives an empty spellbook.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("list")){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Spells");
					
					String spells = "";
					for(Spells spell : Spells.values()){
						
						String s = spell.toString().toLowerCase();
						s = s.replace("_", " ");
						s = StringUtils.capitaliseAllWords(s);
						
						spells = spells + ChatColor.GOLD+ s + ChatColor.YELLOW+ ", ";
					}
					Message.sendCenteredMessage(player, spells);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("book")){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Spells");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+ "You have been given an empty spellbook.");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					player.getInventory().addItem(new SpellBook().getItemStack());
					return true;
				}
			}
			
			if(args.length == 2){
				try{
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Spells");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+ "You have been given a spell tome");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Spells spell = Spells.valueOf(args[0].toUpperCase());
					int level = Integer.valueOf(args[1]);
					player.getInventory().addItem(new SpellTome(spell, level, 0).getItemStack());
					return true;
				}catch(Exception e){}
			}
			
  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
  		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Improper command usage please use /Spell");
  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
  		  return true;
			
		}
		return true;
	}

}
