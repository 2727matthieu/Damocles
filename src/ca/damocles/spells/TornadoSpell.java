package ca.damocles.spells;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Character;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.damagesystem.Damage;
import ca.damocles.damagesystem.DamageType;
import ca.damocles.spellsystem.Spell;
import ca.damocles.utils.Extra;
import ca.damocles.utils.ParticleUtil;

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
	
	public Spells getSpell(){
		return Spells.TORNADO;
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

	public void cast(Player source) {
		Account account = new Account(source);
		Character character = account.getLoadedCharacter();
		if(character.getMana() < getCost()){
			return;
		}
		character.setMana(character.getMana() - getCost());
		new ParticleUtil().playTornadoEffect(overSeconds(), character.getTargetBlock(100).getLocation());
		new BukkitRunnable(){
			List<Entity> affected = new ArrayList<Entity>();
			int count = 0;
			int time = overSeconds();
			
			public void run(){
				
				Entity stand = getLocation().getWorld().spawnEntity(getLocation(), EntityType.ARMOR_STAND);
				affected = stand.getNearbyEntities(getRange()*2, getRange()*2, getRange()*2);
				stand.remove();
				
				affected = Extra.getNearbyEntitiesFromLocation(location, getRange());
				
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
