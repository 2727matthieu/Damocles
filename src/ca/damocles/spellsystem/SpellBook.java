package ca.damocles.spellsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ca.damocles.castsystem.CastingManager;
import ca.damocles.configclasses.ConfigManager;
import ca.damocles.itemblueprints.SpellTome;
import ca.damocles.itemsystem.ItemType;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.menusystem.MenuItem;
import ca.damocles.utils.Data;
import ca.damocles.utils.NBTHandler;
import ca.damocles.utils.RomanNumerals;

public class SpellBook {
	
	int slots = 5;
	ItemStack item = new ItemStack(Material.BOOK);
	UUID uuid;
	List<SpellTome> spells;
	FileConfiguration config;
	
	public SpellBook(){
		this.uuid = UUID.randomUUID();
		this.spells = new ArrayList<SpellTome>();
		ConfigManager.load(this.uuid.toString()+".yml", "data/spellbooks");
		this.config = ConfigManager.get(this.uuid.toString()+".yml");
		a();
	}
	
	public SpellBook(UUID uuid){
		this.uuid = uuid;
		ConfigManager.load(this.uuid.toString()+".yml", "data/spellbooks");
		this.config = ConfigManager.get(this.uuid.toString()+".yml");
		a();
	}
	
	public SpellBook(ItemStack item){
		this.uuid = UUID.fromString(new NBTHandler(item).getStringTag("uuid"));
		this.item = item;
		ConfigManager.load(this.uuid.toString()+".yml", "data/spellbooks");
		this.config = ConfigManager.get(this.uuid.toString()+".yml");
		a();
	}
	
	public void addSpell(SpellTome tome){
		if(this.spells.size() <= this.slots){
			if(!(hasSpell(tome))){
				this.spells.add(tome);
				b();
			}
			return;
		}
		return;
	}
	
	public void removeSpell(SpellTome tome){
		if(hasSpell(tome)){
			this.spells.remove(tome);
			b();
		}
		return;
	}
	
	public boolean hasSpell(SpellTome tome){
		for(SpellTome spell : this.spells){
			if(spell.equals(tome)){
				return true;
			}
		}
		return false;
	}
	
	public void setSpells(List<SpellTome> spells){
		this.spells = spells;
		b();
	}

	public List<SpellTome> getSpells(){
		return this.spells;
	}
	
	public void a(){
		List<SpellTome> spells = new ArrayList<SpellTome>();
		for(int i = 0; i < this.slots; i++){
			ItemStack item = config.getItemStack("spells."+i);
			if(item != null){
				if(new ItemType(item).getType().equals(ItemTypes.SPELL)){
					SpellTome tome = new SpellTome(item);
					spells.add(tome);
				}
			}
		}
		this.spells = spells;
	}
	
	public void b(){
		List<SpellTome> spells = this.spells;
		if(spells.size() == 0){
			for(int i = 0; i<this.slots; i++){
				config.set("spells."+i, null);
			}
		}
		int i = 0;
		for(SpellTome tome : spells){
			if(i < this.slots){
				ItemStack item = tome.getItemStack();
				config.set("spells."+i, item);
				i++;
			}
		}
		c();
	}
	
	public void c(){
		ConfigManager.save(this.uuid.toString()+".yml", "data/spellbooks");
	}
	
	public void d(){
		this.spells = new ArrayList<SpellTome>();
		b();
	}
	
	public Inventory getSpellventory(){
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY+"SpellBook " + Data.encodeItemData(this.uuid.toString()));
		ItemStack empty = MenuItem.getEmpty();
		inv.setItem(0, empty);
		inv.setItem(1, empty);
		for(SpellTome tome : this.spells){
			inv.addItem(tome.getItemStack());
		}
		inv.setItem(7, empty);
		inv.setItem(8, empty);
		return inv;
	}
	
	public ItemStack getItemStack(){
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(ChatColor.UNDERLINE + "" + ChatColor.DARK_GRAY + "Spellbook");
		List<String> lore = new ArrayList<String>();
		int i = 0;
		for(SpellTome tome : spells){
			lore.add(
					ChatColor.GRAY+""+ChatColor.UNDERLINE+ 
					StringUtils.capitalize(tome.getSpell().toString().toLowerCase())
					+" "+RomanNumerals.intToRoman(tome.getLevel())+
					" :" +
					ChatColor.RESET + " " + ChatColor.WHITE+ "("+new CastingManager().ClickCombFromNumb(i)+")"
					);
			i++;
		}
		lore.add(ChatColor.GRAY + "Place in spellbook slot to equip.");
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(im);
		
		ItemStack nbt = this.item;
		nbt = new NBTHandler(nbt).setType(ItemTypes.SPELLBOOK.toString());
		nbt = new NBTHandler(nbt).setStringTag("uuid", uuid.toString());
		this.item = nbt;
		
		b();
		
		return this.item;
	}
	
}
