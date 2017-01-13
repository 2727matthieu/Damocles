package xyz.almia.arrowsystem;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ArrowHandler implements Listener{
	
	HashMap<UUID, Inventory> saved = new HashMap<UUID, Inventory>();
	
	@EventHandler
	public void onArrowShoot(ProjectileLaunchEvent event){
		if(event.getEntity().getShooter() instanceof Player){
			Player player = (Player) event.getEntity().getShooter();
			if(event.getEntity() instanceof Arrow){
				saved.put(player.getUniqueId(), player.getInventory());
			}
		}
	}
	
	@EventHandler
	public void onArrowLand(ProjectileHitEvent event){
		if(event.getEntity() instanceof Arrow){
			
			if(event.getEntity().getShooter() instanceof Player){
				Player player = (Player) event.getEntity().getShooter();
				
				Inventory before = saved.get(player.getUniqueId());
				Inventory after = player.getInventory();
				
				Inventory newInv = before;
				
				for(ItemStack item : before.getContents()){
					for(ItemStack another : after.getContents()){
						if(item != null){
							if(another != null){
								if(item.equals(another)){
									newInv.remove(item);
								}
							}
						}
					}
				}
				
				ItemStack theItem = null;
				
				for(ItemStack left : newInv.getContents()){
					if(left != null){
						if(left.getType().equals(Material.TIPPED_ARROW)){
							theItem = left;
						}
					}
				}
				
				if(theItem != null){
					if(event.getHitBlock() != null){
						CustomArrow arrow = new CustomArrow(theItem);
						switch(arrow.getType()){
						case EXPLOSION:
							event.getHitBlock().getWorld().createExplosion(event.getHitBlock().getLocation(), arrow.getAmplifier());
							return;
						case LIGHTNING:
							event.getHitBlock().getWorld().strikeLightning(event.getHitBlock().getLocation());
							return;
						default:
							return;
						}
					}
					if(event.getHitEntity() != null){
						CustomArrow arrow = new CustomArrow(theItem);
						switch(arrow.getType()){
						case BLIND:
							break;
						case EXPLOSION:
							break;
						case FIRE:
							break;
						case FIRE_RESIST:
							break;
						case HARM:
							break;
						case HEAL:
							break;
						case HUNGER:
							break;
						case JUMP:
							break;
						case LIGHTNING:
							break;
						case MANA:
							break;
						case NAUSEA:
							break;
						case NONE:
							break;
						case POISON:
							break;
						case REGEN:
							break;
						case RESISTANCE:
							break;
						case SLOW:
							break;
						case SPEED:
							break;
						case WEAKNESS:
							break;
						case WITHER:
							break;
						default:
							break;
						
						}
					}
				}
				
			}
			
		}
		
	}
	
}
