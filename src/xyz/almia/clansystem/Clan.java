package xyz.almia.clansystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import xyz.almia.accountsystem.PlayerSetup;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.configclasses.ConfigManager;

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
	
	public List<xyz.almia.accountsystem.Character> getClansmen(){
		List<xyz.almia.accountsystem.Character> characters = new ArrayList<xyz.almia.accountsystem.Character>();
		for(String name : config.getStringList("clansmen")){
			characters.add(new PlayerSetup().getCharacterFromUsername(name));
		}
		return characters;
	}
	
	public void addClansmen(xyz.almia.accountsystem.Character chara){
		List<String> clansmen = config.getStringList("clansmen");
		clansmen.add(chara.getUsername());
		config.set("clansmen", clansmen);
		ConfigManager.save(clan.toString()+".yml", "clans");
	}
	
	public void removeClansmen(xyz.almia.accountsystem.Character chara){
		List<String> clansmen = config.getStringList("clansmen");
		clansmen.remove(chara.getUsername());
		config.set("clansmen", clansmen);
		ConfigManager.save(clan.toString()+".yml", "clans");
	}
	
	public xyz.almia.accountsystem.Character getKing(){
		if(clan.equals(Clans.EXILED))
			return null;
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
	
	public void setKing(xyz.almia.accountsystem.Character chara){
		if(clan.equals(Clans.EXILED))
			return;
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
		if(config.getString("king").equalsIgnoreCase("unknown")){
			return false;
		}else{
			return true;
		}
	}
	
	public List<xyz.almia.accountsystem.Character> getRejected(){
		if(clan.equals(Clans.EXILED))
			return null;
		if(clan.equals(Clans.UNCLANNED))
			return null;
		
		List<xyz.almia.accountsystem.Character> characters = new ArrayList<xyz.almia.accountsystem.Character>();
		for(String name : config.getStringList("rejected")){
			characters.add(new PlayerSetup().getCharacterFromUsername(name));
		}
		return characters;
	}
	
	public void addRejected(xyz.almia.accountsystem.Character chara){
		List<String> rejected = config.getStringList("rejected");
		rejected.add(chara.getUsername());
		config.set("rejected", rejected);
		ConfigManager.save(clan.toString()+".yml", "clans");
	}
	
	public void removeRejected(xyz.almia.accountsystem.Character chara){
		List<String> rejected = config.getStringList("rejected");
		rejected.remove(chara.getUsername());
		config.set("rejected", rejected);
		ConfigManager.save(clan.toString()+".yml", "clans");
	}
	
	public xyz.almia.accountsystem.Character getProposed(){
		if(clan.equals(Clans.EXILED))
			return null;
		if(clan.equals(Clans.UNCLANNED))
			return null;
		try{
			return new PlayerSetup().getCharacterFromUsername(config.getString("proposed"));
		}catch(Exception e){
			return null;
		}
	}
	
	public void setProposed(xyz.almia.accountsystem.Character chara){
		if(clan.equals(Clans.EXILED))
			return;
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
		if(config.getString("proposed").equalsIgnoreCase("unknown")){
			return false;
		}else{
			return true;
		}
	}
	
}
