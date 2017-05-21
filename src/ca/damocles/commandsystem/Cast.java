package ca.damocles.commandsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.PlayerSetup;
import ca.damocles.itemsystem.ItemType;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.spellsystem.SpellBook;

public class Cast implements CommandExecutor{
	
	Plugin plugin;
	PlayerSetup playersetup = new PlayerSetup();
	
	public Cast(Plugin plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		//Account account = new Account(player);
		//xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
		if(cmd.getName().equalsIgnoreCase("cast")){
			//new xyz.almia.spellsystem.Cast(Spells.WINGS, character);
			ItemStack hand = player.getInventory().getItemInMainHand();
			if(new ItemType(hand).getType().equals(ItemTypes.SPELLBOOK)){
				SpellBook book = new SpellBook(player.getInventory().getItemInMainHand());
				player.openInventory(book.getSpellventory());
			}
		}
		return true;
	}
}
