package ca.damocles.enchantsystem;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.itemblueprints.Armor;
import ca.damocles.itemsystem.ItemHandler;
import ca.damocles.itemsystem.ItemType;
import ca.damocles.itemsystem.ItemType.ArmorTypes;

public class BatVision {
	
	public BatVision(){}
	
	Plugin plugin = Cardinal.getPlugin();
	ItemHandler itemhandler = new ItemHandler();
	
	public void checkForBatEnchant(){
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				
				for(Player player : Bukkit.getOnlinePlayers()){
					if(player.getInventory().getHelmet() != null){
						ItemStack item = player.getInventory().getHelmet();
						
						if(new ItemType(item).getArmorType().equals(ArmorTypes.HEAD)){
							Armor detailItem = new Armor(item);
							HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
							if(enchantments.containsKey(Enchantments.BAT_VISION)){
								int amp = 0;
								int level = enchantments.get(Enchantments.BAT_VISION);
								if(level == 1){
									amp = 0;
								}
								player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, amp));
							}

						}
					}
				}
				
			}
        	
        }, 0, 1);
	}
	
}
