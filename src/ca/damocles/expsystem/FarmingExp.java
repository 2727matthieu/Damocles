package ca.damocles.expsystem;

import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;

public class FarmingExp {
	
	Plugin plugin = Cardinal.getPlugin();
	
	public FarmingExp() {}
	
	public int sugarCane(){
		return plugin.getConfig().getInt("Cardinal.professions.farming.sugarcane");
	}
	
	public int wheat(){
		return plugin.getConfig().getInt("Cardinal.professions.farming.wheat");
	}
	
	public int carrot(){
		return plugin.getConfig().getInt("Cardinal.professions.farming.carrot");
	}
	
	public int potato(){
		return plugin.getConfig().getInt("Cardinal.professions.farming.potato");
	}
	
	public int pumpkin(){
		return plugin.getConfig().getInt("Cardinal.professions.farming.pumpkin");
	}
	
	public int melon(){
		return plugin.getConfig().getInt("Cardinal.professions.farming.melon");
	}
	
}
