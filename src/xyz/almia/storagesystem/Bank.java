package xyz.almia.storagesystem;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.almia.menu.MenuItem;
import xyz.almia.storagesystem.Equips.Slot;

public class Bank {
	
	xyz.almia.accountsystem.Character character;
	ItemStack money;
	ItemStack bank;
	ItemStack empty = MenuItem.createItem(ChatColor.GRAY+"Empty", "", Material.STAINED_GLASS_PANE);
	
	public Bank(xyz.almia.accountsystem.Character character){
		this.character = character;
		this.money = MenuItem.createBetterItem(ChatColor.GREEN+"Balance", Arrays.asList(new String[] { ChatColor.GREEN+"$"+ChatColor.GRAY+""+ new Treasury(character).getBalance() }), Material.EMERALD);
		this.bank = character.getEquip(Slot.BANK);
	}
	
	public Inventory getMenu(){
		Inventory menu = Bukkit.createInventory(null, 9, character.getUsername()+"'s Bank");
		menu.setItem(0, empty);
		menu.setItem(1, empty);
		menu.setItem(2, empty);
		menu.setItem(3, money);
		menu.setItem(4, empty);
		if(bank == null || bank.getType().equals(Material.AIR)){
			bank = MenuItem.createItem(ChatColor.GOLD+"Empty Bank", ChatColor.GRAY+"No Bank Equipped.", Material.WHITE_SHULKER_BOX);
		}
		menu.setItem(5, bank);
		menu.setItem(6, empty);
		menu.setItem(7, empty);
		menu.setItem(8, empty);
		return menu;
	}
	
	
	
}
