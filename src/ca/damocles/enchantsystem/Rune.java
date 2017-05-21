package ca.damocles.enchantsystem;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.utils.NBTHandler;

public class Rune implements Listener{
	
	Plugin plugin = Cardinal.getPlugin();
	ca.damocles.enchantsystem.Enchantment enchantclass = new ca.damocles.enchantsystem.Enchantment();
	
	public Rune(){}
	
	public ItemStack setAmount(ItemStack item, int amount){
		if(amount == 0){
			return new ItemStack(Material.AIR, 1);
		}else{
			item.setAmount(amount);
			return item;
		}
	}
	
	public int getSlotsFromRune(ItemStack item){
		if(item.hasItemMeta()){
			if(item.getItemMeta().hasDisplayName()){
				if(item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Slot Rune")){
					return new NBTHandler(item).getIntTag("slots");
				}
			}
		}
		return -1;
	}
	
	public Map<String, Integer> getRune(ItemStack rune){
		Map<String, Integer> value = new HashMap<String, Integer>();
		if(rune.hasItemMeta()){
			
			Enchantments enchant = Enchantments.valueOf(new NBTHandler(rune).getStringTag("enchant"));
			int success = new NBTHandler(rune).getIntTag("success");
			int level = new NBTHandler(rune).getIntTag("level");
			int destroy = new NBTHandler(rune).getIntTag("destroy");
			value.put("level", level);
			value.put(enchant.toString(), 10);
			value.put("success", success);
			value.put("destroy", destroy);
		}
		return value;
	}
	
	public boolean isRune(ItemStack rune){
		if(rune.hasItemMeta()){
			if(rune.getType().equals(Material.NETHER_STAR)){
				return true;
			}
			return false;
		}
		return false;
	}
	
	@EventHandler
	public void onMapRightClick(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(event.getItem() != null){
				if(event.getItem().getType().equals(Material.MAP) || event.getItem().getType().equals(Material.EMPTY_MAP)){
					if(isProtectionRune(event.getItem())){
						event.setCancelled(true);
						event.getPlayer().updateInventory();
					}
				}
			}
		}
	}
	
	public boolean isProtectionRune(ItemStack rune){
		if(rune.hasItemMeta()){
			ItemMeta runeMeta = rune.getItemMeta();
			if(runeMeta.getDisplayName().contains(ChatColor.YELLOW + "Protection Rune")){
				return true;
			}
			return false;
		}
		return false;
	}
}
