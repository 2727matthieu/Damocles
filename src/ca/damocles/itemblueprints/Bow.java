package ca.damocles.itemblueprints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.enchantsystem.Enchantments;
import ca.damocles.itemsystem.Settable;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.utils.NBTHandler;
import ca.damocles.utils.RomanNumerals;

public class Bow implements Settable{
	
	Plugin plugin = Cardinal.getPlugin();
	ca.damocles.enchantsystem.Enchantment enchantclass = new ca.damocles.enchantsystem.Enchantment();
	private ItemStack item;
	
	public Bow(ItemStack item){
		this.item = item;
	}
	
	public void setup(HashMap<Enchantments, Integer> enchants, String name, int slots, int intel, int str, int hp, int agi ,int damage, int reforges, int weight, int upgrades, boolean isprotected, int durability, int maxdurability, int appliedUpgrades){
		
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(name + ChatColor.RESET + " " + ChatColor.BOLD + "+" + appliedUpgrades);
		List<String> lore = new ArrayList<String>();
		lore.add("");
		
		if(!(enchants == null)){
			for(Map.Entry<Enchantments, Integer> entry : enchants.entrySet()){
				lore.add(ChatColor.GRAY+ enchantclass.getName(entry.getKey()) + " " + RomanNumerals.intToRoman(entry.getValue()));
			}
		}
		lore.add(ChatColor.GOLD+""+slots+" Slots");
		lore.add("");
		
		lore.add(ChatColor.GRAY+"Int: +"+ intel + " | " + ChatColor.GRAY+"Hp: +"+ hp);
		lore.add(ChatColor.GRAY+"Str: +"+ str + " | " + ChatColor.GRAY+"Agi: +"+ agi);
		lore.add("");
		lore.add(ChatColor.BLUE+"+"+damage+" Damage");
		lore.add("");
		lore.add(ChatColor.DARK_GRAY+"Weight: "+weight);
		lore.add(ChatColor.DARK_GRAY+"Number of upgrades available: "+ upgrades);
		lore.add(ChatColor.DARK_GRAY+"Number of reforges available: "+ reforges);
		lore.add("");
		lore.add(ChatColor.DARK_GRAY+"Durability: " + durability + "/" + maxdurability);
		
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
		
		nbt = new NBTHandler(nbt).setType(ItemTypes.BOW.toString());
		nbt = new NBTHandler(nbt).setListEmpty("enchants");
		for(Enchantments enchant : enchants.keySet()){
			nbt = new NBTHandler(nbt).addEnchant(enchant, enchants.get(enchant));
		}
		nbt = new NBTHandler(nbt).setStringTag("displayname", name);
		nbt = new NBTHandler(nbt).setIntTag("slots", slots);
		nbt = new NBTHandler(nbt).setIntTag("int", intel);
		nbt = new NBTHandler(nbt).setIntTag("str", str);
		nbt = new NBTHandler(nbt).setIntTag("hp", hp);
		nbt = new NBTHandler(nbt).setIntTag("agi", agi);
		nbt = new NBTHandler(nbt).setIntTag("damage", damage);
		nbt = new NBTHandler(nbt).setIntTag("reforges", reforges);
		nbt = new NBTHandler(nbt).setIntTag("weight", weight);
		nbt = new NBTHandler(nbt).setIntTag("upgrades", upgrades);
		nbt = new NBTHandler(nbt).setIntTag("appliedupgrades", appliedUpgrades);
		nbt = new NBTHandler(nbt).setBoolean("protected", isprotected);
		nbt = new NBTHandler(nbt).setIntTag("durability", durability);
		nbt = new NBTHandler(nbt).setIntTag("maxdurability", maxdurability);
		
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
	
	@SuppressWarnings("deprecation")
	public String getName(){
		if(item.getItemMeta().hasDisplayName())
			return item.getItemMeta().getDisplayName();
		return StringUtils.capitaliseAllWords(item.getType().toString().toLowerCase());
	}
	
	public String getDisplayName(){
		return new NBTHandler(item).getStringTag("displayname");
	}
	
	public void setMaxDurability(int maxdurability){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), maxdurability, getAppliedUpgrades());
	}
	
	public void setDurability(int durability){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), durability, getMaxDurability(), getAppliedUpgrades());
	}
	
	public int getMaxDurability(){
		return new NBTHandler(item).getIntTag("maxdurability");
	}
	
	public int getDurability(){
		return new NBTHandler(item).getIntTag("durability");
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
	
	public int getUpgrades(){
		return new NBTHandler(item).getIntTag("upgrades");
	}
	
	public int getAppliedUpgrades(){
		return new NBTHandler(item).getIntTag("appliedupgrades");
	}
	
	public void setAppliedUpgrades(int amount){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), amount);
	}
	
	public void setInt(int amount){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), amount, getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public void setStr(int amount){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), amount, getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public void setHp(int amount){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), amount, getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public void setAgi(int amount){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), amount, getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public void setUpgrades(int amount){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), amount, isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public ItemStack getItemStack(){
		return item;
	}
	
	public void addEnchant(Enchantments enchant, int level){
		HashMap<Enchantments, Integer> enchantments = getEnchantsAndLevel();
		enchantments.put(enchant, level);
		setup(enchantments, getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public void removeEnchant(Enchantments enchant){
		HashMap<Enchantments, Integer> enchantments = getEnchantsAndLevel();
		enchantments.remove(enchant);
		setup(enchantments, getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}

	public int getWeight(){
		return new NBTHandler(item).getIntTag("weight");
	}

	public void setWeight(int weight){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), weight, getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public void setReforges(int reforges){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), reforges, getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public int getReforges(){
		return new NBTHandler(item).getIntTag("reforges");
	}
	
	public void setDamage(int damage){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), damage, getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public int getDamage(){
		return new NBTHandler(item).getIntTag("damage");
	}
	
	public void setSlots(int slots){
		setup(getEnchantsAndLevel(), getDisplayName(), slots, getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), isProtected(), getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public int getSlots(){
		return new NBTHandler(item).getIntTag("slots");
	}
	
	public void unProtect(){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), false, getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public void protect(){
		setup(getEnchantsAndLevel(), getDisplayName(), getSlots(), getInt(), getStr(), getHp(), getAgi(), getDamage(), getReforges(), getWeight(), getUpgrades(), true, getDurability(), getMaxDurability(), getAppliedUpgrades());
	}
	
	public boolean isProtected(){
		return new NBTHandler(item).isProtected();
	}
	
	public boolean isItemSet(){
		if(item.hasItemMeta()){
			return true;
		}else{
			return false;
		}
	}
	
	public HashMap<Enchantments, Integer> getEnchantsAndLevel(){
		return new NBTHandler(item).getEnchants();
	}
	
}
