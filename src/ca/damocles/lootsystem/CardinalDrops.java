package ca.damocles.lootsystem;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ca.damocles.enchantsystem.Enchantments;
import ca.damocles.itemblueprints.Ring;
import ca.damocles.itemblueprints.Weapon;
import ca.damocles.utils.NBTHandler;
import net.md_5.bungee.api.ChatColor;

public class CardinalDrops {
	
	public CardinalDrops(){}
	
	public ItemStack getItem(Items drop){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta itemmeta = item.getItemMeta();
		HashMap<Enchantments, Integer> enchants = new HashMap<Enchantments, Integer>();
		switch(drop){
		case BAT:
			Weapon bat = new Weapon(item);
			bat.setup(enchants, ChatColor.DARK_GRAY+"Baseball Bat", 0, 0, 0, 0, 0, 3, 0, 8, 0, false, 32, 32, 0);
			bat.setID(3);
			item = bat.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case BRONZE_SWORD:
			Weapon bsword = new Weapon(item);
			bsword.setup(enchants, ChatColor.DARK_GRAY+"Bronze Sword", 0, 0, 0, 0, 0, 5, 0, 20, 0, false, 37, 39, 0);
			bsword.setID(7);
			item = bsword.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case EXCALIBUR:
			Weapon excalibur = new Weapon(item);
			excalibur.setup(enchants, ChatColor.GOLD+"Excalibur", 3, 0, 0, 0, 0, 12, 0, 7, 3, false, 300, 300, 0);
			excalibur.setID(1);
			item = excalibur.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case KENDO_SWORD:
			Weapon kendo = new Weapon(item);
			kendo.setup(enchants, ChatColor.DARK_GRAY+"Kendo Sword", 0, 0, 0, 0, 0, 3, 0, 8, 0, false, 32, 32, 0);
			kendo.setID(4);
			item = kendo.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case MURAMASA:
			Weapon muramasa = new Weapon(item);
			muramasa.setup(enchants, ChatColor.RED+"Muramasa", 3, 0, 0, 0, 0, 9, 0, 12, 3, false, 256, 256, 0);
			muramasa.setID(5);
			item = muramasa.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case PLATINUM_SCIMITAR:
			Weapon pscimitar = new Weapon(item);
			pscimitar.setup(enchants, ChatColor.AQUA+"Platinum Scimitar", 2, 0, 0, 0, 0, 8, 0, 16, 0, false, 128, 128, 0);
			pscimitar.setID(6);
			item = pscimitar.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case RORAN:
			Weapon roran = new Weapon(item);
			roran.setup(enchants, ChatColor.GRAY+"Roran", 1, 0, 0, 0, 0, 10, 0, 13, 0, false, 143, 143, 0);
			roran.setID(2);
			item = roran.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case STEEL_LONG_SWORD:
			Weapon slsword = new Weapon(item);
			slsword.setup(enchants, ChatColor.GRAY+"Steel Long Sword", 4, 0, 0, 0, 0, 12, 0, 20, 0, false, 86, 89, 0);
			slsword.setID(9);
			item = slsword.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case STEEL_RAPIER:
			Weapon srapier = new Weapon(item);
			srapier.setup(enchants, ChatColor.GRAY+"Steel Rapier", 4, 0, 0, 0, 0, 11, 0, 10, 0, false, 37, 39, 0);
			srapier.setID(10);
			item = srapier.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case STEEL_SHORT_SWORD:
			Weapon shsword = new Weapon(item);
			shsword.setup(enchants, ChatColor.GRAY+"Steel Short Sword", 3, 0, 0, 0, 0, 10, 0, 17, 0, false, 86, 89, 0);
			shsword.setID(8);
			item = shsword.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case ENDER_RING:
			item = new ItemStack(Material.SHEARS);
			item.setItemMeta(itemmeta);
			Ring enderring = new Ring(item);
			enderring.setup(ChatColor.LIGHT_PURPLE+"Ender Ring", 0, 0, 0, 0, 0, 1, 8, false, 0);
			enderring.setID(1);
			item = enderring.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case SILVER_RING:
			item = new ItemStack(Material.SHEARS);
			item.setItemMeta(itemmeta);
			Ring silverring = new Ring(item);
			silverring.setup(ChatColor.GRAY+"Silver Ring", 0, 0, 0, 0, 0, 1, 8, false, 0);
			silverring.setID(2);
			item = silverring.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		default:
			break;
		}
		return null;
	}
	
}
