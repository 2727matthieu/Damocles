package xyz.almia.spellsystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.almia.spellsystem.Target.TargetOptions;

public abstract class Spell {
	
	int id;
	
	public abstract int getDamage();
	
	public abstract int overSeconds();
	
	public abstract SpellType getType();
	
	public abstract int getCost();
	
	public abstract int getId();
	
	public abstract int getCooldown();
	
	public abstract int getRange();
	
	public abstract TargetOptions getTargetOption();
	
	public abstract Location getLocation();
	
	public abstract void setLocation(Location loc);
	
	public abstract void playEffect(Object target);
	
	public abstract void cast(Player source);
	
	public Spell(int id){
		this.id = id;
	}
	
	public enum SpellType{
		AOE, TARGETED, TARGETED_AOE, DOT, PASSIVE;
	}
	
}
