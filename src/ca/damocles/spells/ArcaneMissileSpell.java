package ca.damocles.spells;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.Character;
import ca.damocles.spellsystem.Spell;
import ca.damocles.utils.ParticleUtil;

public class ArcaneMissileSpell extends Spell{
	
	Location location;

	public ArcaneMissileSpell(int id) {
		super(id);
	}

	public int getDamage() {
		return 3;
	}

	public int overSeconds() {
		return 0;
	}

	public Spells getSpell() {
		return Spells.ARCANE_MISSILE;
	}

	public int getCost() {
		return 2;
	}

	public int getId() {
		return 2;
	}

	public int getCooldown() {
		return 0;
	}

	public int getRange() {
		return 50;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location loc) {
		this.location = loc;
		return;
	}
	
	public void cast(Player source) {
		Account account = new Account(source);
		Character character = account.getLoadedCharacter();
		if(character.getMana() < getCost()){
			return;
		}
		character.setMana(character.getMana() - getCost());
		new ParticleUtil().playArcaneMissileEffect(source, (double)getDamage());
	}

	@Override
	public TriggerType getTriggerType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CastType getCastType() {
		// TODO Auto-generated method stub
		return null;
	}

}
