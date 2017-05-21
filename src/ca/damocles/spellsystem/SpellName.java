package ca.damocles.spellsystem;

import org.apache.commons.lang3.StringUtils;

import ca.damocles.spells.ArcaneMissileSpell;
import ca.damocles.spells.MeteorShowerSpell;
import ca.damocles.spells.PrayerSpell;
import ca.damocles.spells.TornadoSpell;
import ca.damocles.spells.TransfusionSpell;
import ca.damocles.spells.WingsSpell;
import ca.damocles.spellsystem.Spell.SpellType;
import ca.damocles.spellsystem.Spell.Spells;

public class SpellName {

	public SpellName() { }
	
	public String getSpellType(Spells spell){
		SpellType type;
		
		if(spell.equals(Spells.TORNADO)){
			type = new TornadoSpell(1).getType();
		}else if(spell.equals(Spells.WINGS)){
			type =  new WingsSpell(0).getType();
		}else if(spell.equals(Spells.ARCANE_MISSILE)){
			type =  new ArcaneMissileSpell(2).getType();
		}else if(spell.equals(Spells.METEOR_SHOWER)){
			type =  new MeteorShowerSpell(3).getType();
		}else if(spell.equals(Spells.PRAYER)){
			type =  new PrayerSpell(4).getType();
		}else if(spell.equals(Spells.TRANSFUSION)){
			type =  new TransfusionSpell(5).getType();
		}else{
			type = SpellType.NULL;
		}
		
		String s = type.toString().toLowerCase();
		s = s.replace("_", " ");
		s = StringUtils.capitalize(s);
		return s;
	}
	
}
