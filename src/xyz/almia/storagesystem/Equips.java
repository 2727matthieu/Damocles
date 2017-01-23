package xyz.almia.storagesystem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.ChatColor;
import xyz.almia.menu.MenuItem;

public class Equips{
	
	xyz.almia.accountsystem.Character character;
	
	public enum Slot { 
		HELMET, CHESTPLATE, LEGGINGS, BOOTS,
		BELT, GLOVES, RING1, RING2, RING3, RING4,
		QUIVER, SPELLBOOK, BANK
	}
	
	public Equips(xyz.almia.accountsystem.Character character) {
		this.character = character;
	}
	
	public ItemStack getEquip(Slot slot){
		return character.getEquip(slot);
	}
	
	public void setEquip(Slot slot, ItemStack value){
		character.setEquip(slot, value);
	}
	
	@SuppressWarnings("deprecation")
	public Inventory getMenu(){
		Inventory inv = Bukkit.createInventory(null, 45, "Equip Inventory");
		ItemStack empty = MenuItem.createItem(ChatColor.GRAY+"Empty", "", Material.STAINED_GLASS_PANE);
		
		ItemStack helmet = MenuItem.createItem(ChatColor.GRAY+"Helmet slot", ChatColor.GRAY+ "Helmet goes here -->", Material.DIAMOND_HELMET);
		ItemMeta helmetmeta = helmet.getItemMeta();
		helmetmeta.addEnchant(Enchantment.getById(69), 1, true);
		helmetmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		helmet.setItemMeta(helmetmeta);
		
		ItemStack chestplate = MenuItem.createItem(ChatColor.GRAY+"Chestplate slot", ChatColor.GRAY+"<-- Chestplate goes here", Material.DIAMOND_CHESTPLATE);
		ItemMeta chestplatemeta = chestplate.getItemMeta();
		chestplatemeta.addEnchant(Enchantment.getById(69), 1, true);
		chestplatemeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		chestplate.setItemMeta(chestplatemeta);
		
		ItemStack leggings = MenuItem.createItem(ChatColor.GRAY+"Leggings slot",ChatColor.GRAY+ "Leggings go here -->", Material.DIAMOND_LEGGINGS);
		ItemMeta leggingmeta = leggings.getItemMeta();
		leggingmeta.addEnchant(Enchantment.getById(69), 1, true);
		leggingmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		leggings.setItemMeta(leggingmeta);
		
		ItemStack boots = MenuItem.createItem(ChatColor.GRAY+"Boots slot", ChatColor.GRAY+"<-- Boots go here", Material.DIAMOND_BOOTS);
		ItemMeta bootmeta = boots.getItemMeta();
		bootmeta.addEnchant(Enchantment.getById(69), 1, true);
		bootmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		boots.setItemMeta(bootmeta);
		
		ItemStack leftring = MenuItem.createItem(ChatColor.GRAY+"Ring slot",ChatColor.GRAY+ "<-- Ring go here", Material.SHEARS);
		ItemMeta leftringmeta = leftring.getItemMeta();
		leftring.setDurability((short)2);
		leftringmeta.setUnbreakable(true);
		leftringmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		leftring.setItemMeta(leftringmeta);
		
		ItemStack rightring = MenuItem.createItem(ChatColor.GRAY+"Ring slot",ChatColor.GRAY+ "Ring go here -->", Material.SHEARS);
		ItemMeta rightringmeta = rightring.getItemMeta();
		rightring.setDurability((short)2);
		rightringmeta.setUnbreakable(true);
		rightringmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		rightring.setItemMeta(rightringmeta);
		
		
		inv.setItem(0, helmet);
		inv.setItem(1, getEquip(Slot.HELMET));
		inv.setItem(2, getEquip(Slot.CHESTPLATE));
		inv.setItem(3, chestplate);
		inv.setItem(4, empty);
		inv.setItem(5, leggings);
		inv.setItem(6, getEquip(Slot.LEGGINGS));
		inv.setItem(7, getEquip(Slot.BOOTS));
		inv.setItem(8, boots);
		inv.setItem(9, empty);
		inv.setItem(10, empty);
		inv.setItem(11, empty);
		inv.setItem(12, empty);
		inv.setItem(13, empty);
		inv.setItem(14, empty);
		inv.setItem(15, empty);
		inv.setItem(16, empty);
		inv.setItem(17, empty);
		inv.setItem(18, MenuItem.createItem(ChatColor.GRAY+"Belt slot",ChatColor.GRAY+ "Belt goes here -->", Material.ARMOR_STAND));
		inv.setItem(19, getEquip(Slot.BELT));
		inv.setItem(20, getEquip(Slot.GLOVES));
		inv.setItem(21, MenuItem.createItem(ChatColor.GRAY+"Glove slot",ChatColor.GRAY+ "<-- Gloves go here", Material.ARMOR_STAND));
		inv.setItem(22, empty);
		inv.setItem(23, rightring);
		inv.setItem(24, getEquip(Slot.RING1));
		inv.setItem(25, getEquip(Slot.RING2));
		inv.setItem(26, leftring);
		inv.setItem(27, empty);
		inv.setItem(28, empty);
		inv.setItem(29, empty);
		inv.setItem(30, empty);
		inv.setItem(31, empty);
		inv.setItem(32, empty);
		inv.setItem(33, empty);
		inv.setItem(34, empty);
		inv.setItem(35, empty);
		inv.setItem(36, MenuItem.createItem(ChatColor.GRAY+"SpellBook slot",ChatColor.GRAY+ "SpellBook goes here -->", Material.ENCHANTED_BOOK));
		inv.setItem(37, getEquip(Slot.SPELLBOOK));
		inv.setItem(38, getEquip(Slot.BANK));
		inv.setItem(39, MenuItem.createItem(ChatColor.GRAY+"Bank slot",ChatColor.GRAY+ "<-- Bank chest goes here", Material.CYAN_SHULKER_BOX));
		inv.setItem(40, empty);
		inv.setItem(41, rightring);
		inv.setItem(42, getEquip(Slot.RING3));
		inv.setItem(43, getEquip(Slot.RING4));
		inv.setItem(44, leftring);
		return inv;
	}
	
}