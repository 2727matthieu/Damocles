package xyz.almia.spellsystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import xyz.almia.configclasses.ConfigManager;

public class Target {
	
	TargetOptions option;
	Location location;
	int field;
	int radius;
	Player player;
	
	public Target(TargetOptions option){
		this.option = option;
	}
	
	public Target(TargetOptions option, int field){
		this.option = option;
		this.field = field;
	}
	
	public Target(TargetOptions option, int field, Player player){
		this.option = option;
		this.field = field;
		this.player = player;
	}
	
	public Target(TargetOptions option, int field, int radius, Player player){
		this.option = option;
		this.field = field;
		this.radius = radius;
		this.player = player;
	}
	
	public Target(TargetOptions option, int field, Location location){
		this.option = option;
		this.field = field;
		this.location = location;
	}
	
	public List<Entity> getAOEFromLocation(){
		Entity entity = this.location.getWorld().spawnEntity(this.location, EntityType.ARMOR_STAND);
		List<Entity> entities = entity.getNearbyEntities(field, field, field);
		entity.remove();
		return entities;
	}
	
	public Object getTarget(){
		switch(option){
		case TARGET_AOE:
			Set<Material> aoeset = new HashSet<Material>();
			aoeset.add(Material.AIR);
			Location loc = player.getTargetBlock(aoeset, field).getLocation();
			Entity entity = loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
			List<Entity> entities = entity.getNearbyEntities(radius, radius, radius);
			entity.remove();
			return entities;
		case AOE:
			return player.getNearbyEntities(field, field, field);
		case BLOCK:
			Set<Material> set = new HashSet<Material>();
			set.add(Material.AIR);
			set.add(Material.GLASS);
			return player.getTargetBlock(set, field);
		case ENTITY:
			return getTargetEntity(player, field);
		case PLAYER:
			return getTargetPlayer(player, field);
		default:
			break;
		}
		return null;
	}
	
	public Player getTargetPlayer(Player player, int range) {
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
	
	public Entity getTargetEntity(Player player, int range) {
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
	
	public enum TargetOptions {
		BLOCK, PLAYER, ENTITY, AOE, TARGET_AOE;
	}
	
}
