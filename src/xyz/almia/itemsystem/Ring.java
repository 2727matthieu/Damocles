package xyz.almia.itemsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.itemsystem.ItemType.ItemTypes;

public class Ring {
	
	Plugin plugin = Cardinal.getPlugin();
	xyz.almia.enchantsystem.Enchantment enchantclass = new xyz.almia.enchantsystem.Enchantment();
	private ItemStack item;
	
	public Ring(ItemStack item){
		this.item = item;
	}
	
	public void setup(int intel, int hp, int str, int agi, int damage, int weight, int upgrades){
		ItemMeta im = this.item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GRAY+"Int: +"+ intel + " | " + ChatColor.GRAY+"Hp: +"+ hp);
		lore.add(ChatColor.GRAY+"Str: +"+ str + " | " + ChatColor.GRAY+"Agi: +"+ agi);
		lore.add("");
		lore.add(ChatColor.BLUE+"+"+damage+" Damage");
		lore.add("");
		lore.add(ChatColor.DARK_GRAY+"Weight: "+weight);
		lore.add(ChatColor.DARK_GRAY+"Number of upgrades available: "+ upgrades);
		lore.add("");
		im.setLore(lore);
		im.setUnbreakable(true);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		item.setItemMeta(im);
		
		ItemStack nbt = this.item;
		nbt = new NBTHandler(nbt).setType(ItemTypes.RING.toString());
		nbt = new NBTHandler(nbt).setIntTag("int", intel);
		nbt = new NBTHandler(nbt).setIntTag("str", str);
		nbt = new NBTHandler(nbt).setIntTag("hp", hp);
		nbt = new NBTHandler(nbt).setIntTag("agi", agi);
		nbt = new NBTHandler(nbt).setIntTag("damage", damage);
		nbt = new NBTHandler(nbt).setIntTag("upgrades", upgrades);
		this.item = nbt;
	}
	
	public void setID(int id){
		ItemMeta im = this.item.getItemMeta();
		item.setDurability((short)id);
		im.setUnbreakable(true);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(im);
	}
	
	public int getInt(){
		return new NBTHandler(item).getIntTag("int");
	}
	
	public int getStr(){
		return new NBTHandler(item).getIntTag("str");
	}
	
	public int getHp(){
		return new NBTHandler(item).getIntTag("hp");
	}
	
	public int getAgi(){
		return new NBTHandler(item).getIntTag("agi");
	}
	
	public int getWeight(){
		return new NBTHandler(item).getIntTag("weight");
	}

	public int getDamage(){
		return new NBTHandler(item).getIntTag("damage");
	}

	public int getUpgrades(){
		return new NBTHandler(item).getIntTag("upgrades");
	}
	
	public void setInt(int value){
		setup(value, getHp(), getStr(), getAgi(), getDamage(), getWeight(), getUpgrades());
	}
	
	public void setStr(int value){
		setup(getInt(), getHp(), value, getAgi(), getDamage(), getWeight(), getUpgrades());
	}
	
	public void setHp(int value){
		setup(getInt(), value, getStr(), getAgi(), getDamage(), getWeight(), getUpgrades());
	}
	
	public void setAgi(int value){
		setup(getInt(), getHp(), getStr(), value, getDamage(), getWeight(), getUpgrades());
	}
	
	public void setDamage(int value){
		setup(getInt(), getHp(), getStr(), getAgi(), value, getWeight(), getUpgrades());
	}
	
	public void setWeight(int value){
		setup(getInt(), getHp(), getStr(), getAgi(), getDamage(), value, getUpgrades());
	}
	
	public void setUpgrades(int value){
		setup(getInt(), getHp(), getStr(), getAgi(), getDamage(), getWeight(), value);
	}
	
	public ItemStack getItemStack(){
		return this.item;
	}
	
}
