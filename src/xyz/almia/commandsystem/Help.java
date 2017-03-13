package xyz.almia.commandsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.almia.accountsystem.Account;
import xyz.almia.accountsystem.Rank;
import xyz.almia.utils.Message;

public class Help implements CommandExecutor{
	
	Plugin plugin;
	
	public Help(Plugin plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		if(cmd.getName().equalsIgnoreCase("help")){
			
			if(args.length == 1){
				if(character.getRank().equals(Rank.GAMEMASTER)){
					if(args[0].equals("admin")){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Admin Commands");
						Message.sendCenteredMessage(player, " ");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Potion "+ChatColor.GOLD+ ": For all the Potion Commands.");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Arrow "+ChatColor.GOLD+ ": For all the Arrow Commands.");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rank "+ChatColor.GOLD+ ": For all the Rank Commands.");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Money "+ChatColor.GOLD+ ": Admin money command.");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Cardinal "+ChatColor.GOLD+ ": generates custom weapons.");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Rune "+ChatColor.GOLD+ ": For all the Enchant Commands.");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Heal <Character>"+ChatColor.GOLD+ ": Heals specified player.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return true;
					}
				}
			}
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			Message.sendCenteredMessage(player, ChatColor.BOLD + "Player Commands");
			Message.sendCenteredMessage(player, " ");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"/logout "+ChatColor.GOLD+ ": Logs out of your character.");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"/balance "+ChatColor.GOLD+ ": Displays users bank balance.");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Clan "+ChatColor.GOLD+ ": For all the Clan Commands.");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Stats "+ChatColor.GOLD+ ": For your players Stats.");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Guild "+ChatColor.GOLD+ ": Not implemented.");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Party "+ChatColor.GOLD+ ": Not implemented.");
			Message.sendCenteredMessage(player, ChatColor.YELLOW+"/Trade "+ChatColor.GOLD+ ": Not implemented.");
			Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			return true;
		}
		return true;
	}
	
}
