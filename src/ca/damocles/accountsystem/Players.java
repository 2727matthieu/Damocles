package ca.damocles.accountsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import ca.damocles.configclasses.ConfigManager;

public class Players {
	
	FileConfiguration config;
	PlayerSetup setup = new PlayerSetup();
	
	public Players() {
		ConfigManager.load("characters.yml", "");
		this.config = ConfigManager.get("characters.yml");
		if(!(config.contains("players"))){
			setup();
		}
	}
	
	public void setup(){
		config.set("players", new ArrayList<String>());
		ConfigManager.save("characters.yml", "");
	}
	
	public void addCharacter(String s){
		List<String> path = config.getStringList("players");
		if(!(path.contains(s))){
			path.add(s);
		}
		config.set("players", path);
		ConfigManager.save("characters.yml", "");
	}
	
	
	public List<Character> getCharacters(){
		
		List<Character> characters = new ArrayList<Character>();
		
		for(String s : config.getStringList("players")){
			String[] strings = s.split(";");
			String uuid = strings[0];
			int id = Integer.valueOf(strings[2]);
			characters.add(new Character(UUID.fromString(uuid), id));
		}
		
		return characters;
	}
	
	
	
}
