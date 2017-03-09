package xyz.almia.anvilsystem;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import xyz.almia.accountsystem.Account;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.itemblueprints.Armor;
import xyz.almia.itemblueprints.Weapon;

public class Anvil{
	
	Plugin plugin = Cardinal.getPlugin();
	int cost;
	ItemStack item;
	Item droppeditem;
	Weapon weapon;
	Armor armor;
	Block anvil;
	xyz.almia.accountsystem.Character character;
	Player player;
	Inventory inventory;
	
	public Anvil(ItemStack item, Block anvil, Player player, Inventory inventory){
		this.item = item;
		this.player = player;
		this.anvil = anvil;
		this.inventory = inventory;
		this.weapon = new Weapon(this.item);
		this.armor = new Armor(this.item);
		this.character = new Account(this.player).getLoadedCharacter();
	}
	
	public Weapon getWeapon(){
		return this.weapon;
	}
	
	public Armor getArmor(){
		return this.armor;
	}
	
	public Inventory getInventory(){
		return this.inventory;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public ItemStack getItemStack(){
		return this.item;
	}
	
	public Location getLocation(){
		return this.anvil.getLocation();
	}
	
	public Item getItem(){
		return droppeditem;
	}
	
	public void setShowItem(){
		droppeditem = this.player.getWorld().dropItem(this.getLocation().add(0.5, 1, 0.5), this.getItemStack());
		droppeditem.setVelocity(new Vector(0,0,0));
	}
	
	public void deleteShowItem(){
		droppeditem.remove();
	}
	
	public int getCost(){
		return (weapon.getMaxDurability() - weapon.getDurability())*Cardinal.getPlugin().getConfig().getInt("Cardinal.Anvil.rate");
	}
	
}
