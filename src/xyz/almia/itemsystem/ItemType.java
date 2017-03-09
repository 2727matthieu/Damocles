package xyz.almia.itemsystem;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import xyz.almia.utils.NBTHandler;

public class ItemType {
	
	ItemStack item;
	
	public ItemType(ItemStack item) {
		this.item = item;
	}
	
	public ItemTypes getRawType(){
		switch(item.getType()){
		case DIAMOND_SPADE: case GOLD_SPADE: case IRON_SPADE: case WOOD_SPADE: case STONE_SPADE: case DIAMOND_PICKAXE: case IRON_PICKAXE: case WOOD_PICKAXE: case STONE_PICKAXE: case GOLD_PICKAXE: case DIAMOND_AXE: case IRON_AXE: case WOOD_AXE: case GOLD_AXE: case STONE_AXE:
			return ItemTypes.TOOL;
		case DIAMOND_HELMET: case IRON_HELMET: case CHAINMAIL_HELMET: case GOLD_HELMET: case LEATHER_HELMET: case DIAMOND_CHESTPLATE: case IRON_CHESTPLATE: case CHAINMAIL_CHESTPLATE: case GOLD_CHESTPLATE: case LEATHER_CHESTPLATE: case DIAMOND_LEGGINGS: case IRON_LEGGINGS: case CHAINMAIL_LEGGINGS: case GOLD_LEGGINGS: case LEATHER_LEGGINGS: case DIAMOND_BOOTS: case IRON_BOOTS: case CHAINMAIL_BOOTS: case GOLD_BOOTS: case LEATHER_BOOTS:
			return ItemTypes.ARMOR;
		case DIAMOND_SWORD: case IRON_SWORD: case WOOD_SWORD: case GOLD_SWORD: case STONE_SWORD: case BOW:
			return ItemTypes.WEAPON;
		default:
			break;
		}
		return ItemTypes.NONE;
	}
	
	public RuneType getRuneType(){
		if(getType().equals(ItemTypes.RUNE)){
			if(item.getType().equals(Material.NETHER_STAR)){
				return RuneType.RUNE;
			}
			if(item.getType().equals(Material.EYE_OF_ENDER)){
				return RuneType.SLOT;
			}
			if(item.getType().equals(Material.EMPTY_MAP)){
				return RuneType.PROTECTION;
			}
			return RuneType.NONE;
		}
		return RuneType.NONE;
	}
	
	public ArmorTypes getArmorType(){
		if(getType().equals(ItemTypes.ARMOR)){
			switch(item.getType()){
			case DIAMOND_HELMET: case IRON_HELMET: case CHAINMAIL_HELMET: case GOLD_HELMET: case LEATHER_HELMET:
				return ArmorTypes.HEAD;
			case DIAMOND_CHESTPLATE: case IRON_CHESTPLATE: case CHAINMAIL_CHESTPLATE: case GOLD_CHESTPLATE: case LEATHER_CHESTPLATE:
				return ArmorTypes.CHEST;
			case DIAMOND_LEGGINGS: case IRON_LEGGINGS: case CHAINMAIL_LEGGINGS: case GOLD_LEGGINGS: case LEATHER_LEGGINGS:
				return ArmorTypes.LEGS;
			case DIAMOND_BOOTS: case IRON_BOOTS: case CHAINMAIL_BOOTS: case GOLD_BOOTS: case LEATHER_BOOTS:
				return ArmorTypes.FEET;
			default:
				return ArmorTypes.NONE;
			}
		}
		return ArmorTypes.NONE;
	}
	
	public ItemTypes getType(){
		return ItemTypes.valueOf(new NBTHandler(item).getType());
	}
	
	public MaterialTypes getMaterialType(){
		ArrayList<Material> diamond = new ArrayList<Material>();
		diamond.add(Material.DIAMOND_AXE);
		diamond.add(Material.DIAMOND_HOE);
		diamond.add(Material.DIAMOND_SWORD);
		diamond.add(Material.DIAMOND_SPADE);
		diamond.add(Material.DIAMOND_PICKAXE);
		diamond.add(Material.DIAMOND_BOOTS);
		diamond.add(Material.DIAMOND_CHESTPLATE);
		diamond.add(Material.DIAMOND_LEGGINGS);
		diamond.add(Material.DIAMOND_HELMET);
		ArrayList<Material> iron = new ArrayList<Material>();
		iron.add(Material.IRON_AXE);
		iron.add(Material.IRON_HOE);
		iron.add(Material.IRON_SWORD);
		iron.add(Material.IRON_SPADE);
		iron.add(Material.IRON_PICKAXE);
		iron.add(Material.IRON_BOOTS);
		iron.add(Material.IRON_CHESTPLATE);
		iron.add(Material.IRON_LEGGINGS);
		iron.add(Material.IRON_HELMET);
		ArrayList<Material> gold = new ArrayList<Material>();
		gold.add(Material.GOLD_AXE);
		gold.add(Material.GOLD_HOE);
		gold.add(Material.GOLD_SWORD);
		gold.add(Material.GOLD_SPADE);
		gold.add(Material.GOLD_PICKAXE);
		gold.add(Material.GOLD_BOOTS);
		gold.add(Material.GOLD_CHESTPLATE);
		gold.add(Material.GOLD_LEGGINGS);
		gold.add(Material.GOLD_HELMET);
		ArrayList<Material> leather = new ArrayList<Material>();
		leather.add(Material.WOOD_AXE);
		leather.add(Material.WOOD_HOE);
		leather.add(Material.WOOD_SWORD);
		leather.add(Material.WOOD_SPADE);
		leather.add(Material.WOOD_PICKAXE);
		leather.add(Material.LEATHER_BOOTS);
		leather.add(Material.LEATHER_CHESTPLATE);
		leather.add(Material.LEATHER_LEGGINGS);
		leather.add(Material.LEATHER_HELMET);
		ArrayList<Material> chainmail = new ArrayList<Material>();
		chainmail.add(Material.STONE_AXE);
		chainmail.add(Material.STONE_HOE);
		chainmail.add(Material.STONE_SWORD);
		chainmail.add(Material.STONE_SPADE);
		chainmail.add(Material.STONE_PICKAXE);
		chainmail.add(Material.CHAINMAIL_BOOTS);
		chainmail.add(Material.CHAINMAIL_CHESTPLATE);
		chainmail.add(Material.CHAINMAIL_LEGGINGS);
		chainmail.add(Material.CHAINMAIL_HELMET);
		
		if(diamond.contains(item.getType())){
			return MaterialTypes.DIAMOND;
		}else if(iron.contains(item.getType())){
			return MaterialTypes.IRON;
		}else if(gold.contains(item.getType())){
			return MaterialTypes.GOLD;
		}else if(leather.contains(item.getType())){
			return  MaterialTypes.LEATHER;
		}else if(chainmail.contains(item.getType())){
			return MaterialTypes.CHAINMAIL;
		}else{
			return MaterialTypes.NONE;
		}
	}
	
	public enum ScrollType{
		INTELLIGENCE, AGILITY, HITPOINTS,
		STRENGTH, DAMAGE, ARMOR;
	}
	
	public enum RuneType{
		RUNE, PROTECTION, SLOT, NONE;
	}
	
	public enum MaterialTypes{
		DIAMOND, CHAINMAIL, IRON, GOLD, LEATHER, NONE;
	}
	
	public enum ArmorTypes{
		HEAD, CHEST, LEGS, FEET, NONE;
	}
	
	public enum ItemTypes{
		SHIELD, TOOL, ARMOR,
		WEAPON, GLOVES, BELT,
		RING, BOW, ARROW,
		POTION, RUNE, SPELL,
		SPELLBOOK, SCROLL, NONE;
	}
	
}
