package ca.damocles.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.EventCanceller;
import ca.damocles.accountsystem.Rank;
import ca.damocles.arrowsystem.CustomArrow;
import ca.damocles.potionsystem.Effect;
import ca.damocles.potionsystem.PotionColors;
import ca.damocles.potionsystem.PotionType;
import ca.damocles.utils.Color;
import ca.damocles.utils.Message;

public class Arrow implements CommandExecutor{

	Plugin plugin;
	
	public Arrow(Plugin plugin){
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
		if(cmd.getName().equalsIgnoreCase("arrow")){
			if(character.getRank().equals(Rank.GAMEMASTER)){
				if(args.length == 0){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Arrows Help");
					Message.sendCenteredMessage(player, " ");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Arrow colors");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Arrow <PotionType> <Duration> <Amplifier> <Color> <Amount>");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					return true;
				}
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("colors")){
						player.openInventory(EventCanceller.getArrowColors());
						return true;
					}
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Arrows");
					Message.sendCenteredMessage(player, ChatColor.YELLOW+"Not a valid arrow command.");
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
										int amount = Integer.valueOf(args[4]);
										player.getInventory().addItem(new CustomArrow(new Effect(type, amplifier, duration*20), amount, color.getInt()).getItemStack());
									}catch(Exception e){
										Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
										Message.sendCenteredMessage(player, ChatColor.BOLD + "Arrows");
										Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[4] + " is not a valid amount.");
										Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
										return true;
									}
								}catch(Exception e){
									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
									Message.sendCenteredMessage(player, ChatColor.BOLD + "Arrows");
									Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[3] + " is not a valid color.");
									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
									return true;
								}
							}catch(Exception e){
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Arrows");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[2] + " is not a whole number.");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								return true;
							}
						}catch(Exception e) {
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Arrows");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[1] + " is not whole number.");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						}
					}catch(Exception e) {
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Arrows");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+ args[0] + " is not a valid Potion Type.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
					return true;
					//arrow <PotionType> <Duration> <Amplifier> <Color> <Amount>
				}
				Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				Message.sendCenteredMessage(player, ChatColor.BOLD + "Arrows");
				Message.sendCenteredMessage(player, ChatColor.YELLOW+"Not a valid arrow command.");
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
