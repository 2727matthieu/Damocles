package ca.damocles.spells;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Character;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.spellsystem.Spell;
import ca.damocles.utils.ParticleUtil;

public class WingsSpell extends Spell{
	
	Location location;

	public WingsSpell(int id) {
		super(id);
	}

	public int getDamage() {
		return 0;
	}

	public int overSeconds() {
		return 25;
	}

	public SpellType getType() {
		return SpellType.ACTIVE;
	}

	public Spells getSpell() {
		return Spells.WINGS;
	}

	public int getCost() {
		return 20;
	}

	public int getId() {
		return 0;
	}

	public int getCooldown() {
		return 60;
	}
	
	public int getRange() {
		return 0;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location loc) {
		this.location = loc;
	}

	public void cast(Player source) {
		Account account = new Account(source);
		Character character = account.getLoadedCharacter();
		if(character.getMana() < getCost()){
			return;
		}
		character.setMana(character.getMana() - getCost());
		new ParticleUtil().playWingEffect(source, overSeconds());
		new BukkitRunnable(){
			int count = 0;
			int time = overSeconds();
			public void run(){
				source.setAllowFlight(true);
				source.setFlying(true);	
				count++;
				if(count == time){
					source.setAllowFlight(false);
					source.setFlying(false);
					this.cancel();
				}
			}
		}.runTaskTimer(Cardinal.getPlugin(), 0, 20);
		
	}

}
