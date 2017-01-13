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

public class Eyepatch {
	
	Plugin plugin = Cardinal.getPlugin();
	ItemHandler itemhandler = new ItemHandler();
	
	public Eyepatch() {}
	
	public void checkForEyepatchEnchant(){
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
								if(enchantments.containsKey(Enchantments.EYEPATCH)){
									if(player.getActivePotionEffects().contains(PotionEffectType.BLINDNESS)){
										player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0, 0));
									}
								}
						}
					}
				}
				
			}
        	
        }, 0, 1);
	}
	
}
