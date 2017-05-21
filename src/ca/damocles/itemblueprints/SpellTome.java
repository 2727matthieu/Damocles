package ca.damocles.itemblueprints;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.spellsystem.SpellName;
import ca.damocles.spellsystem.Spell.Spells;
import ca.damocles.utils.NBTHandler;
import ca.damocles.utils.RomanNumerals;

public class SpellTome {
	
	int level;
	int exp;
	Spells spell;
	ItemStack item;
	
	public SpellTome(ItemStack item){
		this.item = item;
		this.spell = Spells.valueOf(new NBTHandler(item).getStringTag("spell"));
		this.level = new NBTHandler(item).getIntTag("level");
		this.exp = new NBTHandler(item).getIntTag("exp");
	}
	
	public SpellTome(Spells spell, int level, int exp){
		this.item = new ItemStack(Material.PAPER);
		this.spell = spell;
		this.level = level;
		this.exp = exp;
		setup(spell, level, exp);
	}
	
	public void setup(Spells spell, int level, int exp){
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(ChatColor.GRAY + "Spell of " + ChatColor.UNDERLINE + StringUtils.capitalize(spell.toString().toLowerCase()) + ChatColor.RESET + " " + ChatColor.YELLOW + RomanNumerals.intToRoman(level));
		List<String> lore = new ArrayList<String>();
		
		lore.add(getExpBar(exp, level));
		lore.add(ChatColor.GOLD + "" + new SpellName().getSpellType(spell) + " Spell");
		lore.add(ChatColor.GRAY + "Place in spellbook to utilize.");
		
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(im);
		
		ItemStack nbt = this.item;
		nbt = new NBTHandler(nbt).setType(ItemTypes.SPELL.toString());
		nbt = new NBTHandler(nbt).setStringTag("spell", spell.toString());
		nbt = new NBTHandler(nbt).setIntTag("level", level);
		nbt = new NBTHandler(nbt).setIntTag("exp", exp);
		this.item = nbt;
	}
	
	public Spells getSpell(){
		return Spells.valueOf(new NBTHandler(item).getStringTag("spell"));
	}
	
	public int getExp(){
		return new NBTHandler(item).getIntTag("exp");
	}
	
	public int getLevel(){
		return new NBTHandler(item).getIntTag("level");
	}
	
	public int expToNextLevel(){
		return this.level*150;
	}
	
	public void setLevel(int value){
		setup(getSpell(), value, getExp());
	}
	
	public void setExp(int value){
		setup(getSpell(), getLevel(), value);
	}
	
	public void levelUp(){
		int difference = getExp()-expToNextLevel();
		if(difference >= 0){
			setup(getSpell(), getLevel()+1, difference);
			return;
		}else{
			setup(getSpell(), getLevel()+1, 0);
			return;
		}
	}
	
	public void addExp(int value){
		int total = value+getExp();
		if(total >= expToNextLevel()){
			setExp(total);
			levelUp();
			return;
		}else{
			setup(getSpell(), getLevel(), total);
			return;
		}
	}
	
	public String getExpBar(int exp, int level){
		double rate = ((double)exp) / ((double)level*150);
		if(rate > 0.0 && rate < 0.1)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+""+ChatColor.RED+" :  :  :  :  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.1 && rate < 0.2)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  "+ChatColor.RED+":  :  :  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.2 && rate < 0.3)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  "+ChatColor.RED+":  :  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.3 && rate < 0.4)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  "+ChatColor.RED+":  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.4 && rate < 0.5)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  "+ChatColor.RED+":  :  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.5 && rate < 0.6)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  "+ChatColor.RED+":  :  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.6 && rate < 0.7)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  "+ChatColor.RED+":  :  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.7 && rate < 0.8)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  :  "+ChatColor.RED+":  :  : "+ChatColor.GRAY+" ]";
		if(rate > 0.8 && rate < 0.9)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  :  :  "+ChatColor.RED+":  : "+ChatColor.GRAY+" ]";
		if(rate > 0.9 && rate < 1.0)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  :  :  :  "+ChatColor.RED+": "+ChatColor.GRAY+" ]";
		if(rate == 1.0)
			return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+" :  :  :  :  :  :  :  :  :  : "+ChatColor.RED+""+ChatColor.GRAY+" ]";
		return ChatColor.GRAY+"[ "+ChatColor.DARK_GREEN+""+ChatColor.RED+" :  :  :  :  :  :  :  :  :  : "+ChatColor.GRAY+" ]";
	}
	
	public ItemStack getItemStack(){
		return this.item;
	}
	
}
