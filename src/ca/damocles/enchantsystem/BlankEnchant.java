package ca.damocles.enchantsystem;
 
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
 
public class BlankEnchant extends Enchantment {
 
	public BlankEnchant(int id) {
		super(id);
	}
 
	@Override
	public boolean canEnchantItem(ItemStack item) {
		return true;
	}
 
	@Override
		public boolean conflictsWith(Enchantment other) {
		return false;
	}
 
	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}
 
	@Override
	public int getMaxLevel() {
		return 2;
	}
 
	@Override
	public String getName() {
		return "CoolFlyEnchantThingy"; //the name
	}
 
	@Override
	public int getId(){
		return 69;
	}
 
	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}
 
}