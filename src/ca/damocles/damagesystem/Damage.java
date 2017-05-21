package ca.damocles.damagesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.enchantsystem.Enchantments;
import ca.damocles.itemblueprints.Armor;
import ca.damocles.itemblueprints.Weapon;
import ca.damocles.itemsystem.ItemType;
import ca.damocles.itemsystem.ItemType.ItemTypes;
import ca.damocles.storagesystem.Equips.Slot;

public class Damage implements Listener{
	
	Plugin plugin = Cardinal.getPlugin();
	List<String> petrified = new ArrayList<String>();
	
	public double applySharpness(double damage, int level){
		return damage + (1 + ( (level - 1) * (2 * (1 / 4) ) ) );
	}
	
	public double getArmorValue(Player player){
		return player.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue();
	}
	
	public double applyArmor(double damage, double armor){
		return damage * ( 1 - Math.min( 20, Math.max( armor / 5, armor - damage / ( 2 + 0 / 4 ) ) ) / 25 );
	}
	
	public int getEPC(Player player){
		ca.damocles.accountsystem.Character character = new Account(player).getLoadedCharacter();
		Armor helm = null;
		Armor chest = null;
		Armor legs = null;
		Armor boots = null;
		if(character.getEquip(Slot.HELMET) != null)
			helm = new Armor(character.getEquip(Slot.HELMET));
		if(character.getEquip(Slot.CHESTPLATE) != null)
			chest = new Armor(character.getEquip(Slot.CHESTPLATE));
		if(character.getEquip(Slot.LEGGINGS) != null)
			legs = new Armor(character.getEquip(Slot.LEGGINGS));
		if(character.getEquip(Slot.BOOTS) != null)
			boots = new Armor(character.getEquip(Slot.BOOTS));
		int helmEPF = 0;
		int chestEPF = 0;
		int legEPF = 0;
		int bootsEPF = 0;
		if(helm != null){
			if(helm.getEnchantsAndLevel() != null){
				if(helm.getEnchantsAndLevel().containsKey(Enchantments.PROTECTION)){
					helmEPF = helm.getEnchantsAndLevel().get(Enchantments.PROTECTION);
				}
			}
		}
		if(chest != null){
			if(chest.getEnchantsAndLevel() != null){
				if(chest.getEnchantsAndLevel().containsKey(Enchantments.PROTECTION)){
					chestEPF = chest.getEnchantsAndLevel().get(Enchantments.PROTECTION);
				}
			}
		}
		if(legs != null){
			if(legs.getEnchantsAndLevel() != null){
				if(legs.getEnchantsAndLevel().containsKey(Enchantments.PROTECTION)){
					legEPF = legs.getEnchantsAndLevel().get(Enchantments.PROTECTION);
				}
			}
		}
		if(boots != null){
			if(boots.getEnchantsAndLevel() != null){
				if(boots.getEnchantsAndLevel().containsKey(Enchantments.PROTECTION)){
					bootsEPF = boots.getEnchantsAndLevel().get(Enchantments.PROTECTION);
				}
			}
		}
		return helmEPF + chestEPF + legEPF + bootsEPF;
	}
	
	public double applyProtection(double damage, int EPF){
		return damage * ( 1 - EPF / 25 );
	}
	
	public void playerDamagePlayer(Player damaged, Player damager, ItemStack weapon, double rawDamage, DamageType cause){
		damaged.damage(0.1);
		Weapon mweapon = null;
		HashMap<Enchantments, Integer> enchants = new HashMap<Enchantments, Integer>();
		
		if(new ItemType(weapon).getType().equals(ItemTypes.WEAPON)){
			mweapon = new Weapon(weapon);
		}
		
		Account damagedAccount = new Account(damaged);
		Account damagerAccount = new Account(damager);
		ca.damocles.accountsystem.Character loadedDamaged = damagedAccount.getLoadedCharacter();
		ca.damocles.accountsystem.Character loadedDamager = damagerAccount.getLoadedCharacter();
		
		switch(cause){
			case MAGICAL:
				double magicdamage = rawDamage;
				magicdamage = applyProtection(magicdamage, getEPC(damaged));
				loadedDamaged.setHealth(loadedDamaged.getHealth() - magicdamage);
				return;
			case PHYSICAL:
				
				double damage = 0.0;
				
				//Damage Application
				if(mweapon != null){
					enchants = mweapon.getEnchantsAndLevel();
					damage = mweapon.getDamage() + loadedDamager.getPhysicalDamage();
				}else{
					damage = 0.0 + loadedDamager.getPhysicalDamage();
				}
				if(enchants.containsKey(Enchantments.SHARPENED))
					damage = applySharpness(damage, mweapon.getEnchantsAndLevel().get(Enchantments.SHARPENED));
				damage = applyArmor(damage, getArmorValue(damaged));
				damage = applyProtection(damage, getEPC(damaged));
				loadedDamaged.setHealth(loadedDamaged.getHealth() - damage);
				
				//After Enchantments
				if(enchants.containsKey(Enchantment.FIRE_ASPECT)){
					damaged.setFireTicks(damaged.getFireTicks()+(enchants.get(Enchantments.FLAME)*80));
				}
				if(enchants.containsKey(Enchantments.ASSASSIN)){
					int chance = 0;
					int rand = ThreadLocalRandom.current().nextInt(100);
					int level = enchants.get(Enchantments.ASSASSIN);
					if(level == 1){
						chance = 12;
					}else if(level == 2){
						chance = 24;
					}else if(level == 3){
						chance = 37;
					}
					if((rand <= chance)){
						damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 0));
					}
				}
				if(enchants.containsKey(Enchantments.PETRIFY)){
					int chance = 0;
					int rand = ThreadLocalRandom.current().nextInt(100);
					int level = enchants.get(Enchantments.PETRIFY);
					if(level == 1){
						chance = 5;
					}else if(level == 2){
						chance = 10;
					}else if(level == 3){
						chance = 15;
					}else if(level == 4){
						chance = 20;
					}else if(level == 5){
						chance = 25;
					}
					if((rand <= chance)){
						
						petrified.add(loadedDamaged.getUsername());
						new BukkitRunnable(){
							@Override
							public void run() {
								petrified.remove(loadedDamaged.getUsername());
							}
						}.runTaskLater(plugin, 30);
						
						
					}
				}
				if(enchants.containsKey(Enchantments.LIFESTEAL)){
					int chance = 0;
					int rand = ThreadLocalRandom.current().nextInt(100);
					int level = enchants.get(Enchantments.LIFESTEAL);
					if(level == 1){
						chance = 8;
					}else if(level == 2){
						chance = 13;
					}else if(level == 3){
						chance = 18;
					}
					if((rand <= chance)){
						damaged.getWorld().spawnParticle(Particle.CRIT_MAGIC, damaged.getLocation(), 20);
						loadedDamager.setHealth(loadedDamager.getHealth()+(damage*.35));
					}
				}
				
				if(enchants.containsKey(Enchantments.SWIPE)){
					int chance = 0;
					int rand = ThreadLocalRandom.current().nextInt(100);
					int level = enchants.get(Enchantments.SWIPE);
					if(level == 1){
						chance = 12;
					}else if(level == 2){
						chance = 18;
					}else if(level == 3){
						chance = 24;
					}else if(level == 4){
						chance = 30;
					}else if(level == 5){
						chance = 36;
					}
					if((rand <= chance)){
						new BukkitRunnable(){
							@Override
							public void run() {
								playerDamagePlayer(damager, damager, weapon, 0.0, cause);
							}
						}.runTaskLater(plugin, 20);
					}
				}
				
				return;
			case ENVIRONMENTAL:
				break;
			default:
				break;
		}
		
		return;
		
	}
	
	public void playerDamageCreature(Creature damaged, Player damager, ItemStack weapon, double rawDamage, DamageType cause){
		damaged.damage(0.1);
		Weapon mweapon = null;
		HashMap<Enchantments, Integer> enchants = new HashMap<Enchantments, Integer>();
		
		
		if(new ItemType(weapon).getType().equals(ItemTypes.WEAPON)){
			mweapon = new Weapon(weapon);
		}
		
		
		Account damagerAccount = new Account(damager);
		ca.damocles.accountsystem.Character loadedDamager = damagerAccount.getLoadedCharacter();
		
		switch(cause){
		case ENVIRONMENTAL:
			break;
		case MAGICAL:
			break;
		case PHYSICAL:
			double damage = 0.0;
			
			//Damage Application
			if(mweapon != null){
				enchants = mweapon.getEnchantsAndLevel();
				damage = mweapon.getDamage() + loadedDamager.getPhysicalDamage();
			}else{
				damage = 0.0 + loadedDamager.getPhysicalDamage();
			}
			if(enchants.containsKey(Enchantments.SHARPENED))
				damage = applySharpness(damage, mweapon.getEnchantsAndLevel().get(Enchantments.SHARPENED));
			
			try{
				damaged.setHealth(damaged.getHealth() - damage);
			}catch(IllegalArgumentException e){
				damaged.setHealth(0.0);
			}
			
			if(enchants.containsKey(Enchantment.FIRE_ASPECT)){
				damaged.setFireTicks(damaged.getFireTicks()+(enchants.get(Enchantments.FLAME)*80));
			}
			if(enchants.containsKey(Enchantments.ASSASSIN)){
				int chance = 0;
				int rand = ThreadLocalRandom.current().nextInt(100);
				int level = enchants.get(Enchantments.ASSASSIN);
				if(level == 1){
					chance = 12;
				}else if(level == 2){
					chance = 24;
				}else if(level == 3){
					chance = 37;
				}
				if((rand <= chance)){
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 0));
				}
			}
			if(enchants.containsKey(Enchantments.LIFESTEAL)){
				int chance = 0;
				int rand = ThreadLocalRandom.current().nextInt(100);
				int level = enchants.get(Enchantments.LIFESTEAL);
				if(level == 1){
					chance = 8;
				}else if(level == 2){
					chance = 13;
				}else if(level == 3){
					chance = 18;
				}
				if((rand <= chance)){
					damaged.getWorld().spawnParticle(Particle.CRIT_MAGIC, damaged.getLocation(), 20);
					loadedDamager.setHealth(loadedDamager.getHealth()+(damage*.35));
				}
			}
			
			if(enchants.containsKey(Enchantments.SWIPE)){
				int chance = 0;
				int rand = ThreadLocalRandom.current().nextInt(100);
				int level = enchants.get(Enchantments.SWIPE);
				if(level == 1){
					chance = 12;
				}else if(level == 2){
					chance = 18;
				}else if(level == 3){
					chance = 24;
				}else if(level == 4){
					chance = 30;
				}else if(level == 5){
					chance = 36;
				}
				if((rand <= chance)){
					new BukkitRunnable(){
						@Override
						public void run() {
							playerDamagePlayer(damager, damager, weapon, 0.0, cause);
						}
					}.runTaskLater(plugin, 20);
				}
			}
			return;
		default:
			break;
		}
		return;
	}

	public void playerDamageEntity(Damageable damaged, Player damager, ItemStack weapon, double rawDamage, DamageType cause){
		damaged.damage(0.1);
		Weapon mweapon = null;
		HashMap<Enchantments, Integer> enchants = new HashMap<Enchantments, Integer>();
		
		
		if(new ItemType(weapon).getType().equals(ItemTypes.WEAPON)){
			mweapon = new Weapon(weapon);
		}
		
		
		Account damagerAccount = new Account(damager);
		ca.damocles.accountsystem.Character loadedDamager = damagerAccount.getLoadedCharacter();
		
		switch(cause){
		case ENVIRONMENTAL:
			break;
		case MAGICAL:
			break;
		case PHYSICAL:
			double damage = 0.0;
			
			//Damage Application
			if(mweapon != null){
				enchants = mweapon.getEnchantsAndLevel();
				damage = mweapon.getDamage() + loadedDamager.getPhysicalDamage();
			}else{
				damage = 0.0 + loadedDamager.getPhysicalDamage();
			}
			if(enchants.containsKey(Enchantments.SHARPENED))
				damage = applySharpness(damage, mweapon.getEnchantsAndLevel().get(Enchantments.SHARPENED));
			
			try{
				damaged.setHealth(damaged.getHealth() - damage);
			}catch(IllegalArgumentException e){
				damaged.setHealth(0.0);
			}
			
			if(enchants.containsKey(Enchantment.FIRE_ASPECT)){
				damaged.setFireTicks(damaged.getFireTicks()+(enchants.get(Enchantments.FLAME)*80));
			}
			/*if(enchants.containsKey(Enchantments.ASSASSIN)){
				int chance = 0;
				int rand = ThreadLocalRandom.current().nextInt(100);
				int level = enchants.get(Enchantments.ASSASSIN);
				if(level == 1){
					chance = 12;
				}else if(level == 2){
					chance = 24;
				}else if(level == 3){
					chance = 37;
				}
				if((rand <= chance)){
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 0));
				}
			}*/
			if(enchants.containsKey(Enchantments.LIFESTEAL)){
				int chance = 0;
				int rand = ThreadLocalRandom.current().nextInt(100);
				int level = enchants.get(Enchantments.LIFESTEAL);
				if(level == 1){
					chance = 8;
				}else if(level == 2){
					chance = 13;
				}else if(level == 3){
					chance = 18;
				}
				if((rand <= chance)){
					damaged.getWorld().spawnParticle(Particle.CRIT_MAGIC, damaged.getLocation(), 20);
					loadedDamager.setHealth(loadedDamager.getHealth()+(damage*.35));
				}
			}
			
			if(enchants.containsKey(Enchantments.SWIPE)){
				int chance = 0;
				int rand = ThreadLocalRandom.current().nextInt(100);
				int level = enchants.get(Enchantments.SWIPE);
				if(level == 1){
					chance = 12;
				}else if(level == 2){
					chance = 18;
				}else if(level == 3){
					chance = 24;
				}else if(level == 4){
					chance = 30;
				}else if(level == 5){
					chance = 36;
				}
				if((rand <= chance)){
					new BukkitRunnable(){
						@Override
						public void run() {
							playerDamagePlayer(damager, damager, weapon, 0.0, cause);
						}
					}.runTaskLater(plugin, 20);
				}
			}
			return;
		default:
			break;
		}
		return;
	}
	
	public void entityDamagePlayer(Player damaged, Entity damager, double rawDamage, DamageType cause){
		damaged.damage(0.1);
		Account account = new Account(damaged);
		ca.damocles.accountsystem.Character character = account.getLoadedCharacter();
		
		switch(cause){
		case ENVIRONMENTAL:
			break;
		case MAGICAL:
			break;
		case PHYSICAL:
			double damage = rawDamage;
			character.setHealth(character.getHealth() - damage);
			return;
		default:
			break;
		}
		return;
	}
	
	public void environmentDamagePlayer(Player damaged, double rawDamage){
		Account account = new Account(damaged);
		ca.damocles.accountsystem.Character character = account.getLoadedCharacter();
		double damage = rawDamage;
		damage = applyArmor(damage, getArmorValue(damaged));
		damage = applyProtection(damage, getEPC(damaged));
		character.setHealth(character.getHealth() - damage);
	}
	
	@EventHandler
	public void onDamaged(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			environmentDamagePlayer(player, event.getDamage());
			event.setDamage(0.0);
			return;
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof Player){
			Player damaged = (Player)event.getEntity();
			if(event.getDamager() instanceof Player){
				Player damager = (Player)event.getDamager();
				playerDamagePlayer(damaged, damager, damager.getInventory().getItemInMainHand(), 0.0, DamageType.PHYSICAL);
				event.setDamage(0.0);
				return;
			}
			if(event.getDamager() instanceof Creature){
				entityDamagePlayer(damaged, event.getDamager(), event.getDamage(), DamageType.PHYSICAL);
				event.setDamage(0.0);
				return;
			}
			return;
		}
		if(event.getEntity() instanceof Creature){
			Creature creature = (Creature) event.getEntity();
			if(event.getDamager() instanceof Player){
				Player damager = (Player)event.getDamager();
				playerDamageCreature(creature, damager, damager.getInventory().getItemInMainHand(), 0.0, DamageType.PHYSICAL);
				event.setDamage(0.0);
				return;
			}
		}
		return;
	}
	
	@EventHandler
	public void onPlayerKillCreature(EntityDeathEvent event){
		Player player = (Player)event.getEntity().getKiller();
		if(player == null){
			return;
		}
		new Account(player).getLoadedCharacter().addExp(plugin.getConfig().getInt("Cardinal.enchant."+event.getEntityType().toString().toUpperCase()));
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		if(new Account(event.getPlayer()).getStatus().equals(AccountStatus.LOGGEDIN)){
			if(petrified.contains(new Account(event.getPlayer()).getLoadedCharacter().getUsername())){
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.YELLOW+"You are petrified!");
			}
		}
	}
	
}
