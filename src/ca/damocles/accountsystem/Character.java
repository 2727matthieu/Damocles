package ca.damocles.accountsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;

import ca.damocles.cardinalsystem.Cardinal;
import ca.damocles.clansystem.Clan;
import ca.damocles.clansystem.Clans;
import ca.damocles.configclasses.ConfigManager;
import ca.damocles.messagesystem.Messages;
import ca.damocles.storagesystem.Equips;
import ca.damocles.storagesystem.Treasury;
import ca.damocles.storagesystem.Equips.Slot;
import ca.damocles.utils.LocationSerializer;

public class Character {
	
	Plugin plugin = Cardinal.getPlugin();
	private Player player;
	public UUID uuid;
	public int characterID = 0;
	FileConfiguration config;
	Players players = new Players();
	PlayerSetup playersetup = new PlayerSetup();
	
	public Character(Player player, int characterID){
		this.player = player;
		this.uuid = player.getUniqueId();
		this.characterID = characterID;
		ConfigManager.load(player.getUniqueId()+";char;"+characterID+".yml", "players/"+player.getUniqueId());
		this.config = ConfigManager.get(player.getUniqueId()+";char;"+characterID+".yml");
	}
	
	public Character(UUID uuid, int characterID){
		this.uuid = uuid;
		this.player = Bukkit.getPlayer(uuid);
		this.characterID = characterID;
		ConfigManager.load(uuid+";char;"+characterID+".yml", "players/"+uuid);
		this.config = ConfigManager.get(uuid+";char;"+characterID+".yml");
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public Block getTargetBlock(int range){
		Set<Material> set = new HashSet<Material>();
		set.add(Material.AIR);
		set.add(Material.GLASS);
		return player.getTargetBlock(set, range);
	}
	
	public Player getTargetPlayer(int range) {
		ConfigManager.load("blacklist.yml", "");
		List<String> blacklist = ConfigManager.get("blacklist.yml").getStringList("list");
		List<Material> materials = new ArrayList<Material>();
		for(String s : blacklist){
			materials.add(Material.valueOf(s));
		}
		BlockIterator bItr = new BlockIterator(player, range);
		Block block;
		Location loc;
		int bx, by, bz;
		double ex, ey, ez;
		// loop through player's line of sight
		while (bItr.hasNext()) {
			block = bItr.next();
			if(materials.contains(block.getType()))
				bItr.next();
			bx = block.getX();
			by = block.getY();
			bz = block.getZ();
			// check for entities near this block in the line of sight
			for (Entity e : player.getNearbyEntities(range, range, range)) {
				loc = e.getLocation();
				ex = loc.getX();
				ey = loc.getY();
				ez = loc.getZ();
				if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75)
						&& (by - 1 <= ey && ey <= by + 2.5)) {
					// entity is close enough, set target and stop
					if(e instanceof Player){
						return (Player)e;
					}
				}
			}
		}
		return null;
	}
	
	public Entity getTargetEntity(int range) {
		ConfigManager.load("blacklist.yml", "");
		List<String> blacklist = ConfigManager.get("blacklist.yml").getStringList("list");
		List<Material> materials = new ArrayList<Material>();
		for(String s : blacklist){
			materials.add(Material.valueOf(s));
		}
		BlockIterator bItr = new BlockIterator(player, range);
		Block block;
		Location loc;
		int bx, by, bz;
		double ex, ey, ez;
		// loop through player's line of sight
		while (bItr.hasNext()) {
			block = bItr.next();
			if(materials.contains(block.getType()))
				bItr.next();
			bx = block.getX();
			by = block.getY();
			bz = block.getZ();
			// check for entities near this block in the line of sight
			for (Entity e : player.getNearbyEntities(range, range, range)) {
				loc = e.getLocation();
				ex = loc.getX();
				ey = loc.getY();
				ez = loc.getZ();
				if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75)
						&& (by - 1 <= ey && ey <= by + 2.5)) {
					// entity is close enough, set target and stop
					return e;
				}
			}
		}
		return null;
	}
	
	public ca.damocles.clansystem.Rank getClanRank(){
		Clans clan = getClan();
		Clan clanProfile = new Clan(clan);
		
		if(clan.equals(Clans.UNCLANNED))
			return ca.damocles.clansystem.Rank.NONE;
		
		if(clanProfile.getKing() != null){
			if(clanProfile.getKing().getUsername().equals(getUsername())){
				return ca.damocles.clansystem.Rank.KING;
			}
		}
		
		for(Character chara : clanProfile.getClansmen()){
			if(chara.getUsername().equals(getUsername())){
				return ca.damocles.clansystem.Rank.CLANSMEN;
			}
		}
		
		return ca.damocles.clansystem.Rank.NONE;
		
	}
	
	public Clans getClan(){
		for(Clans clan : Clans.values()){
			
			if(!(clan.equals(Clans.UNCLANNED))){
				Clan clanProfile = new Clan(clan);
				
				for(Character chara : clanProfile.getClansmen()){
					if(chara.getUsername().equals(getUsername())){
						return clan;
					}
				}
				
				try{
					if(clanProfile.getKingName().equals(getUsername())){
						return clan;
					}
				}catch(Exception e) {}
			}
			
		}
		return Clans.UNCLANNED;
	}
	
	public boolean isInClan(){
		if(getClan().equals(Clans.UNCLANNED)){
			return false;
		}else{
			return true;
		}
	}
	
	@Deprecated
	public int getSkillSlots(){
		return config.getInt("skillslots");
	}
	
	@Deprecated
	public void calcSkillSlots(){
		if(getLevel() == 1){
			config.set("skillslots", 2);
			ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
			return;
		}else if(getLevel() == 6){
			config.set("skillslots", 3);
			ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
			return;
		}else if(getLevel() == 12){
			config.set("skillslots", 4);
			ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
			return;
		}else if(getLevel() == 20){
			config.set("skillslots", 5);
			ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
			return;
		}else if(getLevel() > 20){
			double i = getLevel() - 20;
			i = i/10;
			if(i >= 1.0){
				config.set("skillslots", 5 + ((int)i));
				ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
				return;
			}
			return;
		}else{
			return;
		}
	}
	
	@Deprecated
	public double getBankBalance(){
		return config.getDouble("money");
	}
	
	@Deprecated
	public void setBankBalance(double i){
		config.set("money", i);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	@Deprecated
	public void deposit(double amount){
		setBankBalance(getBankBalance() + amount);
	}
	
	@Deprecated
	public boolean withdraw(double amount){
		if((getBankBalance() - amount) < 0)
			return false;
		setBankBalance(getBankBalance() - amount);
		return true;
	}
	
	@Deprecated
	public void setEquips(){
		ItemStack helmet = new Equips(this).getEquip(Slot.HELMET);
		ItemStack chestplate = new Equips(this).getEquip(Slot.CHESTPLATE);
		ItemStack leggings = new Equips(this).getEquip(Slot.LEGGINGS);
		ItemStack boots = new Equips(this).getEquip(Slot.BOOTS);
		ItemStack spellbook = new Equips(this).getEquip(Slot.SPELLBOOK);
		ItemStack bank = new Equips(this).getEquip(Slot.BANK);
		ItemStack ring1 = new Equips(this).getEquip(Slot.RING1);
		ItemStack ring2 = new Equips(this).getEquip(Slot.RING2);
		ItemStack ring3 = new Equips(this).getEquip(Slot.RING3);
		ItemStack ring4 = new Equips(this).getEquip(Slot.RING4);
		ItemStack belt = new Equips(this).getEquip(Slot.BELT);
		ItemStack gloves = new Equips(this).getEquip(Slot.GLOVES);
		ItemStack quiver = new Equips(this).getEquip(Slot.QUIVER);
		config.set("equips.HELMET", helmet);
		config.set("equips.CHESTPLATE", chestplate);
		config.set("equips.LEGGINGS", leggings);
		config.set("equips.BOOTS", boots);
		config.set("equips.SPELLBOOK", spellbook);
		config.set("equips.BANK", bank);
		config.set("equips.RING1", ring1);
		config.set("equips.RING2", ring2);
		config.set("equips.RING3", ring3);
		config.set("equips.RING4", ring4);
		config.set("equips.BELT", belt);
		config.set("equips.GLOVES", gloves);
		config.set("equips.QUIVER", quiver);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	@Deprecated
	public Inventory getInventory(){
		return this.getPlayer().getInventory();
	}
	
	public int getSouls(){
		return config.getInt("soul");
	}
	
	public void setSouls(int amount){
		config.set("soul", amount);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
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
		List<Map<?, ?>> inventories = config.getMapList("inventories");
		HashMap<Integer, ItemStack> hashInventory = new HashMap<Integer, ItemStack>();
		for(int i = 0; i < inventory.getSize(); i++){
			ItemStack item = inventory.getItem(i);
			if(item != null)
				hashInventory.put(i, item);
			
		}
		inventories.add(hashInventory);
		config.set("inventory", inventories);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public void setEquip(Slot slot, ItemStack itemstack){
		config.set("equips."+slot.toString(), itemstack);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public ItemStack getEquip(Slot slot){
		return config.getItemStack("equips."+slot.toString());
	}
		
	public Location getLastLocation(){
		return LocationSerializer.locationFromString(config.getString("location"));
	}
	
	public void setLastLocation(Location loc){
		config.set("location", LocationSerializer.locationToString(loc));
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public int getPLevel(Profession prof){
		return config.getInt("profession."+prof.toString().toLowerCase()+".level");
	}
	
	public void setPLevel(Profession prof, int level){
		config.set("profession."+prof.toString().toLowerCase()+".level", level);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public boolean getRegening(){
		return config.getBoolean("regening");
	}
	
	public void setRegening(boolean state){
		config.set("regening", state);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
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
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public CharacterStatus getCharacterStatus(){
		return CharacterStatus.valueOf(config.getString("status"));
	}
	
	public void setCharacterStatus(CharacterStatus cs){
		config.set("status", cs.toString());
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
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
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public double getHealth(){
		return config.getDouble("health");
	}
	
	public void setHealth(double value){
		if(value < 0){
			config.set("health", 0);
			ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
			return;
		}else if(value <= getMaxHealth()){
			config.set("health", value);
			ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
			return;
		}else{
			config.set("health", getMaxHealth());
			ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
			return;
		}
	}
	
	public double getMaxHealth(){
		return config.getDouble("maxhealth");
	}
	
	public void setMaxHealth(double value){
		config.set("maxhealth", value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public int getMana(){
		return config.getInt("mana");
	}
	
	public void setMana(int value){
		config.set("mana", value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public int getMaxMana(){
		return config.getInt("maxmana");
	}
	
	public void setMaxMana(int value){
		config.set("maxmana", value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
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
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
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
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public String getTitle(){
		return Rank.getTitle(getRank());
	}
	
	public int getPhysicalDamage(){
		return config.getInt("physicaldamage");
	}
	
	public void setPhysicalDamage(int value){
		config.set("physicaldamage", value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public int getMagicalDamage(){
		return config.getInt("magicaldamage");
	}
	
	public void setMagicalDamage(int value){
		config.set("magicaldamage", value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public int getStat(Stat stat){
		return config.getInt("stats."+stat.toString().toLowerCase());
	}
	
	public void setStat(Stat stat, int value){
		config.set("stats."+stat.toString().toLowerCase(), value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public void levelUp(){
		config.set("level", getLevel()+1);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
		setAbilityPoints(getAbilityPoints() + 4);
		Messages.levelUp(player);
		calcSkillSlots();
		if(getLevel() == 5)
			player.performCommand("clan choose");
	}
	
	public void setLevel(int level){
		config.set("level", level);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public int getLevel(){
		return config.getInt("level");
	}
	
	public int getExp(){
		return config.getInt("exp");
	}
	
	public int getExpToNextLevel(){
		return (getLevel() * 1024);
	}
	
	public void addExp(int value){
		config.set("exp", getExp()+value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
		if(getExp() >= (getLevel() * 1024)){
			setExp(getExp() - (getLevel() * 1024));
			levelUp();
		}
		return;
	}
	
	public void setExp(int value){
		config.set("exp", value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public void setAbilityPoints(int value){
		config.set("ap", value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
	}
	
	public int getAbilityPoints(){
		return config.getInt("ap");
	}
		
	public void applyShowStats(){
		int total = getShowStat(Stat.AGILITY) + getShowStat(Stat.HITPOINTS) + getShowStat(Stat.INTELLIGENCE) + getShowStat(Stat.STRENGTH);
		setAbilityPoints(getAbilityPoints() - total);
		int intel = getShowStat(Stat.INTELLIGENCE);
		int agi = getShowStat(Stat.AGILITY);
		int str = getShowStat(Stat.STRENGTH);
		int hp = getShowStat(Stat.HITPOINTS);
		setStat(Stat.INTELLIGENCE, getStat(Stat.INTELLIGENCE)+ intel);
		setStat(Stat.AGILITY, getStat(Stat.AGILITY)+ agi);
		setStat(Stat.STRENGTH, getStat(Stat.STRENGTH)+ str);
		setStat(Stat.HITPOINTS, getStat(Stat.HITPOINTS)+ hp);
		setShowStat(Stat.AGILITY, 0);
		setShowStat(Stat.HITPOINTS, 0);
		setShowStat(Stat.STRENGTH, 0);
		setShowStat(Stat.INTELLIGENCE, 0);
		return;
	}
	
	public void setShowStat(Stat stat, int value){
		config.set("stats.show."+stat.toString().toLowerCase(), value);
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
		return;
	}
	
	public int getShowStat(Stat stat){
		return config.getInt("stats.show."+stat.toString().toLowerCase());
	}
	
	public void subtractShowStat(Stat stat){
		if(getShowStat(stat) > 0){
			setShowStat(stat, getShowStat(stat)-1);
			return;
		}
		return;
	}
	
	public void addShowStat(Stat stat){
		int total = getShowStat(Stat.AGILITY) + getShowStat(Stat.HITPOINTS) + getShowStat(Stat.INTELLIGENCE) + getShowStat(Stat.STRENGTH);
		if(total < getAbilityPoints()){
			setShowStat(stat, getShowStat(stat)+1);
			return;
		}
		return;
	}
	
	public void remove(){
		
		Clans whatClan = getClan();
		ca.damocles.clansystem.Clan clan = new ca.damocles.clansystem.Clan(whatClan);
		if(isInClan()){
  		  
  		  if(clan.getProposed() != null && clan.getProposed().getUsername().equalsIgnoreCase(getUsername()))
  			  clan.setProposed(null);
  		  
  		  ca.damocles.clansystem.Rank rank = getClanRank();
  		  if(rank.equals(ca.damocles.clansystem.Rank.CLANSMEN)){
				clan.removeClansmen(this);
  		  }
  		  if(rank.equals(ca.damocles.clansystem.Rank.KING)){
				clan.setKing(null);
  		  }
  	  }
		
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
		config.set("ap", 4);
		config.set("health", 6);
		config.set("maxhealth", 6);
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
		config.set("location", LocationSerializer.locationToString(player.getLocation().getWorld().getSpawnLocation()));
		config.set("status", CharacterStatus.CHOOSE_USERNAME.toString());
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
		new Treasury(this).setupBankAccount();
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
		config.set("ap", 4);
		config.set("health", 6);
		config.set("maxhealth", 6);
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
		config.set("stats.show.strength", 0);
		config.set("stats.show.hitpoints", 0);
		config.set("stats.show.agility", 0);
		config.set("stats.show.intelligence", 0);
		config.set("location", LocationSerializer.locationToString(player.getLocation()));
		config.set("status", CharacterStatus.CHOOSE_USERNAME.toString());
		for(Slot slot : Slot.values()){
			config.set("equips."+slot.toString(), new ItemStack(Material.AIR));
		}
		ConfigManager.save(uuid+";char;"+characterID+".yml", "players/"+uuid);
		players.addCharacter(player.getUniqueId()+";char;"+characterID);
	}
	
	public enum CharacterStatus {
		CHOOSE_USERNAME, NORMAL, EXCEPTION;
	}
	
	
}