package xyz.almia.itemsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import xyz.almia.itemsystem.ItemType.ItemTypes;

public class SlotRune {
	
	ItemStack item;
	int slots;

	public SlotRune(int slots){
		this.item = new ItemStack(Material.EYE_OF_ENDER);
		this.slots = slots;
		setup(slots);
	}
	
	public void setSlots(int value){
		setup(value);
	}
	
	public int getSlots(){
		return this.slots;
	}
	
	public void setup(int slots){
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW + "Slot Rune");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "+" + slots + " Slots");
		lore.add(ChatColor.GRAY + "Place rune on item to apply slots.");
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(im);
		
		ItemStack nbt = this.item;
		nbt = new NBTHandler(nbt).setType(ItemTypes.RUNE.toString());
		nbt = new NBTHandler(nbt).setIntTag("slots", slots);
		this.item = nbt;
	}
	
	public ItemStack getItemStack(){
		return this.item;
	}
	
}
