package ca.damocles.soulsystem;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoulSystem implements Listener{
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event){
		if(event.getBlock().getType().equals(Material.MOB_SPAWNER)){
			event.setCancelled(true);
			event.getBlock().setType(Material.GLASS);
			event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), createSoul());
		}
	}
	
	public ItemStack createSoul(){
		ItemStack item = new ItemStack(Material.MONSTER_EGG);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(ChatColor.DARK_GRAY+"Monster Soul");
		itemmeta.setLore(Arrays.asList(ChatColor.GRAY+"The Soul of a monster lives inside."));
		item.setItemMeta(itemmeta);
		return item;
	}
	
	//@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.MONSTER_EGG)){
				event.setCancelled(true);
			}
		}
	}
}
