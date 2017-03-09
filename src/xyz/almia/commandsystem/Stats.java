package xyz.almia.commandsystem;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import mkremins.fanciful.FancyMessage;
import xyz.almia.accountsystem.Account;
import xyz.almia.accountsystem.PlayerSetup;
import xyz.almia.accountsystem.Stat;
import xyz.almia.storagesystem.Equips;
import xyz.almia.utils.Book;

public class Stats implements CommandExecutor{
	
	PlayerSetup playersetup = new PlayerSetup();
	Plugin plugin;
	
	public Stats(Plugin plugin){
		this.plugin = plugin;
	}
	
	public String getExpBar(xyz.almia.accountsystem.Character character){
		double rate = ((double)character.getExp()) / ((double)character.getExpToNextLevel());
		if(rate > 0.0 && rate < 0.1)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+""+ChatColor.RED+" :  :  :  :  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.1 && rate < 0.2)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  "+ChatColor.RED+":  :  :  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.2 && rate < 0.3)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  "+ChatColor.RED+":  :  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.3 && rate < 0.4)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  "+ChatColor.RED+":  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.4 && rate < 0.5)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  "+ChatColor.RED+":  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.5 && rate < 0.6)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  "+ChatColor.RED+":  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.6 && rate < 0.7)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  "+ChatColor.RED+":  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.7 && rate < 0.8)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  :  "+ChatColor.RED+":  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.8 && rate < 0.9)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  :  :  "+ChatColor.RED+":  : "+ChatColor.GRAY+" ]";
		if(rate > 0.9 && rate < 1.0)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  :  :  :  "+ChatColor.RED+": "+ChatColor.GRAY+" ]";
		if(rate == 1.0)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  :  :  :  : "+ChatColor.RED+""+ChatColor.GRAY+" ]";
		return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+""+ChatColor.RED+" :  :  :  :  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
	}
	
	public void openBook(Player player, xyz.almia.accountsystem.Character character){
		Book book = new Book(character.getUsername()+"'s Stats", "God");
		
		List<String> page1 = new ArrayList<String>();
		page1.add(new FancyMessage(character.getUsername()+"'s Stats")
				.color(ChatColor.DARK_GRAY)
				.style(ChatColor.BOLD)
				.style(ChatColor.UNDERLINE)
				.tooltip(
			ChatColor.GRAY + "Name: " + character.getUsername(),
			ChatColor.GRAY + "Rank: " + character.getRank().toString(),
			"",
			ChatColor.GRAY + "Level: " + character.getLevel(),
			ChatColor.GRAY+ "Exp: "+character.getExp()+"/"+(character.getLevel() * 1028),
			ChatColor.GRAY+ "Ability Points: "+character.getAbilityPoints(),
			"",
			ChatColor.GRAY+"Max Health: "+(character.getMaxHealth()),
			ChatColor.GRAY+"Max Mana: "+(character.getMaxMana()),
			ChatColor.GRAY+"Speed: "+character.getSpeed(),
			ChatColor.GRAY+"Base Physical Damage: "+character.getPhysicalDamage() ,
			ChatColor.GRAY+"Base Magic Damage: "+character.getMagicalDamage()
			)
				.then("\nRank: "+ StringUtils.capitalize(character.getRank().toString().toLowerCase()))
				.color(ChatColor.GRAY)
				.then("\nClan: "+ StringUtils.capitalize(new PlayerSetup().getClan(character).toString()))
				.color(ChatColor.GRAY)
				.then("\n")
				.then("\nLevel: "+ character.getLevel())
				.color(ChatColor.DARK_GRAY)
				.then("\n"+getExpBar(character))
				.tooltip(character.getExp()+"/"+character.getExpToNextLevel())
				.color(ChatColor.DARK_GRAY)
				.then("\n"+ChatColor.DARK_GRAY+"Ability Points: ["+ChatColor.DARK_GREEN+character.getAbilityPoints()+ChatColor.DARK_GRAY+"]\n ")
				.then("\n"+ChatColor.DARK_GRAY+"Int: ")
				.then("-")
				.color(ChatColor.RED)
				.command("/stats int down")
				.then(" "+ChatColor.GRAY+"["+character.getStat(Stat.INTELLIGENCE)+"] ")
				.then("+")
				.color(ChatColor.GREEN)
				.command("/stats int up")
				.then(ChatColor.DARK_GRAY+"  : "+ChatColor.GRAY+"("+ChatColor.GREEN+"+"+character.getShowStat(Stat.INTELLIGENCE)+ChatColor.GRAY+")")
				.then("\n"+ChatColor.DARK_GRAY+"Agi: ")
				.then("-")
				.color(ChatColor.RED)
				.command("/stats agi down")
				.then(" "+ChatColor.GRAY+"["+character.getStat(Stat.AGILITY)+"] ")
				.then("+")
				.color(ChatColor.GREEN)
				.command("/stats agi up")
				.then(ChatColor.DARK_GRAY+"  : "+ChatColor.GRAY+"("+ChatColor.GREEN+"+"+character.getShowStat(Stat.AGILITY)+ChatColor.GRAY+")")
				.then("\n"+ChatColor.DARK_GRAY+"Str: ")
				.then("-")
				.color(ChatColor.RED)
				.command("/stats str down")
				.then(" "+ChatColor.GRAY+"["+character.getStat(Stat.STRENGTH)+"] ")
				.then("+")
				.color(ChatColor.GREEN)
				.command("/stats str up")
				.then(ChatColor.DARK_GRAY+"  : "+ChatColor.GRAY+"("+ChatColor.GREEN+"+"+character.getShowStat(Stat.STRENGTH)+ChatColor.GRAY+")")
				.then("\n"+ChatColor.DARK_GRAY+"Hp: ")
				.then("-")
				.color(ChatColor.RED)
				.command("/stats hp down")
				.then(" "+ChatColor.GRAY+"["+character.getStat(Stat.HITPOINTS)+"] ")
				.then("+")
				.color(ChatColor.GREEN)
				.command("/stats hp up")
				.then(ChatColor.DARK_GRAY+"  : "+ChatColor.GRAY+"("+ChatColor.GREEN+"+"+character.getShowStat(Stat.HITPOINTS)+ChatColor.GRAY+")")
				.then("\n                  "+ChatColor.GREEN+ChatColor.UNDERLINE+ChatColor.BOLD+"APPLY")
				.command("/stats apply")
				.then("\n ")
				.then("Equips")
				.color(ChatColor.DARK_GRAY)
				.style(ChatColor.BOLD)
				.tooltip("Click on me to open the Equip Menu.")
				.command("/stats equip")
				.toJSONString());
		book.addPage(page1);
		
		book.openBook(book.build(), player);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		if(cmd.getName().equalsIgnoreCase("stats")){
			
			if(args.length == 0){
				openBook(player, character);
				return true;
			}
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("apply")){
					character.applyShowStats();
					openBook(player, character);
				}
				if(args[0].equalsIgnoreCase("equip")){
					player.openInventory(new Equips(character).getMenu());
				}
				return true;
			}
			
			if(args.length == 2){
				if(args[0].equalsIgnoreCase("int")){
					if(args[1].equalsIgnoreCase("up")){
						character.addShowStat(Stat.INTELLIGENCE);
					}
					if(args[1].equalsIgnoreCase("down")){
						character.subtractShowStat(Stat.INTELLIGENCE);
					}
					openBook(player, character);
					return true;
				}
				if(args[0].equalsIgnoreCase("agi")){
					if(args[1].equalsIgnoreCase("up")){
						character.addShowStat(Stat.AGILITY);
					}
					if(args[1].equalsIgnoreCase("down")){
						character.subtractShowStat(Stat.AGILITY);
					}
					openBook(player, character);
					return true;
				}
				if(args[0].equalsIgnoreCase("str")){
					if(args[1].equalsIgnoreCase("up")){
						character.addShowStat(Stat.STRENGTH);
					}
					if(args[1].equalsIgnoreCase("down")){
						character.subtractShowStat(Stat.STRENGTH);
					}
					openBook(player, character);
					return true;
				}
				if(args[0].equalsIgnoreCase("hp")){
					if(args[1].equalsIgnoreCase("up")){
						character.addShowStat(Stat.HITPOINTS);
					}
					if(args[1].equalsIgnoreCase("down")){
						character.subtractShowStat(Stat.HITPOINTS);
					}
					openBook(player, character);
					return true;
				}
				return true;
			}
			
			return true;
		}
		return true;
	}
}
