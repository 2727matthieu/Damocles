package ca.damocles.castsystem;

import java.util.ArrayList;
import java.util.List;
import ca.damocles.accountsystem.Character;

public class CastHandler{
	
	Character character;
	
	public CastHandler(Character character){
		this.character = character;
	}
	
	public List<Combination> getCombination(){
		if(!CastingManager.castingCombs.containsKey(character.getUsername()))
			return new ArrayList<Combination>();
		return CastingManager.castingCombs.get(character.getUsername());
	}
	
	public void setCombination(List<Combination> list){
		if(CastingManager.castingCombs.containsKey(character.getUsername()))
			CastingManager.castingCombs.remove(character.getUsername());
		CastingManager.castingCombs.put(character.getUsername(), list);
	}
	
	public enum Combination {
		RIGHT, LEFT;
	}
	
}
