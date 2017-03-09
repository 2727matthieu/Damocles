package xyz.almia.itemblueprints;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.ChatColor;
import xyz.almia.itemsystem.ItemType.ItemTypes;
import xyz.almia.itemsystem.ItemType.ScrollType;
import xyz.almia.itemsystem.Settable;
import xyz.almia.utils.NBTHandler;

public class Scroll implements Settable{
	
	private ItemStack item;
	private ScrollType type;
	private int amount;
	private int success;
	private int destroy;
	
	public Scroll(ItemStack item){
		this.item = item;
		this.type = ScrollType.valueOf(new NBTHandler(item).getStringTag("scrolltype"));
		this.amount = new NBTHandler(item).getIntTag("amount");
		this.success = new NBTHandler(item).getIntTag("success");
		this.destroy = new NBTHandler(item).getIntTag("destroy");
	}
	
	public Scroll(ScrollType type, int amount, int success, int destroy){
		this.item = new ItemStack(Material.SHEARS);
		this.type = type;
		this.amount = amount;
		this.success = success;
		this.destroy = destroy;
		setup(type, amount, success, destroy);
	}
	
	public ScrollType getType(){
		return this.type;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	public int getSuccess(){
		return this.success;
	}
	
	public int getDestroy(){
		return this.destroy;
	}
	
	public void setType(ScrollType type){
		this.type = type;
		setup(type, getAmount(), getSuccess(), getDestroy());
	}
	
	public void setAmount(int value){
		this.amount = value;
		setup(getType(), value, getSuccess(), getDestroy());
	}
	
	public void setSuccess(int value){
		this.success = value;
		setup(getType(), getAmount(), value, getDestroy());
	}
	
	public void setDestroy(int value){
		this.destroy = value;
		setup(getType(), getAmount(), getSuccess(), value);
	}
	
	public void setup(ScrollType type, int amount, int success, int destroy){
		ItemMeta im = this.item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.UNDERLINE+"Enhancement Scroll");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.BLUE+"Applies +"+amount+" "+StringUtils.capitalize(type.toString().toLowerCase()));
		lore.add(ChatColor.GREEN+""+success+"% success rate.");
		lore.add(ChatColor.RED+""+destroy+"% destruction rate on fail.");
		lore.add(ChatColor.YELLOW+"Place on item to apply.");
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		item.setItemMeta(im);
		
		ItemStack nbt = this.item;
		nbt = new NBTHandler(nbt).setType(ItemTypes.SCROLL.toString());
		nbt = new NBTHandler(nbt).setStringTag("scrolltype", type.toString());
		nbt = new NBTHandler(nbt).setIntTag("amount", amount);
		nbt = new NBTHandler(nbt).setIntTag("success", success);
		nbt = new NBTHandler(nbt).setIntTag("destroy", destroy);
		this.item = nbt;
		
		setID(3);
		
	}
	
	public void setID(int id){
		ItemMeta im = this.item.getItemMeta();
		item.setDurability((short)id);
		im.setUnbreakable(true);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(im);
	}
	
	public ItemStack getItemStack(){
		setup(getType(), getAmount(), getSuccess(), getDestroy());
		return this.item;
	}
	
}
