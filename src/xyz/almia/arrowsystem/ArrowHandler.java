package xyz.almia.arrowsystem;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.almia.accountsystem.Account;
import xyz.almia.potionsystem.Effect;

public class ArrowHandler implements Listener{
	
	HashMap<UUID, Effect> arrows = new HashMap<UUID, Effect>();
	HashMap<UUID, Effect> shot = new HashMap<UUID, Effect>();
	
	@EventHandler
	public void onBowPrepair(PlayerInteractEvent event){
		Player player = event.getPlayer();
		int arrow = player.getInventory().first(Material.ARROW);
		int tippedarrow = player.getInventory().first(Material.TIPPED_ARROW);
		ItemStack item;
		if( ((tippedarrow == -1) && (arrow != -1)) || (arrow != -1) && (arrow < tippedarrow)){
			if(arrows.containsKey(player.getUniqueId())){
				arrows.remove(arrows.remove(player.getUniqueId()));
			}
			if(shot.containsKey(player.getUniqueId())){
				shot.remove(arrows.remove(player.getUniqueId()));
			}
			return;
		}
		
		if((((arrow == -1) && (tippedarrow != -1) ) || (tippedarrow != -1) && (tippedarrow < arrow))){
			item = player.getInventory().getItem(tippedarrow);
			
			if(arrows.containsKey(player.getUniqueId())){
				arrows.remove(player.getUniqueId());
			}
			if(shot.containsKey(player.getUniqueId())){
				shot.remove(player.getUniqueId());
			}
			
			try{
				CustomArrow customarrow = new CustomArrow(item);
				arrows.put(player.getUniqueId(), customarrow.effect);
				return;
			}catch(IllegalArgumentException e){
				return;
			}
			
		}
		return;
		
	}
	
	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			Effect item;
			if(arrows.containsKey(player.getUniqueId())){
				item = arrows.get(player.getUniqueId());
				shot.put(player.getUniqueId(), item);
				arrows.remove(player.getUniqueId());
				return;
			}
			return;
		}
	}

	@EventHandler
	public void onArrowLand(ProjectileHitEvent event){
		if(event.getEntity() instanceof Arrow){
			if(event.getEntity().getShooter() instanceof Player){
				Player player = (Player) event.getEntity().getShooter();
				if(shot.containsKey(player.getUniqueId())){
					Effect item = shot.get(player.getUniqueId());
					
					if(item != null){
						if(event.getHitBlock() != null){
							
							switch(item.getType()){
							case EXPLOSION:
								event.getHitBlock().getWorld().createExplosion(event.getHitBlock().getLocation(), item.getAmplifier());
								break;
							case LIGHTNING:
								event.getHitBlock().getWorld().strikeLightning(event.getHitBlock().getLocation());
								break;
							default:
								break;
							}
							shot.remove(player.getUniqueId());
							return;
						}
						
						if(event.getHitEntity() != null){
							if(event.getHitEntity() instanceof LivingEntity){
								LivingEntity living = (LivingEntity)event.getHitEntity();
								switch(item.getType()){
								case HARM:
									if(event.getHitEntity() instanceof Player){
										try{
											new Account((Player) event.getHitEntity()).getLoadedCharacter().setHealth(new Account((Player) event.getHitEntity()).getLoadedCharacter().getHealth() - (4*item.getAmplifier()));
										}catch(Exception e) { return; }
									}else{
										try{
											living.setHealth(living.getHealth() - (4*item.getAmplifier()));
										}catch(Exception e) { return; }
									}
									break;
								case HEAL:
									if(event.getHitEntity() instanceof Player){
										try{
											new Account((Player) event.getHitEntity()).getLoadedCharacter().setHealth(new Account((Player) event.getHitEntity()).getLoadedCharacter().getHealth() + (4*item.getAmplifier()));
										}catch(Exception e) { break; }
									}else{
										try{
											living.setHealth(living.getHealth() + (4*item.getAmplifier()));
										}catch(Exception e) { break; }
									}
									break;
								case MANA:
									if(event.getHitEntity() instanceof Player){
										try{
											new Account((Player) event.getHitEntity()).getLoadedCharacter().setMana(new Account((Player) event.getHitEntity()).getLoadedCharacter().getMana() + (5*item.getAmplifier()));
										}catch(Exception e) { break; }
									}
									break;
								case BLIND:
									living.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, item.getDuration(), item.getAmplifier()));
									break;
								case EXPLOSION:
									living.getWorld().createExplosion(living.getLocation(), item.getAmplifier());
									break;
								case FIRE:
									living.setFireTicks(item.getDuration());
									break;
								case FIRE_RESIST:
									living.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, item.getDuration(), item.getAmplifier()));
									break;
								case HUNGER:
									living.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, item.getDuration(), item.getAmplifier()));
									break;
								case JUMP:
									living.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, item.getDuration(), item.getAmplifier()));
									break;
								case LIGHTNING:
									living.getWorld().strikeLightning(living.getLocation());
									break;
								case NAUSEA:
									living.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, item.getDuration(), item.getAmplifier()));
									break;
								case NONE:
									break;
								case POISON:
									living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, item.getDuration(), item.getAmplifier()));
									break;
								case REGEN:
									living.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, item.getDuration(), item.getAmplifier()));
									break;
								case RESISTANCE:
									living.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, item.getDuration(), item.getAmplifier()));
									break;
								case SLOW:
									living.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, item.getDuration(), item.getAmplifier()));
									break;
								case SPEED:
									living.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, item.getDuration(), item.getAmplifier()));
									break;
								case WEAKNESS:
									living.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, item.getDuration(), item.getAmplifier()));
									break;
								case WITHER:
									living.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, item.getDuration(), item.getAmplifier()));
									break;
								default:
									break;
								}
								shot.remove(player.getUniqueId());
								return;
							}else{
								return;
							}
						}
					}
				}
				
				
			}
			
		}
		
	}
	
}
