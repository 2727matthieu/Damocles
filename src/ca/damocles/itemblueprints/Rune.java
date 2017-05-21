package ca.damocles.itemblueprints;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ca.damocles.enchantsystem.Enchantments;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.utils.NBTHandler;
import ca.damocles.utils.RomanNumerals;

public class Rune {
	
	ca.damocles.enchantsystem.Enchantment enchantclass = new ca.damocles.enchantsystem.Enchantment();
	private ItemStack item;
	
	public Rune(Enchantments enchant, int level, int success, int destroy){
		this.item = new ItemStack(Material.NETHER_STAR);
		setup(enchant, level, success, destroy);
	}
	
	public Enchantments getEnchant(){
		return Enchantments.valueOf(new NBTHandler(item).getStringTag("enchant"));
	}
	
	public int getLevel(){
		return new NBTHandler(item).getIntTag("level");
	}
	
	public int getSuccess(){
		return new NBTHandler(item).getIntTag("success");
	}
	
	public int getDestroy(){
		return new NBTHandler(item).getIntTag("destroy");
	}
	
	public void setEnchant(Enchantments value){
		setup(value, getLevel(), getSuccess(), getDestroy());
	}
	
	public void setLevel(int value){
		setup(getEnchant(), value, getSuccess(), getDestroy());
	}
	
	public void setSuccess(int value){
		setup(getEnchant(), getLevel(), value, getDestroy());
	}
	
	public void setDestroy(int value){
		setup(getEnchant(), getLevel(), getSuccess(), value);
	}
	
	public void setup(Enchantments enchant, int level, int success, int destroy){
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW + "Rune of " + ChatColor.UNDERLINE + enchantclass.getName(enchant) + ChatColor.RESET + " " + ChatColor.YELLOW + RomanNumerals.intToRoman(level));
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.GREEN + "" + success + "% Success Rate");
		lore.add(ChatColor.RED + "" + destroy + "% Destroy Rate On Fail.");
		lore.add(ChatColor.YELLOW + "Level " + ChatColor.GOLD + "" + level);
		lore.add(ChatColor.GOLD + "" + enchantclass.getType(enchant).toString() + " Enchantment");
		lore.add(ChatColor.GRAY + "Place rune on item to enchant.");
		
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(im);
		
		ItemStack nbt = this.item;
		nbt = new NBTHandler(nbt).setType(ItemTypes.RUNE.toString());
		nbt = new NBTHandler(nbt).setStringTag("enchant", enchant.toString());
		nbt = new NBTHandler(nbt).setIntTag("level", level);
		nbt = new NBTHandler(nbt).setIntTag("success", success);
		nbt = new NBTHandler(nbt).setIntTag("destroy", destroy);
		this.item = nbt;
	}
	
	public ItemStack getItemStack(){
		return this.item;
	}
	
}
