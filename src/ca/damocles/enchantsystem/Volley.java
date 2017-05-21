package ca.damocles.enchantsystem;

import java.util.HashMap;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import ca.damocles.itemblueprints.Bow;
import ca.damocles.itemsystem.ItemType;
import ca.damocles.itemsystem.ItemType.ItemTypes;

public class Volley implements Listener{
		
	@EventHandler
	public void arrowFireEvent(ProjectileLaunchEvent event){
		if(event.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) event.getEntity();
			if(arrow.getShooter() instanceof Player){
				Player player = (Player) arrow.getShooter();
				ItemStack item = player.getInventory().getItemInMainHand();
				if(new ItemType(item).getType().equals(ItemTypes.BOW)){
					Bow detailItem = new Bow(item);
						if(detailItem.getEnchantsAndLevel() != null){
							HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
							if(enchantments.containsKey(Enchantments.VOLLEY)){
								for(int i = 0; i < 10; i++){
									Arrow newArrow = player.getWorld().spawnArrow(
											player.getLocation().add(player.getLocation().getDirection()).add(0, 2, 0),
											player.getLocation().getDirection().normalize().multiply(2),
											(float) 2,
											12);
									if(arrow.isCritical()){
										newArrow.setCritical(true);
									}
								}
							}
							return;
						}
				}
			}
		}
	}
	
}
