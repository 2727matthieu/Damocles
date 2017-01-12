package xyz.almia.itemsystem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class CustomArrow {
	
	int color;
	String name;
	int amount;
	PotionEffect effect;
	
	public CustomArrow(String name, int amount, int color, PotionEffect effect){
		this.color = color;
		this.name = name;
		this.amount = amount;
		this.effect = effect;
	}
	
	public ItemStack getItemStack(){
		ItemStack item = new ItemStack(Material.TIPPED_ARROW, this.amount);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(this.name);
		item.setItemMeta(itemmeta);
		item = new NBTHandler(item).setIntTag("CustomPotionColor", color);
		item = new NBTHandler(item).setPotionEffect(effect);
		return item;
	}
	
}
