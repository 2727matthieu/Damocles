package ca.damocles.utils;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import ca.damocles.enchantsystem.Enchantments;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_11_R1.NBTBase;
import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagString;

public class NBTHandler {
	
	ItemStack item;
	net.minecraft.server.v1_11_R1.ItemStack nmsStack;
	
	public NBTHandler(ItemStack item){
		this.item = item;
		nmsStack = CraftItemStack.asNMSCopy(item);
	}
	
	public String getType(){
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		String s = compound.getString("itemtype");
		if(s.equals("")){
			return "NONE";
		}
		return compound.getString("itemtype");
	}
	
	public ItemStack setType(String string){
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		compound.set("itemtype", new NBTTagString(string));
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public double getDamage(){
		
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();

		NBTTagList am = (NBTTagList) compound.get("AttributeModifiers");
		for(int i = 0; i < am.size(); i++){
			NBTTagCompound damage = am.get(i);
			if(damage.getString("AttributeName").equals("generic.attackDamage")){
				return damage.getDouble("Amount");
			}
		}
		return 0.0;
	}
	
	public ItemStack resetArmor(){
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		NBTTagCompound armor = new NBTTagCompound();
		armor.set("AttributeName", new NBTTagString("generic.armor"));
		armor.set("Name", new NBTTagString("generic.armor"));
		armor.set("Amount", new NBTTagDouble(0.0));
		armor.set("Operation", new NBTTagInt(0));
		armor.set("UUIDLeast", new NBTTagInt(894654));
		armor.set("UUIDMost", new NBTTagInt(2872));
		modifiers.add(armor);
		compound.set("AttributeModifiers", modifiers);
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}

	
    /*public String getItemInventory(){
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = nmsStack.getTag();
        return compound.getString("id");
    }
	
	public ItemStack setItemInventory(List<ItemStack> items){
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList Items = new NBTTagList();
        for(ItemStack newItem : items){
        	NBTTagCompound newItemCompound = new NBTTagCompound();
        	newItemCompound.set("id", new NBTTagString(newItem.getType().toString()));
        	newItemCompound.set("Count", new NBTTagByte((byte)newItem.getAmount()));
        	newItemCompound.set("tag", CraftItemStack.asNMSCopy(newItem).getTag());
        	Items.add(newItemCompound);
        }
        compound.set("id", new NBTTagString("BOOK"));
        compound.set("Count", new NBTTagByte((byte)1));
        nmsStack = new net.minecraft.server.v1_11_R1.ItemStack(compound);
        NBTTagCompound edited = new NBTTagCompound();
        edited.set("Items", Items);
        nmsStack.setTag(edited);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public ItemStack setItemInventory(ItemStack[] items){
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("test");
		item.setItemMeta(meta);
		nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.set("Count", new NBTTagByte((byte)item.getAmount()));
		compound.set("id", new NBTTagString(item.getType().toString()));
		
		NBTTagList inventory = new NBTTagList();
		
		for(ItemStack item : items){
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("hello");
			item.setItemMeta(itemmeta);
			NBTTagCompound itemnbt = new NBTTagCompound();
			itemnbt.set("Count", new NBTTagByte((byte)item.getAmount()));
			itemnbt.set("id", new NBTTagString(item.getType().toString()));
			itemnbt.set("tag", CraftItemStack.asNMSCopy(item).getTag());
			inventory.add(itemnbt);
		}
		
		compound.set("Items", inventory);
		nmsStack.load(compound);
		
		return CraftItemStack.asBukkitCopy(nmsStack);
	}*/
	
	public void setBoxInventory(Inventory inventory){
		if(item.getItemMeta() instanceof BlockStateMeta){
			BlockStateMeta im = (BlockStateMeta)item.getItemMeta();
			if(im.getBlockState() instanceof ShulkerBox){
				ShulkerBox shulker = (ShulkerBox) im.getBlockState();
				shulker.getInventory().setContents(inventory.getContents());
				im.setBlockState(shulker);
				item.setItemMeta(im);
				return;
			}
		}
		return;
	}
	
	/*
	 *  Id: The ID of the effect.

 Amplifier: The amplifier of the effect, with 0 being level 1.

 Duration: The duration of the effect in ticks.

 Ambient: 1 or 0 (true/false) - whether or not this is an effect provided by a beacon and therefore should be less intrusive on the screen. This tag is optional and defaults to 0. Due to a bug, it has no effect on splash potions.

 ShowParticles: 1 or 0 (true/false) - whether or not this effect produces particles. This tag is optional and defaults to 1. Due to a bug, it has no effect on splash potions.
	 */
	
	public ItemStack createPotion(){
		ItemStack splashpotion = new ItemStack(Material.SPLASH_POTION);
		net.minecraft.server.v1_11_R1.ItemStack nmspotion = CraftItemStack.asNMSCopy(splashpotion);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        
        NBTTagList potioneffect = new NBTTagList();
        
        NBTTagCompound damage = new NBTTagCompound();
        damage.set("Id", new NBTTagByte((byte)7));
        damage.set("Amplifier", new NBTTagByte((byte)1));
        damage.set("Duration", new NBTTagInt(20));
        damage.set("ShowParticles", new NBTTagByte((byte)1));
        
        NBTTagCompound slow = new NBTTagCompound();
        slow.set("Id", new NBTTagByte((byte)2));
        slow.set("Amplifier", new NBTTagByte((byte)2));
        slow.set("Duration", new NBTTagInt(100));
        slow.set("ShowParticles", new NBTTagByte((byte)1));
        
        potioneffect.add(slow);
        potioneffect.add(damage);
        
        compound.set("CustomPotionEffects", potioneffect);
        compound.set("CustomPotionColor", new NBTTagInt(181<<16 + 65<<8 + 71));
        
        
        nmspotion.setTag(compound);
        
        splashpotion = CraftItemStack.asBukkitCopy(nmspotion);
        ItemMeta meta = splashpotion.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Witch pot");
        splashpotion.setItemMeta(meta);
        
        return splashpotion;
	}
	
	public Inventory getBoxInventory(){
		Inventory inv = Bukkit.createInventory(null, 27, "Bank");
		if(item.getItemMeta() instanceof BlockStateMeta){
			BlockStateMeta im = (BlockStateMeta)item.getItemMeta();
			if(im.getBlockState() instanceof ShulkerBox){
				ShulkerBox shulker = (ShulkerBox) im.getBlockState();
				inv.setContents(shulker.getInventory().getContents());
				return inv;
			}
		}
		return inv;
	}
	
	public ItemStack setColor(Color color){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        int RGB = color.Red<<16 + color.Green<<8 + color.Blue;
        compound.set("CustomPotionColor", new NBTTagInt(RGB));
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}

	@SuppressWarnings({ "deprecation" })
	public ItemStack setPotionEffect(PotionEffect effect){
		if(effect != null){
			NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
			NBTTagList list = new NBTTagList();
			NBTTagCompound potionEffect = new NBTTagCompound();
			potionEffect.set("Id", new NBTTagByte((byte)effect.getType().getId()));
			potionEffect.set("Amplifier", new NBTTagByte((byte)effect.getAmplifier()));
			potionEffect.set("Duration", new NBTTagInt(effect.getDuration()));
			potionEffect.set("Ambient", new NBTTagByte((byte)0));
			potionEffect.set("ShowParticles", new NBTTagByte((byte)1));
			list.add(potionEffect);
			compound.set("CustomPotionEffects", list);
			nmsStack.setTag(compound);
			return CraftItemStack.asBukkitCopy(nmsStack);
		}else{
			return item;
		}
	}
	
	public ItemStack setBoolean(String tag, boolean value){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		if(value){
			compound.set(tag, new NBTTagByte((byte) 1));
		}else{
			compound.set(tag, new NBTTagByte((byte) 0));
		}
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public boolean isProtected(){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		if(compound.getShort("protected") == (short)1){
			return true;
		}else{
			return false;
		}
	}
	
	public ItemStack setUnbreakable(boolean value){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(value){
        	compound.set("Unbreakable", new NBTTagByte((byte) 1));
        }else{
        	compound.set("Unbreakable", new NBTTagByte((byte) 0));
        }
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public ItemStack setListEmpty(String tag){
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		compound.set(tag, list);
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public NBTTagList getTagList(String tag){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return (NBTTagList)compound.get(tag);
	}
	
	public HashMap<Enchantments, Integer> getEnchants(){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList list = (compound.hasKey("enchants")) ? getTagList("enchants") : new NBTTagList();
        HashMap<Enchantments, Integer> enchantments = new HashMap<Enchantments, Integer>();
    	NBTTagCompound enchant;
        for(int i = 0; i < list.size(); i++){
        	enchant = list.get(i);
        	enchantments.put(Enchantments.valueOf(enchant.getString("name")), enchant.getInt("lvl"));
        }
        return enchantments;
	}
	
	public ItemStack removeEnchant(Enchantments enchant){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList list = (compound.hasKey("enchants")) ? getTagList("enchants") : new NBTTagList();
        HashMap<Enchantments, Integer> enchantments = getEnchants();
        if(enchantments.containsKey(enchant)){
        	NBTTagCompound enchantment;
            for(int i = 0; i < list.size(); i++){
            	enchantment = list.get(i);
            	if(Enchantments.valueOf(enchantment.getString("name")).equals(enchant)){
            		list.remove(i);
            	}
            }
        }
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public ItemStack addEnchant(Enchantments enchant, int lvl){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList list = (compound.hasKey("enchants")) ? getTagList("enchants") : new NBTTagList();
        NBTTagCompound enchantment = new NBTTagCompound();
        enchantment.set("name", new NBTTagString(enchant.toString()));
        enchantment.set("lvl", new NBTTagInt(lvl));
        list.add(enchantment);
        nmsStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public NBTBase getTag(String tag){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.get(tag);
	}
	
	public int getIntTag(String tag){
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		return compound.getInt(tag);
	}
	
	public String getStringTag(String tag){
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		return compound.getString(tag);
	}
	
	public ItemStack setTag(String tag, NBTBase value){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.set(tag, value);
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public ItemStack setIntTag(String tag, int value){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.set(tag, new NBTTagInt(value));
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public ItemStack setStringTag(String tag, String value){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.set(tag, new NBTTagString(value));
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public ItemStack setCompound(NBTTagCompound compound){
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
}
