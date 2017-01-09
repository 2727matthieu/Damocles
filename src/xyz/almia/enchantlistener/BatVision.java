package xyz.almia.enchantlistener;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.enchantsystem.EnchantTypes;
import xyz.almia.enchantsystem.Enchantments;
import xyz.almia.itemsystem.Armor;
import xyz.almia.itemsystem.ItemHandler;

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
						
						if(itemhandler.getEnchantType(item).equals(EnchantTypes.HELMET)){
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
