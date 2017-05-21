package ca.damocles.spells;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ca.damocles.accountsystem.Character;
import ca.damocles.accountsystem.Account;
import ca.damocles.spellsystem.Spell;
import ca.damocles.utils.ParticleUtil;

public class PrayerSpell extends Spell{

	public PrayerSpell(int id) {
		super(id);
	}

	public int getDamage() {
		return 2;
	}

	public int overSeconds() {
		return 0;
	}

	public SpellType getType(){
		return SpellType.ACTIVE;
	}

	public Spells getSpell() {
		return Spells.PRAYER;
	}

	public int getCost() {
		return 5;
	}

	public int getId() {
		return 4;
	}

	public int getCooldown() {
		return 0;
	}

	public int getRange() {
		return 0;
	}

	public Location getLocation() {
		return null;
	}

	public void setLocation(Location loc) {
		return;
	}

	public void cast(Player source) {
		Account account = new Account(source);
		Character character = account.getLoadedCharacter();
		if(character.getMana() < getCost()){
			return;
		}
		character.setMana(character.getMana() - getCost());
		character.setHealth(character.getHealth()+getDamage());
		new ParticleUtil().playPrayerEffect(source);
		
	}

}
