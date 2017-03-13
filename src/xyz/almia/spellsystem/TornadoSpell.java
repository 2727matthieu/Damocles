package xyz.almia.spellsystem;

import xyz.almia.accountsystem.Character;
import xyz.almia.damagesystem.DamageType;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.almia.accountsystem.Account;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.damagesystem.Damage;
import xyz.almia.effectLibrary.TornadoEffect;
import xyz.almia.spellsystem.Target.TargetOptions;

public class TornadoSpell extends Spell{
	
	Location location;

	public TornadoSpell(int id) {
		super(id);
	}
	
	public int getDamage() {
		return 10;
	}
	
	public int overSeconds(){
		return 4;
	}
	
	public SpellType getType() {
		return SpellType.TARGETED_AOE;
	}

	public int getCost() {
		return 20;
	}

	public int getId() {
		return 1;
	}

	public int getCooldown() {
		return 7;
	}

	public TargetOptions getTargetOption() {
		return TargetOptions.TARGET_AOE;
	}

	public void playEffect(Object target) {
		new TornadoEffect(overSeconds(), (Location)target);
	}

	public void cast(Player source) {
		Account account = new Account(source);
		Character character = account.getLoadedCharacter();
		character.setMana(character.getMana() - getCost());
		new BukkitRunnable(){
			List<Entity> affected = new ArrayList<Entity>();
			int count = 0;
			int time = overSeconds();
			
			public void run(){
				
				Entity stand = getLocation().getWorld().spawnEntity(getLocation(), EntityType.ARMOR_STAND);
				affected = stand.getNearbyEntities(getRange()*2, getRange()*2, getRange()*2);
				stand.remove();
				
				affected = new Target(getTargetOption(), getRange(), location).getAOEFromLocation();
				
				for(Entity entity : affected){
					if(entity instanceof Creature){
						new Damage().playerDamageCreature((Creature)entity, source, source.getInventory().getItemInMainHand(), (getDamage()/overSeconds()), DamageType.MAGICAL);
					}
					if(entity instanceof Player){
						new Damage().playerDamagePlayer((Player)entity, source, source.getInventory().getItemInMainHand(), (getDamage()/overSeconds()), DamageType.MAGICAL);
					}
				}
				
				count++;
				
				if(count == time)
					this.cancel();
				
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, 20);
	}

	public int getRange() {
		return 17;
	}

	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location loc){
		this.location = loc;
	}

}
