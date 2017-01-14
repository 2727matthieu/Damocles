package xyz.almia.potionsystem;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.almia.accountsystem.Account;

public class PotionHandler implements Listener{
	
	public PotionHandler(){}
	
	public String getName(PotionType type){
		switch(type){
		case HEAL:
			return "Healing";
		case MANA:
			return "Mana Regeneration";
		case NONE:
			return "Nothing";
		case BLIND:
			return "Blindness";
		case EXPLOSION:
			return "Explosion";
		case FIRE:
			return "Fire";
		case FIRE_RESIST:
			return "Fire Resistance";
		case HARM:
			return "Harming";
		case HUNGER:
			return "Hunger";
		case JUMP:
			return "Jumping";
		case LIGHTNING:
			return "Lightning";
		case NAUSEA:
			return "Sickness";
		case POISON:
			return "Poison";
		case REGEN:
			return "Regeneration";
		case RESISTANCE:
			return "Resistance";
		case SLOW:
			return "Slowness";
		case SPEED:
			return "Speed";
		case WEAKNESS:
			return "Weakness";
		case WITHER:
			return "Withering";
		}
		return "Nothing";
	}
	
	public String getLore(PotionType type){
		switch(type){
		case HEAL:
			return "Health";
		case MANA:
			return "Mana";
		case NONE:
			return "Nothing";
		case BLIND:
			return "Blindness";
		case EXPLOSION:
			return "Exploding";
		case FIRE:
			return "Fire";
		case FIRE_RESIST:
			return "Fire Resistance";
		case HARM:
			return "Harming";
		case HUNGER:
			return "Hunger";
		case JUMP:
			return "Jumping";
		case LIGHTNING:
			return "Smiting";
		case NAUSEA:
			return "Sickness";
		case POISON:
			return "Poison";
		case REGEN:
			return "Regenration";
		case RESISTANCE:
			return "Resistance";
		case SLOW:
			return "Slowness";
		case SPEED:
			return "Speed";
		case WEAKNESS:
			return "Weakness";
		case WITHER:
			return "Wither";
		}
		return "Nothing";
	}
	
	@EventHandler
	public void onPotionConsume(PlayerItemConsumeEvent event){
		ItemStack consumed = event.getItem();
		if(consumed.getType().equals(Material.POTION)){
			Potion potion = new Potion(consumed);
			Account player = new Account(event.getPlayer());
			xyz.almia.accountsystem.Character character = player.getLoadedCharacter();
			
			switch(potion.getType()){
			case HARM:
				character.setHealth(character.getHealth() - (4*potion.getAmplifier()));
				return;
			case HEAL:
				character.setHealth(character.getHealth() + (4*potion.getAmplifier()));
				return;
			case MANA:
				character.setMana(character.getMana() + (5*potion.getAmplifier()));
				return;
			case BLIND:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, potion.getDuration(), potion.getAmplifier()));
				return;
			case EXPLOSION:
				event.getPlayer().getWorld().createExplosion(event.getPlayer().getLocation(), potion.getAmplifier());
				return;
			case FIRE:
				event.getPlayer().setFireTicks(potion.getDuration());
				return;
			case FIRE_RESIST:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, potion.getDuration(), potion.getAmplifier()));
				return;
			case HUNGER:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, potion.getDuration(), potion.getAmplifier()));
				return;
			case JUMP:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, potion.getDuration(), potion.getAmplifier()));
				return;
			case LIGHTNING:
				event.getPlayer().getWorld().strikeLightning(event.getPlayer().getLocation());
				return;
			case NAUSEA:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, potion.getDuration(), potion.getAmplifier()));
				return;
			case NONE:
				return;
			case POISON:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, potion.getDuration(), potion.getAmplifier()));
				return;
			case REGEN:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, potion.getDuration(), potion.getAmplifier()));
				return;
			case RESISTANCE:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, potion.getDuration(), potion.getAmplifier()));
				return;
			case SLOW:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, potion.getDuration(), potion.getAmplifier()));
				return;
			case SPEED:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, potion.getDuration(), potion.getAmplifier()));
				return;
			case WEAKNESS:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, potion.getDuration(), potion.getAmplifier()));
				return;
			case WITHER:
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, potion.getDuration(), potion.getAmplifier()));
				return;
			default:
				return;
			}
			
		}
		
	}
	
}
