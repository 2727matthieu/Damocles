package xyz.almia.potionsystem;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.almia.itemsystem.NBTHandler;
import xyz.almia.itemsystem.ItemType.ItemTypes;
import xyz.almia.utils.RomanNumerals;

public class Potion {
	
	private PotionHandler handler = new PotionHandler();
	private ItemStack item;
	public Effect effect;
	private int color;
	private boolean splash;
	
	public Potion(Effect effect, int color, boolean splash){
		this.effect = effect;
		this.color = color;
		this.splash = splash;
	}
	
	public Potion(ItemStack item){
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
		return PotionType.valueOf(new NBTHandler(item).getStringTag("type"));
	}
	
	public boolean isSplash(){
		if(item.getType().equals(Material.SPLASH_POTION))
			return true;
		if(item.getType().equals(Material.POTION))
			return false;
		return false;
	}
	
	public ItemStack buildItemStack(){
		if(splash){
			item = new ItemStack(Material.SPLASH_POTION);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.DARK_GRAY+"Potion of "+handler.getName(effect.getType()));
			List<String> lore = new ArrayList<String>();
			if(effect.getDuration() == 20)
				lore.add(ChatColor.GRAY+"Instant "+handler.getLore(effect.getType())+" "+RomanNumerals.intToRoman(effect.getAmplifier()));
			if(effect.getDuration() > 20)
				lore.add(ChatColor.GRAY+handler.getLore(effect.getType())+" "+RomanNumerals.intToRoman(effect.getAmplifier()) + " ("+ ((effect.getDuration()/20)/60) +":"+( ( (effect.getDuration()/20) - ((effect.getDuration()/20)/60)) ) +")");
			im.setLore(lore);
			im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			item.setItemMeta(im);
			
			item = new NBTHandler(item).setIntTag("CustomPotionColor", color);
			item = new NBTHandler(item).setIntTag("amp", effect.getAmplifier());
			item = new NBTHandler(item).setIntTag("dur", effect.getDuration());
			item = new NBTHandler(item).setType(ItemTypes.POTION.toString());
			item = new NBTHandler(item).setStringTag("type", effect.getType().toString());
		}else{
			item = new ItemStack(Material.POTION);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.DARK_GRAY+"Potion of "+handler.getName(effect.getType()));
			List<String> lore = new ArrayList<String>();
			if(effect.getDuration() == 20)
				lore.add(ChatColor.GRAY+"Instant "+handler.getLore(effect.getType())+" "+RomanNumerals.intToRoman(effect.getAmplifier()));
			if(effect.getDuration() > 20)
				lore.add(ChatColor.GRAY+handler.getLore(effect.getType())+" "+RomanNumerals.intToRoman(effect.getAmplifier()) + " ("+ ((effect.getDuration()/20)/60) +":"+((effect.getDuration()/20) - ((effect.getDuration()/20)/60)) +")");
			im.setLore(lore);
			im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			item.setItemMeta(im);
			
			item = new NBTHandler(item).setIntTag("CustomPotionColor", color);
			item = new NBTHandler(item).setIntTag("amp", effect.getAmplifier());
			item = new NBTHandler(item).setIntTag("dur", effect.getDuration());
			item = new NBTHandler(item).setStringTag("type", effect.getType().toString());
			
		}
		return item;
	}
}
