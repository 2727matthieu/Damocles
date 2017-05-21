package ca.damocles.itemblueprints;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.itemsystem.Settable;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.utils.NBTHandler;

public class Ring implements Settable{
	
	Plugin plugin = Cardinal.getPlugin();
	ca.damocles.enchantsystem.Enchantment enchantclass = new ca.damocles.enchantsystem.Enchantment();
	private ItemStack item;
	
	public Ring(ItemStack item){
		this.item = item;
	}
	
	public void setup(String name, int intel, int hp, int str, int agi, int damage, int weight, int upgrades, boolean isprotected, int appliedUpgrades){
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(name + ChatColor.RESET + " " + ChatColor.BOLD + "+" + appliedUpgrades);
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GRAY+"Int: +"+ intel + " | " + ChatColor.GRAY+"Hp: +"+ hp);
		lore.add(ChatColor.GRAY+"Str: +"+ str + " | " + ChatColor.GRAY+"Agi: +"+ agi);
		lore.add("");
		lore.add(ChatColor.BLUE+"+"+damage+" Damage");
		lore.add("");
		lore.add(ChatColor.DARK_GRAY+"Weight: "+weight);
		lore.add(ChatColor.DARK_GRAY+"Number of upgrades available: "+ upgrades);
		if(isprotected){
			lore.add("");
			lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "PROTECTED");
		}
		im.setLore(lore);
		im.setUnbreakable(true);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		item.setItemMeta(im);
		
		ItemStack nbt = this.item;
		nbt = new NBTHandler(nbt).setType(ItemTypes.RING.toString());
		nbt = new NBTHandler(nbt).setStringTag("displayname", name);
		nbt = new NBTHandler(nbt).setBoolean("protected", isprotected);
		nbt = new NBTHandler(nbt).setIntTag("int", intel);
		nbt = new NBTHandler(nbt).setIntTag("str", str);
		nbt = new NBTHandler(nbt).setIntTag("hp", hp);
		nbt = new NBTHandler(nbt).setIntTag("agi", agi);
		nbt = new NBTHandler(nbt).setIntTag("damage", damage);
		nbt = new NBTHandler(nbt).setIntTag("upgrades", upgrades);
		nbt = new NBTHandler(nbt).setIntTag("appliedupgrades", appliedUpgrades);
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
	
	public String getDisplayName(){
		return new NBTHandler(item).getStringTag("displayname");
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
	
	public boolean isProtected(){
		return new NBTHandler(item).isProtected();
	}
	
	public int getAppliedUpgrades(){
		return new NBTHandler(item).getIntTag("appliedupgrades");
	}
	
	public void setAppliedUpgrades(int value){
		setup(getDisplayName(), getInt(), getHp(), getStr(), getAgi(), getDamage(), getWeight(), getUpgrades(), isProtected(), value);
	}
	
	public void protect(){
		setup(getDisplayName(), getInt(), getHp(), getStr(), getAgi(), getDamage(), getWeight(), getUpgrades(), true, getAppliedUpgrades());
	}
	
	public void unprotect(){
		setup(getDisplayName(), getInt(), getHp(), getStr(), getAgi(), getDamage(), getWeight(), getUpgrades(), false, getAppliedUpgrades());
	}
	
	public void setInt(int value){
		setup(getDisplayName(), value, getHp(), getStr(), getAgi(), getDamage(), getWeight(), getUpgrades(), isProtected(), getAppliedUpgrades());
	}
	
	public void setStr(int value){
		setup(getDisplayName(), getInt(), getHp(), value, getAgi(), getDamage(), getWeight(), getUpgrades(), isProtected(), getAppliedUpgrades());
	}
	
	public void setHp(int value){
		setup(getDisplayName(), getInt(), value, getStr(), getAgi(), getDamage(), getWeight(), getUpgrades(), isProtected(), getAppliedUpgrades());
	}
	
	public void setAgi(int value){
		setup(getDisplayName(), getInt(), getHp(), getStr(), value, getDamage(), getWeight(), getUpgrades(), isProtected(), getAppliedUpgrades());
	}
	
	public void setDamage(int value){
		setup(getDisplayName(), getInt(), getHp(), getStr(), getAgi(), value, getWeight(), getUpgrades(), isProtected(), getAppliedUpgrades());
	}
	
	public void setWeight(int value){
		setup(getDisplayName(), getInt(), getHp(), getStr(), getAgi(), getDamage(), value, getUpgrades(), isProtected(), getAppliedUpgrades());
	}
	
	public void setUpgrades(int value){
		setup(getDisplayName(), getInt(), getHp(), getStr(), getAgi(), getDamage(), getWeight(), value, isProtected(), getAppliedUpgrades());
	}
	
	public ItemStack getItemStack(){
		return this.item;
	}
	
}
