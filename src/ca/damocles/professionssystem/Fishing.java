package ca.damocles.professionssystem;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class Fishing implements Listener{
	
	@EventHandler
	public void onPlayerCatchEvent(PlayerFishEvent event){
		if(event.getCaught() instanceof Item){
			Item drop = (Item)event.getCaught();
			drop.setItemStack(new ItemStack(Material.APPLE, 1));
		}
	}
	
}
