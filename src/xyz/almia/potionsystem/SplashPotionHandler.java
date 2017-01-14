package xyz.almia.potionsystem;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import xyz.almia.accountsystem.Account;

public class SplashPotionHandler implements Listener{
	
	HashMap<UUID, Effect> potion = new HashMap<UUID, Effect>();
	
	@EventHandler
	public void onPotionPrepair(PlayerInteractEvent event){
		Player player = event.getPlayer();
		
		if(potion.containsKey(player.getUniqueId())){
			potion.remove(player.getUniqueId());
		}
		
		if(player.getInventory().getItemInMainHand().getType().equals(Material.SPLASH_POTION)){
			try{
				Potion custompotion = new Potion(player.getInventory().getItemInMainHand());
				potion.put(player.getUniqueId(), custompotion.effect);
				return;
			}catch(IllegalArgumentException e){
				return;
			}
		}
		if(player.getInventory().getItemInOffHand().getType().equals(Material.SPLASH_POTION)){
			try{
				Potion custompotion = new Potion(player.getInventory().getItemInOffHand());
				potion.put(player.getUniqueId(), custompotion.effect);
				return;
			}catch(IllegalArgumentException e){
				return;
			}
		}
		
		return;
		
	}
	
	@EventHandler
	public void onPotionSplash(PotionSplashEvent event){
		if(event.getEntity().getShooter() instanceof Player){
			Player player = (Player) event.getEntity().getShooter();
			if(potion.containsKey(player.getUniqueId())){
				Effect effect = potion.get(player.getUniqueId());
				
				if(effect.getType().equals(PotionType.EXPLOSION)){
					event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), effect.getAmplifier());
					return;
				}
				if(effect.getType().equals(PotionType.LIGHTNING)){
					event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
					return;
				}
				
				for(Entity entity : event.getAffectedEntities()){
					
					if(entity instanceof LivingEntity){
						LivingEntity living = (LivingEntity) entity;
						switch(effect.getType()){
						case HARM:
							if(entity instanceof Player){
								try{
									new Account((Player) entity).getLoadedCharacter().setHealth(new Account((Player) entity).getLoadedCharacter().getHealth() - (4*effect.getAmplifier()));
								}catch(Exception e) { return; }
							}else{
								try{
									living.setHealth(living.getHealth() - (4*effect.getAmplifier()));
								}catch(Exception e) { return; }
							}
							return;
						case HEAL:
							if(entity instanceof Player){
								try{
									new Account((Player) entity).getLoadedCharacter().setHealth(new Account((Player) entity).getLoadedCharacter().getHealth() + (4*effect.getAmplifier()));
								}catch(Exception e) { return; }
							}else{
								try{
									living.setHealth(living.getHealth() + (4*effect.getAmplifier()));
								}catch(Exception e) { return; }
							}
							return;
						case MANA:
							if(entity instanceof Player){
								try{
									new Account((Player) entity).getLoadedCharacter().setMana(new Account((Player) entity).getLoadedCharacter().getMana() + (5*effect.getAmplifier()));
								}catch(Exception e) { return; }
							}
							return;
						case BLIND:
							living.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, effect.getDuration(), effect.getAmplifier()));
							return;
						case FIRE:
							living.setFireTicks(effect.getDuration());
							return;
						case FIRE_RESIST:
							living.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, effect.getDuration(), effect.getAmplifier()));
							return;
						case HUNGER:
							living.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, effect.getDuration(), effect.getAmplifier()));
							return;
						case JUMP:
							living.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, effect.getDuration(), effect.getAmplifier()));
							return;
						case NAUSEA:
							living.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, effect.getDuration(), effect.getAmplifier()));
							return;
						case NONE:
							return;
						case POISON:
							living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, effect.getDuration(), effect.getAmplifier()));
							return;
						case REGEN:
							living.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, effect.getDuration(), effect.getAmplifier()));
							return;
						case RESISTANCE:
							living.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effect.getDuration(), effect.getAmplifier()));
							return;
						case SLOW:
							living.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effect.getDuration(), effect.getAmplifier()));
							return;
						case SPEED:
							living.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, effect.getDuration(), effect.getAmplifier()));
							return;
						case WEAKNESS:
							living.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, effect.getDuration(), effect.getAmplifier()));
							return;
						case WITHER:
							living.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, effect.getDuration(), effect.getAmplifier()));
							return;
						default:
							return;
						}
					}
					
				}
				
				
				potion.remove(player.getUniqueId());
				return;
			}
			return;
		}
	}

	
}
