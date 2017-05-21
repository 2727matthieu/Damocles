package ca.damocles.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Extra {
	
	public static List<Entity> getNearbyEntitiesFromLocation(Location location, int radius){
		Set<Material> aoeset = new HashSet<Material>();
		aoeset.add(Material.AIR);
		Entity entity = location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		List<Entity> entities = entity.getNearbyEntities(radius, radius, radius);
		entity.remove();
		return entities;
	}
	
}
