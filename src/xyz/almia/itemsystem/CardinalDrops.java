package xyz.almia.itemsystem;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.ChatColor;
import xyz.almia.enchantsystem.Enchantments;

public class CardinalDrops {
	
	public CardinalDrops(){}
	
	public ItemStack getItem(Items drop){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta itemmeta = item.getItemMeta();
		HashMap<Enchantments, Integer> enchants = new HashMap<Enchantments, Integer>();
		switch(drop){
		case BAT:
			itemmeta.setDisplayName(ChatColor.DARK_GRAY+"Baseball Bat");
			item.setItemMeta(itemmeta);
			Weapon bat = new Weapon(item);
			bat.setup(enchants, 0, 0, 0, 0, 0, 3, 0, 8, 0, false, 32, 32);
			bat.setID(3);
			item = bat.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case BRONZE_SWORD:
			itemmeta.setDisplayName(ChatColor.DARK_GRAY+"Bronze Sword");
			item.setItemMeta(itemmeta);
			Weapon bsword = new Weapon(item);
			bsword.setup(enchants, 0, 0, 0, 0, 0, 5, 0, 20, 0, false, 37, 39);
			bsword.setID(7);
			item = bsword.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case EXCALIBUR:
			itemmeta.setDisplayName(ChatColor.GOLD+"Excalibur");
			item.setItemMeta(itemmeta);
			Weapon excalibur = new Weapon(item);
			excalibur.setup(enchants, 3, 0, 0, 0, 0, 12, 0, 7, 3, false, 300, 300);
			excalibur.setID(1);
			item = excalibur.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case KENDO_SWORD:
			itemmeta.setDisplayName(ChatColor.DARK_GRAY+"Kendo Sword");
			item.setItemMeta(itemmeta);
			Weapon kendo = new Weapon(item);
			kendo.setup(enchants, 0, 0, 0, 0, 0, 3, 0, 8, 0, false, 32, 32);
			kendo.setID(4);
			item = kendo.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case MURAMASA:
			itemmeta.setDisplayName(ChatColor.RED+"Muramasa");
			item.setItemMeta(itemmeta);
			Weapon muramasa = new Weapon(item);
			muramasa.setup(enchants, 3, 0, 0, 0, 0, 9, 0, 12, 3, false, 256, 256);
			muramasa.setID(5);
			item = muramasa.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case PLATINUM_SCIMITAR:
			itemmeta.setDisplayName(ChatColor.AQUA+"Platinum Scimitar");
			item.setItemMeta(itemmeta);
			Weapon pscimitar = new Weapon(item);
			pscimitar.setup(enchants, 2, 0, 0, 0, 0, 8, 0, 16, 0, false, 128, 128);
			pscimitar.setID(6);
			item = pscimitar.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case RORAN:
			itemmeta.setDisplayName(ChatColor.GRAY+"Roran");
			item.setItemMeta(itemmeta);
			Weapon roran = new Weapon(item);
			roran.setup(enchants, 1, 0, 0, 0, 0, 10, 0, 13, 0, false, 143, 143);
			roran.setID(2);
			item = roran.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case STEEL_LONG_SWORD:
			itemmeta.setDisplayName(ChatColor.GRAY+"Steel Long Sword");
			item.setItemMeta(itemmeta);
			Weapon slsword = new Weapon(item);
			slsword.setup(enchants, 4, 0, 0, 0, 0, 12, 0, 20, 0, false, 86, 89);
			slsword.setID(9);
			item = slsword.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case STEEL_RAPIER:
			itemmeta.setDisplayName(ChatColor.GRAY+"Steel Rapier");
			item.setItemMeta(itemmeta);
			Weapon srapier = new Weapon(item);
			srapier.setup(enchants, 4, 0, 0, 0, 0, 11, 0, 10, 0, false, 37, 39);
			srapier.setID(10);
			item = srapier.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		case STEEL_SHORT_SWORD:
			itemmeta.setDisplayName(ChatColor.GRAY+"Steel Short Sword");
			item.setItemMeta(itemmeta);
			Weapon shsword = new Weapon(item);
			shsword.setup(enchants, 3, 0, 0, 0, 0, 10, 0, 17, 0, false, 86, 89);
			shsword.setID(8);
			item = shsword.getItemStack();
			item = new NBTHandler(item).setUnbreakable(true);
			return item;
		}
		return null;
	}
	
}
