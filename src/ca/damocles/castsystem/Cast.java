package ca.damocles.castsystem;

import org.bukkit.entity.Player;
import ca.damocles.accountsystem.Character;
import ca.damocles.itemblueprints.SpellTome;
import ca.damocles.spells.ArcaneMissileSpell;
import ca.damocles.spells.MeteorShowerSpell;
import ca.damocles.spells.PrayerSpell;
import ca.damocles.spells.TornadoSpell;
import ca.damocles.spells.TransfusionSpell;
import ca.damocles.spells.WingsSpell;
import ca.damocles.spellsystem.Spell.Spells;
import ca.damocles.spellsystem.SpellBook;
import ca.damocles.storagesystem.Equips.Slot;

public class Cast {
	
	public Cast(int i, Character character){
		Player player = character.getPlayer();
			if(i != -1){
				if(character.getEquip(Slot.SPELLBOOK) != null){
					SpellBook book = new SpellBook(character.getEquip(Slot.SPELLBOOK));
					SpellTome tome = book.getSpells().get(i);
					Spells spell = tome.getSpell();
					
					if(spell.equals(Spells.WINGS)){
						WingsSpell wingspell = new WingsSpell(0);
						wingspell.setLocation(player.getLocation());
						wingspell.cast(player);
					}
					if(spell.equals(Spells.TORNADO)){
						TornadoSpell tornadospell = new TornadoSpell(1);
						tornadospell.setLocation(player.getLocation());
						tornadospell.cast(player);
					}
					
					if(spell.equals(Spells.ARCANE_MISSILE)){
						ArcaneMissileSpell missile = new ArcaneMissileSpell(2);
						missile.cast(player);
					}
					
					if(spell.equals(Spells.METEOR_SHOWER)){
						MeteorShowerSpell meteor = new MeteorShowerSpell(3);
						meteor.cast(player);
					}
					
					if(spell.equals(Spells.PRAYER)){
						PrayerSpell prayer = new PrayerSpell(4);
						prayer.cast(player);
					}
					
					if(spell.equals(Spells.TRANSFUSION)){
						TransfusionSpell transfusion = new TransfusionSpell(5);
						transfusion.cast(player);
					}
					
				}
				
			}
		
		
	}
	
}
