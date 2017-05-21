package ca.damocles.clansystem;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Character;
import ca.damocles.accountsystem.PlayerSetup;
import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.configclasses.ConfigManager;

public class Clan {
	
	private Clans clan;
	Plugin plugin = Cardinal.getPlugin();
	FileConfiguration config;
	
	public Clan(Clans clan){
		this.clan = clan;
		ConfigManager.load(clan.toString()+".yml", "clans");
		this.config = ConfigManager.get(clan.toString()+".yml");
		if(!(config.contains("clansmen"))){
			setup();
		}
	}
	
	public void setup(){
		config.set("king", "unknown");
		config.set("officer", "unknown");
		config.set("clansmen", new ArrayList<String>());
		config.set("rejected", new ArrayList<String>());
		config.set("proposed", "unknown");
		ConfigManager.save(clan.toString()+".yml", "clans");
	}
	
	public List<Character> getClansmen(){
		if(clan.equals(Clans.UNCLANNED))
			return new ArrayList<Character>();
		
		List<ca.damocles.accountsystem.Character> characters = new ArrayList<Character>();
		for(String name : config.getStringList("clansmen")){
			characters.add(new PlayerSetup().getCharacterFromUsername(name));
		}
		return characters;
	}
	
	public void addClansmen(Character chara){
		if(clan.equals(Clans.UNCLANNED))
			return;
		
		List<String> clansmen = config.getStringList("clansmen");
		clansmen.add(chara.getUsername());
		config.set("clansmen", clansmen);
		ConfigManager.save(clan.toString()+".yml", "clans");
		return;
	}
	
	public void removeClansmen(Character chara){
		if(clan.equals(Clans.UNCLANNED))
			return;
		List<String> clansmen = config.getStringList("clansmen");
		clansmen.remove(chara.getUsername());
		config.set("clansmen", clansmen);
		ConfigManager.save(clan.toString()+".yml", "clans");
		return;
	}
	
	public Character getKing(){
		if(clan.equals(Clans.UNCLANNED))
			return null;
		try{
			return new PlayerSetup().getCharacterFromUsername(config.getString("king"));
		}catch(Exception e){
			return null;
		}
	}
	
	public String getKingName(){
		return config.getString("king");
	}
	
	public void setKing(Character chara){
		if(clan.equals(Clans.UNCLANNED))
			return;
		
		if(chara == null){
			config.set("king", "unknown");
			ConfigManager.save(clan.toString()+".yml", "clans");
			return;
		}
		
		config.set("king", chara.getUsername());
		ConfigManager.save(clan.toString()+".yml", "clans");
		return;
	}
	
	public boolean isThereAKing(){
		if(clan.equals(Clans.UNCLANNED))
			return false;
		
		if(config.getString("king").equalsIgnoreCase("unknown")){
			return false;
		}else{
			return true;
		}
	}
	
	public List<Character> getRejected(){
		if(clan.equals(Clans.UNCLANNED))
			return new ArrayList<Character>();
		
		List<ca.damocles.accountsystem.Character> characters = new ArrayList<Character>();
		for(String name : config.getStringList("rejected")){
			characters.add(new PlayerSetup().getCharacterFromUsername(name));
		}
		return characters;
	}
	
	public void addRejected(Character chara){
		if(clan.equals(Clans.UNCLANNED))
			return;
		List<String> rejected = config.getStringList("rejected");
		rejected.add(chara.getUsername());
		config.set("rejected", rejected);
		ConfigManager.save(clan.toString()+".yml", "clans");
		return;
	}
	
	public void removeRejected(Character chara){
		if(clan.equals(Clans.UNCLANNED))
			return;
		List<String> rejected = config.getStringList("rejected");
		rejected.remove(chara.getUsername());
		config.set("rejected", rejected);
		ConfigManager.save(clan.toString()+".yml", "clans");
		return;
	}
	
	public Character getProposed(){
		if(clan.equals(Clans.UNCLANNED))
			return null;
		try{
			return new PlayerSetup().getCharacterFromUsername(config.getString("proposed"));
		}catch(Exception e){
			return null;
		}
	}
	
	public void setProposed(Character chara){
		if(clan.equals(Clans.UNCLANNED))
			return;
		
		if(chara == null){
			config.set("proposed", "unknown");
			ConfigManager.save(clan.toString()+".yml", "clans");
			return;
		}
		config.set("proposed", chara.getUsername());
		ConfigManager.save(clan.toString()+".yml", "clans");
		return;
	}
	
	public boolean isSomeoneProposed(){
		if(clan.equals(Clans.UNCLANNED))
			return false;
		if(config.getString("proposed").equalsIgnoreCase("unknown")){
			return false;
		}else{
			return true;
		}
	}
	
}