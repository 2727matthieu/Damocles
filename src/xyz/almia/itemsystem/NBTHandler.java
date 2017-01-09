package xyz.almia.itemsystem;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_11_R1.NBTBase;
import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagString;
import xyz.almia.enchantsystem.Enchantments;

public class NBTHandler {
	
	ItemStack item;
	net.minecraft.server.v1_11_R1.ItemStack nmsStack;
	
	public NBTHandler(ItemStack item){
		this.item = item;
		nmsStack = CraftItemStack.asNMSCopy(item);
	}
	
	public ItemStack setArmor(int value, String slot){
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound armor = new NBTTagCompound();
        armor.set("AttributeName", new NBTTagString("generic.armor"));
        armor.set("Name", new NBTTagString("generic.armor"));
        armor.set("Amount", new NBTTagInt(value));
        armor.set("Operation", new NBTTagInt(0));
        armor.set("UUIDLeast", new NBTTagInt(894654));
        armor.set("UUIDMost", new NBTTagInt(2872));
        armor.set("Slot", new NBTTagString(slot));
        modifiers.add(armor);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsStack);
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
