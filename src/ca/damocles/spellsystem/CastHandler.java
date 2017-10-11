package ca.damocles.spellsystem;

import java.util.HashMap;
import ca.damocles.accountsystem.Character;

public class CastHandler {
	
	private static CastHandler instance = new CastHandler();
	public static CastHandler getInstance(){ return instance; }
	
	public CastHandler(){}
	
	private HashMap<Character, CastLog> list = new HashMap<>();
	private boolean OFF_CD;
	
	public void add(CastLog log){ list.put(log.getCHAR(), log); }
	
	public void remove(CastLog log) { list.remove(log.getCHAR()); }
	
	public boolean check(Character character, Spell spell){
		OFF_CD = true;
		list.entrySet().forEach(entry -> {
			long currentTime = System.currentTimeMillis();
			if(entry.getKey().getUsername().equals(character.getUsername())){
				if(entry.getValue().getSPELL().getId() == entry.getValue().getSPELL().getId()){
					if(currentTime >= entry.getValue().getTIME()){
						remove(entry.getValue());
					}else{
						OFF_CD = false;
					}
				}
			}
		});
		return OFF_CD;
	}
	
}
