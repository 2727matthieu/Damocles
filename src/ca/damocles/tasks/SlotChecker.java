package ca.damocles.tasks;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ca.damocles.enchantsystem.Enchantments;
import ca.damocles.itemblueprints.Armor;
import ca.damocles.itemblueprints.Bow;
import ca.damocles.itemblueprints.Weapon;
import ca.damocles.itemsystem.ItemType;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.spellsystem.SpellBook;
import ca.damocles.utils.Data;

public class SlotChecker extends BukkitRunnable{
	
	@SuppressWarnings("unused")
	private final Plugin plugin;

    public SlotChecker(Plugin plugin) {
        this.plugin = plugin;
    }

	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			for(ItemStack item : p.getInventory().getContents()){
				if(item != null){
					
					if(new ItemType(item).getType().equals(ItemTypes.SPELLBOOK)){
						for(int i = 0; i < 36; i++){
							if(p.getInventory().getItem(i) != null){
								if(p.getInventory().getItem(i).equals(item)){
									if(!(new SpellBook(item).getItemStack().equals(item))){
										p.getInventory().setItem(i, new SpellBook(item).getItemStack());
									}
								}
							}
						}
					}
					
					if(new ItemType(item).getRawType().equals(ItemTypes.ARMOR)){
						Armor detailItem = new Armor(item);
						if(!(detailItem.isItemSet())){
							int slots = ThreadLocalRandom.current().nextInt(3);
							int reforges = ThreadLocalRandom.current().nextInt(2);
							int upgrades = ThreadLocalRandom.current().nextInt(4);
							int weight = Data.getData().getDefaultWeight(ItemTypes.ARMOR);
							int armor = Data.getData().getDefaultArmor(item.getType());
							int maxdurability = ThreadLocalRandom.current().nextInt(143) + 100;
							int durability = ThreadLocalRandom.current().nextInt(41) + 50;
							detailItem.setup(new HashMap<Enchantments, Integer>(), Data.getData().getItemName(item.getType()), slots, 0, 0, 0, 0, armor, reforges, weight, upgrades, false, durability, maxdurability, 0);
							for(int i = 0; i < 36; i++){
								if(p.getInventory().getItem(i) != null){
									if(p.getInventory().getItem(i).equals(item)){
										p.getInventory().setItem(i, detailItem.getItemStack());
									}
								}
							}
							p.updateInventory();
						}
					}
					if(new ItemType(item).getRawType().equals(ItemTypes.BOW)){
						Bow detailItem = new Bow(item);
						if(!(detailItem.isItemSet())){
							int slots = ThreadLocalRandom.current().nextInt(3);
							int reforges = ThreadLocalRandom.current().nextInt(2);
							int upgrades = ThreadLocalRandom.current().nextInt(4);
							int maxdurability = ThreadLocalRandom.current().nextInt(143) + 100;
							int durability = ThreadLocalRandom.current().nextInt(41) + 50;
							int weight = Data.getData().getDefaultWeight(ItemTypes.WEAPON);
							int damage = Data.getData().getDefaultDamage(item.getType());
							detailItem.setup(new HashMap<Enchantments, Integer>(), Data.getData().getItemName(item.getType()), slots, 0, 0, 0, 0, damage, reforges, weight, upgrades, false, durability, maxdurability, 0);
							for(int i = 0; i < 36; i++){
								if(p.getInventory().getItem(i) != null){
									if(p.getInventory().getItem(i).equals(item)){
										p.getInventory().setItem(i, detailItem.getItemStack());
									}
								}
							}
							p.updateInventory();
						}
					}
					if(new ItemType(item).getRawType().equals(ItemTypes.WEAPON)){
						Weapon detailItem = new Weapon(item);
						if(!(detailItem.isItemSet())){
							int slots = ThreadLocalRandom.current().nextInt(3);
							int reforges = ThreadLocalRandom.current().nextInt(2);
							int upgrades = ThreadLocalRandom.current().nextInt(4);
							int maxdurability = ThreadLocalRandom.current().nextInt(143) + 100;
							int durability = ThreadLocalRandom.current().nextInt(41) + 50;
							int weight = Data.getData().getDefaultWeight(ItemTypes.WEAPON);
							int damage = Data.getData().getDefaultDamage(item.getType());
							detailItem.setup(new HashMap<Enchantments, Integer>(), Data.getData().getItemName(item.getType()), slots, 0, 0, 0, 0, damage, reforges, weight, upgrades, false, durability, maxdurability, 0);
							for(int i = 0; i < 36; i++){
								if(p.getInventory().getItem(i) != null){
									if(p.getInventory().getItem(i).equals(item)){
										p.getInventory().setItem(i, detailItem.getItemStack());
									}
								}
							}
							p.updateInventory();
						}
					}
					
				}
			}
		}
		
	}

}
