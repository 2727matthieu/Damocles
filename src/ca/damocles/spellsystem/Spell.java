package ca.damocles.spellsystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Spell {
	
	private int id;
	private Location loc;
	
	public abstract TriggerType getTriggerType();
	
	public abstract CastType getCastType();
	
	public abstract Spells getSpell();
	
	public abstract int getDamage();
	
	public abstract int getCost();
	
	public abstract long getCooldown();
	
	public abstract int getRange();
	
	public abstract void cast(Player source);
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}
	
	public Spell(int id){
		this.setId(id);
	}


	public enum CastType{
		AOE("Affects all entities in a specific range around you."),
		TARGETED("Targets the specific entity in front of you."),
		PASSIVE("This is always enabled."),
		TARGETED_AOE("Targets all entities in a specific range around a targeted area."),
		ACTIVE("Effects are ativated on cast.");
		
		private final String description;
		CastType(String description){ this.description = description; }
		public String getDesc(){ return this.description; }
	}
	
	public enum TriggerType{
		DOT("Affects are applied over a fixed period of time."),
		ACTIVE("Affects are dealt immediatly after cast."),
		DELAYED_ACTIVE("Affects are dealt after a fixed period of time."),
		OTHER("Affects are applied all the time.");

		private final String description;
		TriggerType(String description){ this.description = description; }
		public String getDesc(){ return this.description; }
	}
	
	public enum Spells {
		WINGS("Wings", 0, CastType.PASSIVE, TriggerType.OTHER),
		TORNADO("Tornado", 1, CastType.TARGETED_AOE, TriggerType.DOT),
		ARCANE_MISSILE("Arcane Missiles", 2, CastType.TARGETED, TriggerType.ACTIVE),
		METEOR_SHOWER("Meteor Shower", 3, CastType.AOE, TriggerType.OTHER),
		PRAYER("Prayer", 4, CastType.ACTIVE, TriggerType.ACTIVE),
		TRANSFUSION("Transfusion", 5, CastType.TARGETED, TriggerType.DOT);
		
		private final String name;
		private final int ID;
		private final CastType castType;
		private final TriggerType triggerType;																																																															
		Spells(String name, int id, CastType castType, TriggerType triggerType){
			this.name = name;
			this.ID = id;
			this.castType  = castType;
			this.triggerType = triggerType;
		}
		public String getSpellName(){ return name; }
		public int getID(){ return ID; }
		public CastType getCastType(){ return castType; }
		public TriggerType getTriggerType(){ return triggerType; }
	}
	
}
