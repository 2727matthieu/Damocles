package ca.damocles.accountsystem;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.configclasses.ConfigManager;

public class Account {
	
	Plugin plugin = Cardinal.getPlugin();
	public Player player;
	FileConfiguration config;
	
	public Account(Player player){
		this.player = player;
		ConfigManager.load(player.getUniqueId()+";acc.yml", "players/"+player.getUniqueId());
		config = ConfigManager.get(player.getUniqueId()+";acc.yml");
	}
	
	public AccountStatus getStatus(){
		return AccountStatus.valueOf(config.getString("status"));
	}
	
	public void logout(){
		getLoadedCharacter().setSavedInventory(player.getInventory());
		getLoadedCharacter().setLastLocation(player.getLocation());
		getLoadedCharacter().setRegening(false);
		player.getInventory().clear();
		config.set("status", AccountStatus.LOGGINGIN.toString());
		config.set("lastLoaded", getLoadedCharacterID());
		config.set("loaded", -1);
		ConfigManager.save(player.getUniqueId()+";acc.yml", "players/"+player.getUniqueId());
	}
	
	public Character loadCharacter(int ID){
		if(ID == -1){
			return null;
		}else if(ID > (getCharacterLimit() - 1)){
			return null;
		}else{
			config.set("status", AccountStatus.LOGGEDIN.toString());
			config.set("loaded", ID);
			ConfigManager.save(player.getUniqueId()+";acc.yml", "players/"+player.getUniqueId());
			return new Character(player, ID);
		}
	}
	
	public Character getLoadedCharacter(){
		return loadCharacter(getLoadedCharacterID());
	}
	
	public int getLoadedCharacterID(){
		return config.getInt("loaded");
	}
	
	public Character getLastLoadedCharacter(){
		if(getLastLoadedCharacterID() == -1){
			return null;
		}
		return new Character(player, getLastLoadedCharacterID());
	}
	
	public int getLastLoadedCharacterID(){
		return config.getInt("lastLoaded");
	}
	
	public void unLoadCharacter(){
		config.set("lastLoaded", getLoadedCharacterID());
		config.set("loaded", -1);
		ConfigManager.save(player.getUniqueId()+";acc.yml", "players/"+player.getUniqueId());
	}
	
	public void firstTimeSetup(){
		config.set("status", AccountStatus.LOGGINGIN.toString());
		config.set("accountlimit", 3);
		config.set("loaded", -1);
		config.set("lastLoaded", -1);
		ConfigManager.save(player.getUniqueId()+";acc.yml", "players/"+player.getUniqueId());
		new Character(player, 0).create();
		new Character(player, 1).create();
		new Character(player, 2).create();
	}
	
	public int getCharacterLimit(){
		return config.getInt("accountlimit");
	}
	
	public void setCharacterLimit(int value){
		config.set("accountlimit", value);
		ConfigManager.save(player.getUniqueId()+";acc.yml", "players/"+player.getUniqueId());
	}
	
	public Character getCharacterFromID(int ID){
		return new Character(player, ID);
	}
	
}
