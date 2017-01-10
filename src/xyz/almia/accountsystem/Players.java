package xyz.almia.accountsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import xyz.almia.configclasses.ConfigManager;

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
		List<String> players = config.getStringList("players");
		for(String s : players){
			characters.add(setup.deserializeCharacter(s));
		}
		return characters;
	}
	
	
	
}
