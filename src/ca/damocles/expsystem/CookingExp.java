package ca.damocles.expsystem;

import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;

public class CookingExp {
	
	Plugin plugin = Cardinal.getPlugin();
	
	public CookingExp() {}
	
	public int bacon(){
		return plugin.getConfig().getInt("Cardinal.professions.cooking.bacon");
	}
	
	public int steak(){
		return plugin.getConfig().getInt("Cardinal.professions.cooking.steak");
	}
	
	public int rabbit(){
		return plugin.getConfig().getInt("Cardinal.professions.cooking.rabbit");
	}
	
	public int trout(){
		return plugin.getConfig().getInt("Cardinal.professions.cooking.trout");
	}
	
	public int mutton(){
		return plugin.getConfig().getInt("Cardinal.professions.cooking.mutton");
	}
	
	public int potato(){
		return plugin.getConfig().getInt("Cardinal.professions.cooking.potato");
	}
	
}
