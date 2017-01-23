package xyz.almia.damagesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import xyz.almia.accountsystem.Account;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.enchantsystem.Enchantments;
import xyz.almia.itemsystem.Armor;
import xyz.almia.itemsystem.ItemType;
import xyz.almia.itemsystem.ItemType.ItemTypes;
import xyz.almia.itemsystem.Weapon;

public class DamageSystem implements Listener{
	
	Plugin plugin = Cardinal.getPlugin();
	
	public int getArmorValue(Player player){
		int armor = 0;
		
		if(player.getInventory().getHelmet() != null){
			if(new ItemType(player.getInventory().getHelmet()).getType().equals(ItemTypes.ARMOR)){
				Armor item = new Armor(player.getInventory().getHelmet());
				armor = armor + item.getArmor();
			}
		}
		if(player.getInventory().getChestplate() != null){
			if(new ItemType(player.getInventory().getChestplate()).getType().equals(ItemTypes.ARMOR)){
				Armor item = new Armor(player.getInventory().getChestplate());
				armor = armor + item.getArmor();
			}
		}
		if(player.getInventory().getLeggings() != null){
			if(new ItemType(player.getInventory().getLeggings()).getType().equals(ItemTypes.ARMOR)){
				Armor item = new Armor(player.getInventory().getLeggings());
				armor = armor + item.getArmor();
			}
		}
		if(player.getInventory().getBoots() != null){
			if(new ItemType(player.getInventory().getBoots()).getType().equals(ItemTypes.ARMOR)){
				Armor item = new Armor(player.getInventory().getBoots());
				armor = armor + item.getArmor();
			}
		}
		
		return armor;
		
	}
	
	public int getDefaultArmorValue(Material material){
		if(material.equals(Material.LEATHER_HELMET)){
			return 1;
		}
		if(material.equals(Material.LEATHER_CHESTPLATE)){
			return 3;
		}
		if(material.equals(Material.LEATHER_LEGGINGS)){
			return 2;
		}
		if(material.equals(Material.LEATHER_BOOTS)){
			return 1;
		}
		if(material.equals(Material.CHAINMAIL_HELMET)){
			return 2;
		}
		if(material.equals(Material.CHAINMAIL_CHESTPLATE)){
			return 5;
		}
		if(material.equals(Material.CHAINMAIL_LEGGINGS)){
			return 4;		}
		if(material.equals(Material.CHAINMAIL_BOOTS)){
			return 1;
		}
		if(material.equals(Material.IRON_HELMET)){
			return 2;
		}
		if(material.equals(Material.IRON_CHESTPLATE)){
			return 6;
		}
		if(material.equals(Material.IRON_LEGGINGS)){
			return 5;
		}
		if(material.equals(Material.IRON_BOOTS)){
			return 2;
		}
		if(material.equals(Material.GOLD_HELMET)){
			return 2;
		}
		if(material.equals(Material.GOLD_CHESTPLATE)){
			return 5;
		}
		if(material.equals(Material.GOLD_LEGGINGS)){
			return 3;
		}
		if(material.equals(Material.GOLD_BOOTS)){
			return 1;
		}
		if(material.equals(Material.DIAMOND_HELMET)){
			return 3;
		}
		if(material.equals(Material.DIAMOND_CHESTPLATE)){
			return 8;
		}
		if(material.equals(Material.DIAMOND_LEGGINGS)){
			return 6;
		}
		if(material.equals(Material.DIAMOND_BOOTS)){
			return 3;
		}
		return 0;
	}
	
	public double applyArmor(Player player, double damage){
		int armor = getArmorValue(player);
		
		return damage * ( 1 - Math.min( 20, Math.max( armor / 5, armor - damage / ( 2 + 0 / 4 ) ) ) / 25 );
		
	}
	
	public HashMap<UUID, ItemStack> playerAndBow = new HashMap<UUID, ItemStack>();
	public List<UUID> petrified = new ArrayList<UUID>();
	
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
									int chance = 0;
									int rand = ThreadLocalRandom.current().nextInt(100);
									int level = enchantments.get(Enchantments.VOLLEY);
									if(level == 1){
										chance = 100;
									}
									if((rand <= chance)){

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
									}else{
										return;
									}
								}
								return;
							}
					}
				
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		
		if(event.getEntity() instanceof Creature){
			double damage = event.getDamage();
			if(event.getDamager() instanceof Player){
				Player attacker = (Player)event.getDamager();
				double itemDamage = 1;
				if(attacker.getInventory().getItemInMainHand() != null){
					itemDamage = 0;
					
					if(new ItemType(attacker.getInventory().getItemInMainHand()).getType().equals(ItemTypes.WEAPON)){
						Weapon item = new Weapon(attacker.getInventory().getItemInMainHand());
						itemDamage = item.getDamage();
						HashMap<Enchantments, Integer> enchants = item.getEnchantsAndLevel();
						if(enchants != null){
							
							if(enchants.containsKey(Enchantments.SHARPENED)){
								itemDamage = itemDamage + (1 + ( (enchants.get(Enchantments.SHARPENED) - 1) * (2* (1/4) ) ) );
							}
							
							if(enchants.containsKey(Enchantments.FLAME)){
								event.getEntity().setFireTicks(event.getEntity().getFireTicks()+(enchants.get(Enchantments.FLAME)*80));
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
									if(event.getEntity() instanceof LivingEntity){
										LivingEntity hit = (LivingEntity)event.getEntity();
										
										hit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 0));
									}
								}
							}
							
							if(enchants.containsKey(Enchantments.DEMON_SIPHON)){
								int chance = 0;
								int rand = ThreadLocalRandom.current().nextInt(100);
								int level = enchants.get(Enchantments.DEMON_SIPHON);
								if(level == 1){
									chance = 8;
								}else if(level == 2){
									chance = 16;
								}else if(level == 3){
									chance = 25;
								}else if(level == 4){
									chance = 32;
								}
								if((rand <= chance)){
									new Account(attacker).getLoadedCharacter().setHealth(new Account(attacker).getLoadedCharacter().getMaxHealth());
									event.getEntity().remove();
									
								}
							}
							
						}
					}
					
					
					
				}
				damage = new Account(attacker).getLoadedCharacter().getPhysicalDamage() + itemDamage;
				
			}
			event.setDamage(damage);
			
		}
		
		if(event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			
			int armor = getArmorValue(player);
			
			double damage = event.getDamage();
			
			if(event.getDamager() instanceof Arrow){
				Arrow arrow = (Arrow) event.getDamager();
				
				if(arrow.getShooter() instanceof Player){
					Player attacker = (Player) arrow.getShooter();
					
					ItemStack bow = playerAndBow.get(attacker.getUniqueId());
					
					Weapon item = new Weapon(bow);
					
					double itemDamage = item.getDamage();
						HashMap<Enchantments, Integer> enchants = item.getEnchantsAndLevel();
						if(enchants != null){
							
							if(enchants.containsKey(Enchantments.HOLY_SMITE)){
								int chance = 0;
								int rand = ThreadLocalRandom.current().nextInt(100);
								int level = enchants.get(Enchantments.HOLY_SMITE);
								if(level == 1){
									chance = 18;
								}else if(level == 2){
									chance = 24;
								}else if(level == 3){
									chance = 32;
								}else if(level == 4){
									chance = 40;
								}else if(level == 5){
									chance = 48;
								}
								if((rand <= chance)){
									player.getWorld().strikeLightningEffect(player.getLocation());
									for(PotionEffect effect : player.getActivePotionEffects()){
										player.removePotionEffect(effect.getType());
									}
								}
							}
							
							if(enchants.containsKey(Enchantments.SNARE)){
								int chance = 0;
								int rand = ThreadLocalRandom.current().nextInt(100);
								int level = enchants.get(Enchantments.SNARE);
								if(level == 1){
									chance = 8;
								}else if(level == 2){
									chance = 13;
								}else if(level == 3){
									chance = 18;
								}else if(level == 4){
									chance = 23;
								}
								if((rand <= chance)){
									
									new BukkitRunnable(){
										int i = (((level*60)/2)/20);
										@Override
										public void run() {
											player.getWorld().spawnParticle(Particle.SLIME, player.getLocation(), 10);
											i--;
											if(i <= 0){
												this.cancel();
											}
										}
										
									}.runTaskTimer(plugin, 0, 20);
									player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (level * 60)/2, 1, true));
									return;
								}
							}
							
							if(enchants.containsKey(Enchantments.SOULSHOT)){
								int chance = 0;
								int rand = ThreadLocalRandom.current().nextInt(100);
								int level = enchants.get(Enchantments.SOULSHOT);
								if(level == 1){
									chance = 18;
								}
								if((rand <= chance)){
									armor = 0;
								}
							}
							
							if(enchants.containsKey(Enchantments.WILD_MARK)){
								int chance = 100;
								int rand = ThreadLocalRandom.current().nextInt(100);
								int level = enchants.get(Enchantments.WILD_MARK);
								if((rand <= chance)){
									itemDamage = itemDamage + (1 + ( (level - 1) * (2* (1/4) ) ) );
								}
							}
							
							
						}
					damage = (int) new Account(attacker).getLoadedCharacter().getPhysicalDamage() + (int)itemDamage;
					
				}
				
			}
			
			if(event.getDamager() instanceof Player){
				Player attacker = (Player)event.getDamager();
				Account attackerAccount = new Account(attacker);
				xyz.almia.accountsystem.Character attackerCharacter = attackerAccount.getLoadedCharacter();
				double itemDamage = 1;
				if(attacker.getInventory().getItemInMainHand() != null){
					itemDamage = 0;
					
					if(new ItemType(attacker.getInventory().getItemInMainHand()).getType().equals(ItemTypes.WEAPON)){
						
						Weapon item = new Weapon(attacker.getInventory().getItemInMainHand());
						itemDamage = item.getDamage();
						HashMap<Enchantments, Integer> enchants = item.getEnchantsAndLevel();
						if(enchants != null){
							
							if(enchants.containsKey(Enchantments.SHARPENED)){
								itemDamage = itemDamage + (1 + ( (enchants.get(Enchantments.SHARPENED) - 1) * (2* (1/4) ) ) );
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
									
									petrified.add(player.getUniqueId());
									new BukkitRunnable(){
										@Override
										public void run() {
											petrified.remove(player.getUniqueId());
										}
									}.runTaskLater(plugin, 30);
									
									
								}
							}
							
							if(enchants.containsKey(Enchantments.FLAME)){
								player.setFireTicks(event.getEntity().getFireTicks()+(enchants.get(Enchantments.FLAME)*80));
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
									if(event.getEntity() instanceof LivingEntity){
										LivingEntity hit = (LivingEntity)event.getEntity();
										
										hit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 0));
									}
								}
							}
							
						}
						
					}
					
					
				}
				damage = (int) attackerCharacter.getPhysicalDamage() + (int)itemDamage;
			}
			
			damage = damage * ( 1 - Math.min( 20, Math.max( armor / 5, armor - damage / ( 2 + 0 / 4 ) ) ) / 25 );
			
			damage = applyProtection(player, damage);
			
			Account account = new Account(player);
			xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
			
			double aegis = 0;
			
			if(player.getInventory().getItemInMainHand() != null){
				
				if(new ItemType(player.getInventory().getItemInMainHand()).getType().equals(ItemTypes.WEAPON)){
					Weapon item = new Weapon(player.getInventory().getItemInMainHand());
					HashMap<Enchantments, Integer> enchants = item.getEnchantsAndLevel();
					if(enchants != null){
						if(enchants.containsKey(Enchantments.AEGIS)){
							int chance = 0;
							int rand = ThreadLocalRandom.current().nextInt(100);
							int level = enchants.get(Enchantments.AEGIS);
							if(level == 1){
								chance = 12;
							}else if(level == 2){
								chance = 24;
							}else if(level == 3){
								chance = 37;
							}else if(level == 4){
								chance = 50;
							}else if(level == 5){
								chance = 63;
							}
							if((rand <= chance)){
								aegis = damage*.20;
							}
						}
					}
				}
			
			}
			
			character.setHealth((int)(character.getHealth() + aegis - damage));
			
			
			if(event.getDamager() instanceof Player){
				Player attacker = (Player)event.getDamager();
				
				if(attacker.getInventory().getItemInMainHand() != null){
					
					if(new ItemType(attacker.getInventory().getItemInMainHand()).getType().equals(ItemTypes.WEAPON)){
						Weapon item = new Weapon(attacker.getInventory().getItemInMainHand());
						int itemDamage = item.getDamage();
						HashMap<Enchantments, Integer> enchants = item.getEnchantsAndLevel();
						if(enchants != null){
							
							if(enchants.containsKey(Enchantments.SHARPENED)){
								itemDamage = itemDamage + (1 + ( (enchants.get(Enchantments.SHARPENED) - 1) * (2* (1/4) ) ) );
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
									player.getWorld().spawnParticle(Particle.CRIT_MAGIC, event.getEntity().getLocation(), 20);
									new Account(attacker).getLoadedCharacter().setHealth((int)(new Account(attacker).getLoadedCharacter().getHealth() + (damage*.35)));
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
									final double finalDamage = (double)itemDamage;
									new BukkitRunnable(){
										@Override
										public void run() {
											player.damage(finalDamage, attacker);
										}
									}.runTaskLater(plugin, 20);
								}
							}
							
						}
					}
				
					
				}
				
			}
			
			event.setDamage(0);
			
		}
		
		
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		if(petrified.contains(event.getPlayer().getUniqueId())){
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.YELLOW+"You are petrified!");
		}
	}
	
	@EventHandler
	public void onDamaged(EntityDamageEvent event){
		
		if(event.getEntity() instanceof Player){
			Player player = (Player)event.getEntity();
			Account account = new Account(player);
			xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
			DamageCause cause = event.getCause();
			Double damage = event.getDamage();
			
			switch(cause){
			case BLOCK_EXPLOSION:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case CONTACT:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case CUSTOM:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case DRAGON_BREATH:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case DROWNING:
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case ENTITY_ATTACK:
				break;
			case ENTITY_EXPLOSION:
				break;
			case FALL:
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case FALLING_BLOCK:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case FIRE:
				break;
			case FIRE_TICK:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case FLY_INTO_WALL:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case HOT_FLOOR:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case LAVA:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case LIGHTNING:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case MAGIC:
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case MELTING:
				break;
			case POISON:
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case PROJECTILE:
				break;
			case STARVATION:
				break;
			case SUFFOCATION:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case SUICIDE:
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case THORNS:
				break;
			case VOID:
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case WITHER:
				damage = applyArmor(player, damage);
				damage = applyProtection(player, damage);
				character.setHealth((int) (character.getHealth() - damage));
				event.setDamage(0);
				break;
			case CRAMMING:
				character.setHealth((int)character.getHealth() - damage);
				event.setDamage(0);
				break;
			default:
				break;
			}
		}
		
	}
		
	@SuppressWarnings({ "unused" })
	public void damage(double originalDamage, DamageType damageType, Player source, LivingEntity damaged){
		double basePhysicalDamage = new Account(source).getLoadedCharacter().getPhysicalDamage();
		double damagedHealth = damaged.getHealth();
		double damagedMaxHealth = damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		
		switch(damageType){
		case MAGICAL:
			damaged.setHealth(damagedHealth - (originalDamage + new Account(source).getLoadedCharacter().getMagicalDamage()));
			break;
		case PHYSICAL:
			damaged.damage(originalDamage, source);
			break;
		}
		
	}
	
	@EventHandler
	public void onPlayerKillCreature(EntityDeathEvent event){
		Player player = (Player)event.getEntity().getKiller();
		if(player == null){
			return;
		}
		new Account(player).getLoadedCharacter().addExp(plugin.getConfig().getInt("Cardinal.enchant."+event.getEntityType().toString().toUpperCase()));
	}
	
	@SuppressWarnings("unused")
	public double applyProtection(Player player, double damage){
		
		ItemStack helmet = null;
		ItemStack chestplate = null;
		ItemStack leggings = null;
		ItemStack boots = null;
		
		int helmEPF = 0;
		int chestEPF = 0;
		int legEPF = 0;
		int bootsEPF = 0;
		
		if(player.getInventory().getHelmet() != null)
			helmet = player.getInventory().getHelmet();
		if(player.getInventory().getChestplate() != null)
			helmet = player.getInventory().getChestplate();
		if(player.getInventory().getLeggings() != null)
			helmet = player.getInventory().getLeggings();
		if(player.getInventory().getBoots() != null)
			helmet = player.getInventory().getBoots();
		
		
		if(helmet != null){
			Armor detailItem = new Armor(helmet);
			if(detailItem.getEnchantsAndLevel() != null){
				HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
				if(enchantments.containsKey(Enchantments.PROTECTION)){
					int level = enchantments.get(Enchantments.PROTECTION);
					if(level == 1){
						helmEPF = 1;
					}else if(level == 2){
						helmEPF = 2;
					}else if(level == 3){
						helmEPF = 3;
					}else if(level == 4){
						helmEPF = 4;
					}
						
				}
			}
		}
		
		if(chestplate != null){
			Armor detailItem = new Armor(chestplate);
				HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
				if(enchantments.containsKey(Enchantments.PROTECTION)){
					int level = enchantments.get(Enchantments.PROTECTION);
					if(level == 1){
						chestEPF = 1;
					}else if(level == 2){
						chestEPF = 2;
					}else if(level == 3){
						chestEPF = 3;
					}else if(level == 4){
						chestEPF = 4;
					}
						
				}
		}
		
		if(leggings != null){
			Armor detailItem = new Armor(leggings);
				HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
				if(enchantments.containsKey(Enchantments.PROTECTION)){
					ItemMeta im = helmet.getItemMeta();
					String[] enchantAndDamage = null;
					int level = enchantments.get(Enchantments.PROTECTION);
					if(level == 1){
						legEPF = 1;
					}else if(level == 2){
						legEPF = 2;
					}else if(level == 3){
						legEPF = 3;
					}else if(level == 4){
						legEPF = 4;
					}
						
				}
		}
		
		if(boots != null){
			Armor detailItem = new Armor(boots);
				HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
				if(enchantments.containsKey(Enchantments.PROTECTION)){
					int level = enchantments.get(Enchantments.PROTECTION);
					if(level == 1){
						bootsEPF = 1;
					}else if(level == 2){
						bootsEPF = 2;
					}else if(level == 3){
						bootsEPF = 3;
					}else if(level == 4){
						bootsEPF = 4;
					}
						
				}
		}
		
		
		int totalEPF = helmEPF + chestEPF + legEPF + bootsEPF;
		
		double damageFinal = damage * ( 1 - totalEPF / 25 );
		
		return damageFinal;
	}
	
}
