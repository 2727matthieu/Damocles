package xyz.almia.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.almia.enchantsystem.Enchantments;
import xyz.almia.itemsystem.Protection;
import xyz.almia.itemsystem.SlotRune;
import xyz.almia.utils.Message;

public class Rune implements CommandExecutor{
	
	xyz.almia.enchantsystem.Enchantment enchantclass = new xyz.almia.enchantsystem.Enchantment();
	xyz.almia.enchantsystem.Rune rune = new xyz.almia.enchantsystem.Rune();
	Plugin plugin;
	
	public Rune(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("rune")){
			
			if(args.length == 0){
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Rune Help");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Rune list "+ChatColor.GOLD+": for a list of Runes.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Rune <Enchantment> <level> <Success Rate> <Destroy Rate>");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Rune slot <Amount> "+ChatColor.GOLD+": gives a Slot Rune.");
				Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Rune protection "+ChatColor.GOLD+": gives a Protection Rune.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("list")){
				if(args.length == 1){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Runes");
					
					String enchants = "";
					for(Enchantments enchant : Enchantments.values()){
						enchants = enchants + ChatColor.GOLD+ enchantclass.getName(enchant) + ChatColor.YELLOW+ ", ";
					}
					Message.sendCenteredMessage(player, enchants);
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguemnts for proper usage use /Rune");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
				}
			}else if(args[0].equalsIgnoreCase("slot")){
				if(args.length == 2){
					try{
						int slots = Integer.valueOf(args[1]);
						player.getInventory().addItem(new SlotRune(slots).getItemStack());
						return true;
					}catch(Exception e){
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+args[1]+" is not a whole number or your inventory is full!");
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
					}
				}else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguemnts for proper usage use /Rune");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
				}
			}else if(args[0].equalsIgnoreCase("protection")){
				if(args.length == 1){
					try{
						player.getInventory().addItem(new Protection().getItemStack());
						return true;
					}catch(Exception e){
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Your inventory is full.");
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
					}
				}else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguemnts for proper usage use /Rune");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
				}
			}
			
			
			for(Enchantments enchant : Enchantments.values()){
				if(args[0].equalsIgnoreCase(enchant.toString())){
					try{
						int level = Integer.valueOf(args[1]);
						int success = Integer.valueOf(args[2]);
						int destroy = Integer.valueOf(args[3]);
						if(level > enchantclass.getMaxLevel(enchant))
						if(level > enchantclass.getMaxLevel(enchant)){
							player.sendMessage(ChatColor.YELLOW + "Error: The max level for " + enchantclass.getName(enchant) + " is " + enchantclass.getMaxLevel(enchant));
							return true;
						}
						player.getInventory().addItem(new xyz.almia.itemsystem.Rune(enchant, level, success, destroy).getItemStack());
						return true;
					}catch(NumberFormatException e) {
				  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				  		  Message.sendCenteredMessage(player, ChatColor.YELLOW+args[1]+ ", "+args[2]+", "+args[3]+" One or allof these are not whole numbers!");
				  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				  		  return true;
					}
				}
			}
			
  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
  		  Message.sendCenteredMessage(player, ChatColor.YELLOW+args[0]+" is not a rune, or a valid sub-command of /rune");
  		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
  		  return true;

		}
		return true;
	}
}
