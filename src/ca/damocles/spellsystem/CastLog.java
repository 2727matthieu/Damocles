package ca.damocles.spellsystem;

import ca.damocles.accountsystem.Character;

public class CastLog {
	
	private final Character CHAR;
	private final long TIME;
	private final Spell SPELL;
	
	public CastLog(Character character, Spell spell, long time){
		this.CHAR = character;
		this.TIME = time;
		this.SPELL = spell;
	}

	public Character getCHAR() {
		return CHAR;
	}

	public long getTIME() {
		return TIME;
	}

	public Spell getSPELL() {
		return SPELL;
	}

}
