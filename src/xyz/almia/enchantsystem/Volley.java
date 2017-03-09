package xyz.almia.enchantsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import xyz.almia.itemblueprints.Weapon;
import xyz.almia.itemsystem.ItemType;
import xyz.almia.itemsystem.ItemType.ItemTypes;

public class Volley implements Listener{
	
	public HashMap<UUID, ItemStack> playerAndBow = new HashMap<UUID, ItemStack>();
	
	@EventHandler
	public void arrowFireEvent(ProjectileLaunchEvent event){
		if(event.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) event.getEntity();
			if(arrow.getShooter() instanceof Player){
				Player player = (Player) arrow.getShooter();
				if(playerAndBow.containsKey(player.getUniqueId())){
					playerAndBow.remove(player.getUniqueId());
				}
				playerAndBow.put(player.getUniqueId(), player.getInventory().getItemInMainHand());
			}
		}
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent event){
		if(event.getEntityType().equals(EntityType.ARROW)){
			Arrow arrow = (Arrow) event.getEntity();
			if(arrow.getShooter() instanceof Player){
				Player player = (Player) arrow.getShooter();
				
					ItemStack item = playerAndBow.get(player.getUniqueId());
					if(new ItemType(item).getType().equals(ItemTypes.WEAPON)){
						Weapon detailItem = new Weapon(item);
							if(detailItem.getEnchantsAndLevel() != null){
								HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
								if(enchantments.containsKey(Enchantments.VOLLEY)){
						            Location loc = arrow.getLocation().add(0, 10, 0);
						            List<Location> locs = new ArrayList<Location>();
						                for(int x = -2; x <= 2; x++) {
						                for(int z = -2; z <= 2; z++) {
						                	locs.add(loc.clone().add(x, 0, z));
						                }
						                }
						                for(Location arrowSpot : locs) {
						                    	arrowSpot.getWorld().spawnEntity(arrowSpot, EntityType.ARROW);
						                  	}
										return;
								}
								return;
							}
					}
				
			}
		}
	}
	
}
