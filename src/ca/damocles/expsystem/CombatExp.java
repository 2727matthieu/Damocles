package ca.damocles.expsystem;

import org.bukkit.plugin.Plugin;

import ca.damocles.cardinalsystem.Cardinal;

public class CombatExp {
	
	Plugin plugin = Cardinal.getPlugin();
	
	public CombatExp() {}
	
	public int ZOMBIE(){
		return plugin.getConfig().getInt("Cardinal.enchant.ZOMBIE");
	}
	
	public int CAVE_SPIDER(){
		return plugin.getConfig().getInt("Cardinal.enchant.CAVE_SPIDER");
	}
	
	public int MUSHROOM_COW(){
		return plugin.getConfig().getInt("Cardinal.enchant.MUSHROOM_COW");
	}
	
	public int ENDERMAN(){
		return plugin.getConfig().getInt("Cardinal.enchant.ENDERMAN");
	}
	
	public int BLAZE(){
		return plugin.getConfig().getInt("Cardinal.enchant.BLAZE");
	}
	
	public int CREEPER(){
		return plugin.getConfig().getInt("Cardinal.enchant.CREEPER");
	}
	
	public int ENDERMITE(){
		return plugin.getConfig().getInt("Cardinal.enchant.ENDERMITE");
	}
	
	public int GHAST(){
		return plugin.getConfig().getInt("Cardinal.enchant.GHAST");
	}
	
	public int GIANT(){
		return plugin.getConfig().getInt("Cardinal.enchant.GIANT");
	}
	
	public int GUARDIAN(){
		return plugin.getConfig().getInt("Cardinal.enchant.GUARDIAN");
	}
	
	public int IRON_GOLEM(){
		return plugin.getConfig().getInt("Cardinal.enchant.IRON_GOLEM");
	}
	
	public int MAGMA_CUBE(){
		return plugin.getConfig().getInt("Cardinal.enchant.MAGMA_CUBE");
	}
	
	public int PIG_ZOMBIE(){
		return plugin.getConfig().getInt("Cardinal.enchant.PIG_ZOMBIE");
	}
	
	public int SILVERFISH(){
		return plugin.getConfig().getInt("Cardinal.enchant.SILVERFISH");
	}
	
	public int SKELETON(){
		return plugin.getConfig().getInt("Cardinal.enchant.SKELETON");
	}
	
	public int SLIME(){
		return plugin.getConfig().getInt("Cardinal.enchant.SLIME");
	}
	
	public int SPIDER(){
		return plugin.getConfig().getInt("Cardinal.enchant.SPIDER");
	}
	
	public int SQUID(){
		return plugin.getConfig().getInt("Cardinal.enchant.SQUID");
	}
	
	public int WITCH(){
		return plugin.getConfig().getInt("Cardinal.enchant.WITCH");
	}
	
	public int PIG(){
		return plugin.getConfig().getInt("Cardinal.enchant.PIG");
	}
	
	public int COW(){
		return plugin.getConfig().getInt("Cardinal.enchant.COW");
	}
	
	public int CHICKEN(){
		return plugin.getConfig().getInt("Cardinal.enchant.CHICKEN");
	}
	
	public int BAT(){
		return plugin.getConfig().getInt("Cardinal.enchant.BAT");
	}
	
	public int HORSE(){
		return plugin.getConfig().getInt("Cardinal.enchant.HORSE");
	}
	
	public int PLAYER(){
		return plugin.getConfig().getInt("Cardinal.enchant.PLAYER");
	}
	
	public int SHEEP(){
		return plugin.getConfig().getInt("Cardinal.enchant.SHEEP");
	}
	
	public int RABBIT(){
		return plugin.getConfig().getInt("Cardinal.enchant.RABBIT");
	}
	
}
