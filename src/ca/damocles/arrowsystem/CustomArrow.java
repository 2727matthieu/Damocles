package ca.damocles.arrowsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.potionsystem.Effect;
import ca.damocles.potionsystem.PotionHandler;
import ca.damocles.potionsystem.PotionType;
import ca.damocles.utils.NBTHandler;
import ca.damocles.utils.RomanNumerals;
import net.md_5.bungee.api.ChatColor;

public class CustomArrow {
	
	ItemStack item;
	PotionHandler handler = new PotionHandler();
	int color;
	String name;
	int amount;
	Effect effect;
	
	public CustomArrow(Effect effect, int amount, int color){
		this.color = color;
		this.amount = amount;
		this.effect = effect;
	}
	
	public CustomArrow(String name, int amount, int color){
		this.name = name;
		this.amount = amount;
		this.color = color;
	}
	
	public CustomArrow(ItemStack item) {
		this.item = item;
		this.effect = new Effect(PotionType.valueOf(new NBTHandler(item).getStringTag("type")), new NBTHandler(item).getIntTag("amp"), new NBTHandler(item).getIntTag("dur"));
	}
	
	public int getAmplifier(){
		return new NBTHandler(item).getIntTag("amp");
	}
	
	public int getDuration(){
		return new NBTHandler(item).getIntTag("dur");
	}
	
	public int getColor(){
		return new NBTHandler(item).getIntTag("CustomPotionColor");
	}
	
	public PotionType getType(){
		return this.effect.getType();
	}

	public ItemStack getSimple(){
		ItemStack item = new ItemStack(Material.TIPPED_ARROW, this.amount);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(this.name);
		item.setItemMeta(itemmeta);
		item = new NBTHandler(item).setIntTag("CustomPotionColor", this.color);
		return item;
	}

	public ItemStack getItemStack(){
		ItemStack item = new ItemStack(Material.TIPPED_ARROW, this.amount);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(ChatColor.DARK_GRAY+"Arrow of "+handler.getName(effect.getType()));
		List<String> lore = new ArrayList<String>();
		if(effect.getDuration() == 20)
			lore.add(ChatColor.GRAY+"Instant "+handler.getLore(effect.getType())+" "+RomanNumerals.intToRoman(effect.getAmplifier()));
		if(effect.getDuration() > 20)
			lore.add(ChatColor.GRAY+handler.getLore(effect.getType())+" "+RomanNumerals.intToRoman(effect.getAmplifier()) + " ("+ ((effect.getDuration()/20)/60) +":"+((effect.getDuration()/20) - ((effect.getDuration()/20)/60)) +")");
		itemmeta.setLore(lore);
		itemmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		item.setItemMeta(itemmeta);
		item = new NBTHandler(item).setIntTag("CustomPotionColor", color);
		item = new NBTHandler(item).setIntTag("amp", effect.getAmplifier());
		item = new NBTHandler(item).setIntTag("dur", effect.getDuration());
		item = new NBTHandler(item).setType(ItemTypes.ARROW.toString());
		item = new NBTHandler(item).setStringTag("type", effect.getType().toString());
		return item;
	}
	
}
