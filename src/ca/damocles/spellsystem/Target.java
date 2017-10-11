package ca.damocles.spellsystem;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import ca.damocles.accountsystem.Character;

public class Target {
	
	private Character character;
	private Spell spell;

	public Target(Character character, Spell spell){
		this.character = character;
		this.spell = spell;
	}
	
	public List<Entity> getTarget(){
		
		List<Entity> entities = new ArrayList<Entity>();
		
		switch(spell.getCastType()){
		case ACTIVE:
			entities.add(this.character.getPlayer());
			return entities;
		case AOE:
			entities = character.getPlayer().getNearbyEntities(spell.getRange()*2, spell.getRange()*2, spell.getRange()*2);
			return entities;
		case PASSIVE:
			entities.add(this.character.getPlayer());
			return entities;
		case TARGETED:
			entities.add(character.getTargetEntity(spell.getRange()));
			return entities;
		case TARGETED_AOE:
			Block block = character.getTargetBlock(spell.getRange());
			Entity stand = block.getLocation().getWorld().spawnEntity(block.getLocation(), EntityType.ARMOR_STAND);
			entities = stand.getNearbyEntities(spell.getRange()*2, spell.getRange()*2, spell.getRange()*2);
			stand.remove();
			return entities;
		default:
			return entities;
		}
	}
	
	
}
