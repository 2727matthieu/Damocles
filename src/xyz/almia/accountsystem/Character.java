package xyz.almia.accountsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.configclasses.ConfigManager;
import xyz.almia.messagesystem.Messages;
import xyz.almia.utils.LocationSerializer;

public class Character {
	
	Plugin plugin = Cardinal.getPlugin();
	private Player player;
	int characterID = 0;
	FileConfiguration config;
	Players players = new Players();
	
	public Character(Player player, int characterID){
		this.player = player;
		this.characterID = characterID;
		ConfigManager.load(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
		this.config = ConfigManager.get(player.getUniqueId()+";char;"+characterID+".yml");
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public int getSouls(){
		return config.getInt("soul");
	}
	
	public void setSouls(int amount){
		config.set("soul", amount);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public double getBankBalance(){
		return config.getDouble("money");
	}
	
	public void setBankBalance(double i){
		config.set("money", i);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public void deposit(double amount){
		setBankBalance(getBankBalance() + amount);
	}
	
	public boolean withdraw(double amount){
		if((getBankBalance() - amount) < 0)
			return false;
		setBankBalance(getBankBalance() - amount);
		return true;
	}
	
	public Inventory getSavedInventory(){
		List<Map<?, ?>> inventories = config.getMapList("inventory");
		@SuppressWarnings("unchecked")
		HashMap<Integer, ItemStack> hashInventory = (HashMap<Integer, ItemStack>) inventories.get(0);
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
		for(Integer i : hashInventory.keySet()){
			ItemStack item = hashInventory.get(i);
			inv.setItem(i, item);
		}
		return inv;
	}
	
	public void applyInventory(){
		this.getPlayer().getInventory().clear();
		List<Map<?, ?>> list = config.getMapList("inventory");
		@SuppressWarnings("unchecked")
		Map<Integer, ItemStack> hashInventory = (Map<Integer, ItemStack>)list.get(0);
		for(Integer i : hashInventory.keySet()){
			ItemStack item = hashInventory.get(i);
			this.getPlayer().getInventory().setItem(i, item);
		}
	}
	
	public void setSavedInventory(Inventory inventory){
		//Inventory oldsavedInventory = getSavedInventory();
		List<Map<?, ?>> inventories = new ArrayList<Map<?, ?>>();
		HashMap<Integer, ItemStack> hashInventory = new HashMap<Integer, ItemStack>();
		for(int i = 0; i < inventory.getSize(); i++){
			ItemStack item = inventory.getItem(i);
			if(item != null)
				hashInventory.put(i, item);
			
		}
		inventories.add(hashInventory);
		config.set("inventory", inventories);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public Inventory getInventory(){
		return this.getPlayer().getInventory();
	}
	
	public Location getLastLocation(){
		return LocationSerializer.locationFromString(config.getString("location"));
	}
	
	public void setLastLocation(Location loc){
		config.set("location", LocationSerializer.locationToString(loc));
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public int getPLevel(Profession prof){
		return config.getInt("profession."+prof.toString().toLowerCase()+".level");
	}
	
	public void setPLevel(Profession prof, int level){
		config.set("profession."+prof.toString().toLowerCase()+".level", level);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public boolean getRegening(){
		return config.getBoolean("regening");
	}
	
	public void setRegening(boolean state){
		config.set("regening", state);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public void addPExp(Profession prof, int value){
		setPExp(prof, getPExp(prof)+value);
		if(getPExp(prof) >= getPLevel(prof) * 100){
			setPExp(prof, getPExp(prof) - getPLevel( prof) * 100);
			setPLevel( prof, getPLevel(prof) + 1);
			Messages.proflevelUp(prof, player);
		}
	}
	
	public int getPExp(Profession prof){
		return config.getInt("profession."+prof.toString().toLowerCase()+".exp");
	}
	
	public void setPExp(Profession prof, int value){
		config.set("profession."+prof.toString().toLowerCase()+".exp", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public CharacterStatus getCharacterStatus(){
		return CharacterStatus.valueOf(config.getString("status"));
	}
	
	public void setCharacterStatus(CharacterStatus cs){
		config.set("status", cs.toString());
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public int getID(){
		return this.characterID;
	}
	
	public boolean exists(){
		if(!(getUsername().equalsIgnoreCase("unknown"))){
			return true;
		}
		return false;
	}
	
	public String getUsername(){
		return config.getString("username");
	}
	
	public void setUsername(String username){
		config.set("username", username);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public double getHealth(){
		return config.getDouble("health");
	}
	
	public void setHealth(double value){
		if(value < 0){
			config.set("health", 0);
			ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
			return;
		}else if(value <= getMaxHealth()){
			config.set("health", value);
			ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
			return;
		}else{
			config.set("health", getMaxHealth());
			ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
			return;
		}
	}
	
	public double getMaxHealth(){
		return config.getDouble("maxhealth");
	}
	
	public void setMaxHealth(double value){
		config.set("maxhealth", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public int getMana(){
		return config.getInt("mana");
	}
	
	public void setMana(int value){
		config.set("mana", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public int getMaxMana(){
		return config.getInt("maxmana");
	}
	
	public void setMaxMana(int value){
		config.set("maxmana", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public void regenMana(){
		if(getMana() < getMaxMana()){
			setMana(getMana() + 1);
		}
	}
	
	public void displayMana(){
		player.setFoodLevel(getMana());
	}
	
	public double getSpeed(){
		return config.getDouble("speed");
	}
	
	public void setSpeed(double value){
		config.set("speed", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public double getDefaultSpeed(){
		return 0.2;
	}
	
	public void applySpeed(){
		player.setWalkSpeed((float)getSpeed());
	}
	
	public Rank getRank(){
		return Rank.valueOf(config.getString("rank"));
	}
	
	public void setRank(Rank rank){
		config.set("rank", rank.toString());
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public String getTitle(){
		return Rank.getTitle(getRank());
	}
	
	public int getPhysicalDamage(){
		return config.getInt("physicaldamage");
	}
	
	public void setPhysicalDamage(int value){
		config.set("physicaldamage", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public int getMagicalDamage(){
		return config.getInt("magicaldamage");
	}
	
	public void setMagicalDamage(int value){
		config.set("magicaldamage", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public int getStat(Stat stat){
		return config.getInt("stats."+stat.toString().toLowerCase());
	}
	
	public void setStat(Stat stat, int value){
		config.set("stats."+stat.toString().toLowerCase(), value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public void levelUp(){
		config.set("level", getLevel()+1);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
		setAbilityPoints(getAbilityPoints() + 4);
		Messages.levelUp(player);
		calcSkillSlots();
		if(getLevel() == 5)
			player.performCommand("clan choose");
	}
	
	public void setLevel(int level){
		config.set("level", level);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public int getLevel(){
		return config.getInt("level");
	}
	
	public int getExp(){
		return config.getInt("exp");
	}
	
	public void addExp(int value){
		config.set("exp", getExp()+value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
		if(getExp() >= (getLevel() * 1024)){
			setExp(getExp() - (getLevel() * 1024));
			levelUp();
		}
		return;
	}
	
	public void setExp(int value){
		config.set("exp", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public void setAbilityPoints(int value){
		config.set("ap", value);
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public int getAbilityPoints(){
		return config.getInt("ap");
	}
	
	public int getSkillSlots(){
		return config.getInt("skillslots");
	}
	
	public void calcSkillSlots(){
		if(getLevel() == 1){
			config.set("skillslots", 2);
			ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
			return;
		}else if(getLevel() == 6){
			config.set("skillslots", 3);
			ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
			return;
		}else if(getLevel() == 12){
			config.set("skillslots", 4);
			ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
			return;
		}else if(getLevel() == 20){
			config.set("skillslots", 5);
			ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
			return;
		}else if(getLevel() > 20){
			double i = getLevel() - 20;
			i = i/10;
			if(i >= 1.0){
				config.set("skillslots", 5 + ((int)i));
				ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
				return;
			}
			return;
		}else{
			return;
		}
	}
	
	public void kill(){
	}
			
	public void remove(){
		new Account(player).logout();
		config.set("username", "UNKNOWN");
		config.set("rank", "PLAYER");
		config.set("physicaldamage", 1);
		config.set("magicaldamage", 1);
		config.set("mana", 0);
		config.set("maxmana", 20);
		config.set("speed", .2);
		config.set("level", 1);
		config.set("exp", 0);
		config.set("skillslots", 2);
		config.set("ap", 4);
		config.set("health", 6);
		config.set("maxhealth", 6);
		config.set("money", 0.0);
		config.set("soul", 5);
		config.set("regening", false);
		config.set("profession.herbalism.level", 1);
		config.set("profession.herbalism.exp", 0);
		config.set("profession.cooking.level", 1);
		config.set("profession.cooking.exp", 0);
		config.set("profession.mining.level", 1);
		config.set("profession.mining.exp", 0);
		config.set("profession.fishing.level", 1);
		config.set("profession.fishing.exp", 0);
		config.set("profession.alchemy.level", 1);
		config.set("profession.alchemy.exp", 0);
		config.set("profession.forging.level", 1);
		config.set("profession.forging.exp", 0);
		config.set("inventory", null);
		config.set("stats.strength", 0);
		config.set("stats.hitpoints", 0);
		config.set("stats.agility", 0);
		config.set("stats.intelligence", 0);
		config.set("location", LocationSerializer.locationToString(player.getLocation()));
		config.set("status", CharacterStatus.CHOOSE_USERNAME.toString());
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
	}
	
	public void create(){
		config.set("username", "UNKNOWN");
		config.set("rank", "PLAYER");
		config.set("physicaldamage", 1);
		config.set("magicaldamage", 1);
		config.set("mana", 0);
		config.set("maxmana", 20);
		config.set("speed", .2);
		config.set("level", 1);
		config.set("exp", 0);
		config.set("skillslots", 2);
		config.set("ap", 4);
		config.set("health", 6);
		config.set("maxhealth", 6);
		config.set("money", 0.0);
		config.set("soul", 5);
		config.set("regening", false);
		config.set("profession.herbalism.level", 1);
		config.set("profession.herbalism.exp", 0);
		config.set("profession.cooking.level", 1);
		config.set("profession.cooking.exp", 0);
		config.set("profession.mining.level", 1);
		config.set("profession.mining.exp", 0);
		config.set("profession.fishing.level", 1);
		config.set("profession.fishing.exp", 0);
		config.set("profession.alchemy.level", 1);
		config.set("profession.alchemy.exp", 0);
		config.set("profession.forging.level", 1);
		config.set("profession.forging.exp", 0);
		config.set("inventory", null);
		config.set("stats.strength", 0);
		config.set("stats.hitpoints", 0);
		config.set("stats.agility", 0);
		config.set("stats.intelligence", 0);
		config.set("location", LocationSerializer.locationToString(player.getLocation()));
		config.set("status", CharacterStatus.CHOOSE_USERNAME.toString());
		ConfigManager.save(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
		players.addCharacter(player.getUniqueId()+";char;"+characterID);
	}
	
}