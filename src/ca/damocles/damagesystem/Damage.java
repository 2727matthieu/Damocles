package ca.damocles.damagesystem;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.AccountStatus;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.damagesystem.DamageInstance.DamageType;

public class Damage implements Listener{
	
	public static List<String> petrified = new ArrayList<String>();
	Plugin plugin = Cardinal.getPlugin();
	
	@EventHandler
	public void onDamaged(EntityDamageEvent event){
		double rawDamage = event.getDamage();
		event.setDamage(0.0);
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity damaged = (LivingEntity) event.getEntity();
			switch(event.getCause()) {
			case BLOCK_EXPLOSION:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case CONTACT:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case CRAMMING:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case CUSTOM:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case DRAGON_BREATH:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case DROWNING:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case ENTITY_EXPLOSION:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case FALL:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case FALLING_BLOCK:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case FIRE:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case FIRE_TICK:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case FLY_INTO_WALL:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case HOT_FLOOR:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case LAVA:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case LIGHTNING:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case MAGIC:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case MELTING:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case POISON:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case SUFFOCATION:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case SUICIDE:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case THORNS:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case VOID:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			case WITHER:
				new DamageInstance(damaged, null, rawDamage, DamageType.ENVIRONMENTAL);
				return;
			default:
				break;
			}
			return;
		}
		return;
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		event.setDamage(0.0);
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity Damaged = (LivingEntity) event.getEntity();
			if(event.getDamager() instanceof LivingEntity) {
				LivingEntity Damager = (LivingEntity)event.getDamager();
				new DamageInstance(Damaged, Damager, event.getDamage(), DamageType.PHYSICAL);
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
