package xyz.almia.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import xyz.almia.configclasses.ConfigManager;

public class Teleport implements Listener{
	
	@EventHandler
	public void teleport(PlayerInteractEvent event){
		List<UUID> uuids = new ArrayList<UUID>();
		ConfigManager.load("teleport.yml", "");
		if(ConfigManager.get("teleport.yml").getString("list") == null){
			ConfigManager.get("teleport.yml").set("list", new ArrayList<String>());
		}
		for(String s : ConfigManager.get("teleport.yml").getStringList("list")){
			uuids.add(UUID.fromString(s));
		}
		if(!(uuids.contains(event.getPlayer().getUniqueId()))){
			return;
		}
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR)){
			if(getTarget(event.getPlayer(), 50) == null){
				event.getPlayer().sendMessage("no target");
			}else{
				event.getPlayer().sendMessage(getTarget(event.getPlayer(), 50).getName());
				event.getPlayer().teleport(getTarget(event.getPlayer(), 50));
			}
		}
	}
	
	public static Entity getTarget(Player player, int range) {
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
}
