package xyz.almia.enchantsystem;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.itemsystem.Armor;
import xyz.almia.itemsystem.ItemHandler;

public class Speed {
	
	Plugin plugin = Cardinal.getPlugin();
	ItemHandler itemhandler = new ItemHandler();
	
	public Speed() {}
	
	public void checkForSpeedEnchant(){
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					if(player.getInventory().getBoots() != null){
						ItemStack item = player.getInventory().getBoots();
						if(itemhandler.getEnchantType(item).equals(EnchantTypes.BOOTS)){
							Armor detailItem = new Armor(item);
								HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
								if(enchantments.containsKey(Enchantments.SPEED)){
									int amp = 0;
									int level = enchantments.get(Enchantments.SPEED);
									if(level == 1){
										amp = 0;
									}else if(level == 2){
										amp = 1;
									}else if(level == 3){
										amp = 2;
									}
										player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, amp));
								}
						}
					}
				}
			}
			
        }, 0, 1);
	}
	
}
