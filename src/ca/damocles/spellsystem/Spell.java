package ca.damocles.spellsystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Spell {
	
	int id;
	
	public abstract int getDamage();
	
	public abstract int overSeconds();
	
	public abstract SpellType getType();
	
	public abstract Spells getSpell();
	
	public abstract int getCost();
	
	public abstract int getId();
	
	public abstract int getCooldown();
	
	public abstract int getRange();
	
	public abstract Location getLocation();
	
	public abstract void setLocation(Location loc);
	
	public abstract void cast(Player source);
	
	public Spell(int id){
		this.id = id;
	}
	
	public enum SpellType{
		AOE, TARGETED, TARGETED_AOE,
		DOT, PASSIVE, ACTIVE, NULL;
	}
	
	public enum Spells {
		TORNADO, WINGS, ARCANE_MISSILE,
		METEOR_SHOWER, PRAYER, TRANSFUSION;
	}
	
}
