package xyz.almia.itemsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import xyz.almia.itemsystem.ItemType.ItemTypes;

public class Protection {
	
	ItemStack item;

	public Protection(){
		this.item = new ItemStack(Material.EMPTY_MAP);
		setup();
	}
	
	public void setup(){
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW + "Protection Rune");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Prevents an item from being destroyed");
		lore.add(ChatColor.GRAY + "due to a failed Enchantment Rune.");
		lore.add(ChatColor.YELLOW + "Place on item to apply.");
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(im);
		
		ItemStack nbt = this.item;
		nbt = new NBTHandler(nbt).setType(ItemTypes.RUNE.toString());
		nbt = new NBTHandler(nbt).setIntTag("protect", 1);
		this.item = nbt;
	}
	
	public ItemStack getItemStack(){
		return this.item;
	}
	
}