package ca.damocles.expsystem;

import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;

public class SmeltingExp {
	
	Plugin plugin = Cardinal.getPlugin();
	
	public SmeltingExp() {}
	
	public int iron(){
		return plugin.getConfig().getInt("Cardinal.professions.forging.iron");
	}
	
	public int coal(){
		return plugin.getConfig().getInt("Cardinal.professions.forging.coal");
	}
	
	public int gold(){
		return plugin.getConfig().getInt("Cardinal.professions.forging.gold");
	}
	
	public int diamond(){
		return plugin.getConfig().getInt("Cardinal.professions.forging.diamond");
	}
	
	public int emerald(){
		return plugin.getConfig().getInt("Cardinal.professions.forging.emerald");
	}
}
