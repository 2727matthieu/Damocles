package ca.damocles.commandsystem;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Rank;
import ca.damocles.potionsystem.Effect;
import ca.damocles.potionsystem.PotionColors;
import ca.damocles.potionsystem.PotionType;
import ca.damocles.utils.Color;
import ca.damocles.utils.Message;

public class Potion implements CommandExecutor{

	Plugin plugin;
	
	public Potion(Plugin plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		ca.damocles.accountsystem.Character character = account.getLoadedCharacter();
		if(cmd.getName().equalsIgnoreCase("potion")){
			if(character.getRank().equals(Rank.GAMEMASTER)){
				if(args.length == 0){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Potion Help");
					Message.sendCenteredMessage(player, " ");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Potion colors");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Potion types");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Potion <PotionType> <Duration> <Amplifier> <Color> <splash>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("colors")){
						String s = ChatColor.GOLD+"";
						for(PotionColors color : PotionColors.values()){
							String string = color.toString().toLowerCase();
							string = string.replace("_", " ");
							string = WordUtils.capitalizeFully(string);
							s = s+ChatColor.GOLD+WordUtils.capitalizeFully(string)+ChatColor.YELLOW+", ";
						}
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Colors");
						player.sendMessage(s);
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
					if(args[0].equalsIgnoreCase("types")){
						String s = ChatColor.GOLD+"";
						for(PotionType color : PotionType.values()){
							String string = color.toString().toLowerCase();
							string = string.replace("_", " ");
							string = WordUtils.capitalizeFully(string);
							s = s+ChatColor.GOLD+WordUtils.capitalizeFully(string)+ChatColor.YELLOW+", ";
						}
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Types");
						player.sendMessage(s);
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Potion");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Not a valid potion command.");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
				if(args.length == 5){
					try{
						PotionType type = PotionType.valueOf(args[0].toUpperCase());
						try{
							int duration = Integer.valueOf(args[1]);
							try{
								int amplifier = Integer.valueOf(args[2]);
								try{
									Color color = new Color(PotionColors.valueOf(args[3].toUpperCase()));
									try{
										boolean splash = Boolean.valueOf(args[4]);
										player.getInventory().addItem(new ca.damocles.potionsystem.Potion(new Effect(type, amplifier, duration*20), color.getInt(), splash).buildItemStack());
									}catch(Exception e){
										Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
										Message.sendCenteredMessage(player, ChatColor.BOLD + "Potion");
										Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[4] + " is not true or false.");
										Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
										return true;
									}
								}catch(Exception e){
									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
									Message.sendCenteredMessage(player, ChatColor.BOLD + "Potion");
									Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[3] + " is not a valid color.");
									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
									return true;
								}
							}catch(Exception e){
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Potion");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[2] + " is not a whole number.");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								return true;
							}
						}catch(Exception e) {
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Potion");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[1] + " is not whole number.");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						}
					}catch(Exception e) {
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Potion");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[0] + " is not a valid Potion Type.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
					return true;
					//arrow <PotionType> <Duration> <Amplifier> <Color> <Amount>
				}
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Potion");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Not a valid potion command.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}else{
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Help");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"You must be a GameMaster to use this command.");
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				return true;
			}
		}
		return true;
	}

}
