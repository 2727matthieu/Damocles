package xyz.almia.anvilsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.almia.accountsystem.Account;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.itemblueprints.Armor;
import xyz.almia.itemblueprints.Weapon;
import xyz.almia.itemsystem.ItemType;
import xyz.almia.menu.MenuItem;
import xyz.almia.storagesystem.Treasury;
import xyz.almia.utils.Message;

public class AnvilHandler implements Listener{
	public List<Anvil> anvils = new ArrayList<Anvil>();
	public List<Anvil> animating = new ArrayList<Anvil>();
	ItemStack yes = MenuItem.createBetterItem(ChatColor.GREEN+ "" + ChatColor.BOLD + "AGREE", Arrays.asList(ChatColor.GRAY+"Click to accept."), Material.EMERALD);
	ItemStack no = MenuItem.createBetterItem(ChatColor.RED+ "" + ChatColor.BOLD + "CANCEL", Arrays.asList(ChatColor.GRAY+"Click to cancel."), Material.BARRIER);
	Plugin plugin = Cardinal.getPlugin();
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		for(Anvil anvil : anvils){
			if(anvil.getItem().equals(event.getItem())){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onAnvilRightClick(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(event.getClickedBlock() != null){
				if(event.getClickedBlock().getType().equals(Material.ANVIL)){
					event.setCancelled(true);
					
					for(Anvil anvil : anvils){
						if(event.getClickedBlock().getLocation().equals(anvil.getLocation())){
							return;
						}
					}
					
					Player player = event.getPlayer();
					player.closeInventory();
					if(player.getInventory().getItemInMainHand() != null){
						ItemType type = new ItemType(player.getInventory().getItemInMainHand());
						if(type.getType().equals(xyz.almia.itemsystem.ItemType.ItemTypes.ARMOR) || type.getType().equals(xyz.almia.itemsystem.ItemType.ItemTypes.WEAPON)){
							
							if(type.getType().equals(xyz.almia.itemsystem.ItemType.ItemTypes.WEAPON)){
								Weapon weapon = new Weapon(player.getInventory().getItemInMainHand());
								if(weapon.getDurability() < weapon.getMaxDurability()){
									Inventory inventory = createWeaponAnvil(weapon, player);
									Anvil anvil = new Anvil(player.getInventory().getItemInMainHand(), event.getClickedBlock(), player, inventory);
									anvils.add(anvil);
									player.getInventory().setItemInMainHand(null);
									anvil.setShowItem();
									player.openInventory(inventory);
									return;
								}else{
									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
									Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
									Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Weapon doesn't need to be repaired!");
									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
									return;
								}
							}else{
								Armor armor = new Armor(player.getInventory().getItemInMainHand());
								if(armor.getDurability() < armor.getMaxDurability()){
									Inventory inventory = createArmorAnvil(armor, player);
									Anvil anvil = new Anvil(player.getInventory().getItemInMainHand(), event.getClickedBlock(), player, inventory);
									anvils.add(anvil);
									player.getInventory().setItemInMainHand(null);
									anvil.setShowItem();
									player.openInventory(inventory);
									return;
								}else{
									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
									Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
									Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Armor doesn't need to be repaired!");
									Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
									return;
								}
							}
						}else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Anvils can only repair weapons or armor!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return;
						}
					}else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+ "Anvils can only repair weapons or armor!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		for(Anvil anvil : anvils){
			if(event.getInventory().equals(anvil.getInventory())){
				event.setCancelled(true);
				if(event.getCurrentItem() == null){
					return;
				}else if(event.getCurrentItem().equals(yes)){
					Player player = (Player)event.getWhoClicked();
					Account account = new Account(player);
					xyz.almia.accountsystem.Character character = account.getLoadedCharacter();
					if(!(new Treasury(character).withdraw(anvil.getCost()))){
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Anvil");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+ "You cannot afford to repair this item.");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						event.getWhoClicked().closeInventory();
						anvil.deleteShowItem();
						return;
					}
					
					if(new ItemType(anvil.getItemStack()).getType().equals(xyz.almia.itemsystem.ItemType.ItemTypes.WEAPON)){
						Weapon weapon = anvil.getWeapon();
						weapon.setDurability(anvil.getWeapon().getMaxDurability());
						animating.add(anvil);
						event.getWhoClicked().closeInventory();
						Location loc = anvil.getLocation().add(0.5, 1, 0.5);
						new BukkitRunnable(){
							int i = 3;
							public void run(){
								if(i == 0){
									player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.add(0,0.5,0), 5);
									event.getWhoClicked().getInventory().setItemInMainHand(weapon.getItemStack());
									animating.remove(anvil);
									anvils.remove(anvil);
									anvil.deleteShowItem();
									this.cancel();
								}else{
									player.getWorld().spawnParticle(Particle.LAVA, loc, 10);
									i--;
								}
							}
						}.runTaskTimer(plugin, 0, 20);
						return;
					}else{
						Armor armor = anvil.getArmor();
						armor.setDurability(anvil.getWeapon().getMaxDurability());
						animating.add(anvil);
						event.getWhoClicked().closeInventory();
						Location loc = anvil.getLocation().add(0.5, 1, 0.5);
						new BukkitRunnable(){
							int i = 3;
							public void run(){
								if(i == 0){
									player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.add(0,0.5,0), 5);
									event.getWhoClicked().getInventory().setItemInMainHand(armor.getItemStack());
									animating.remove(anvil);
									anvils.remove(anvil);
									anvil.deleteShowItem();
									this.cancel();
								}else{
									player.getWorld().spawnParticle(Particle.LAVA, loc, 10);
									i--;
								}
							}
						}.runTaskTimer(plugin, 0, 20);
						return;
					}
					
				}else if(event.getCurrentItem().equals(no)){
					anvils.remove(anvil);
					anvil.deleteShowItem();
					event.getWhoClicked().getInventory().setItemInMainHand(anvil.getItemStack());
					event.getWhoClicked().closeInventory();
					return;
				}else{
					return;
				}
			}
		}
		return;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		for(Anvil an : animating){
			if(event.getPlayer().equals(an.getPlayer())){
				return;
			}
		}
		for(Anvil anvil : anvils){
			if(event.getPlayer().equals(anvil.getPlayer())){
				event.getPlayer().getInventory().setItemInMainHand(anvil.getItemStack());
				anvil.deleteShowItem();
				anvils.remove(anvil);
				return;
			}
		}
	}
	
	public Inventory createWeaponAnvil(Weapon weapon, Player player){
		if(Cardinal.getPlugin().getConfig().getConfigurationSection("Cardinal.Anvil.rate") == null){
			Cardinal.getPlugin().getConfig().set("Cardinal.Anvil.rate", 3);
			Cardinal.getPlugin().saveConfig();
		}
		int cost = (weapon.getMaxDurability() - weapon.getDurability())*Cardinal.getPlugin().getConfig().getInt("Cardinal.Anvil.rate");
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY+ "Fix "+ ChatColor.GRAY + weapon.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.GREEN + cost + ChatColor.DARK_GRAY + " col?");
		ItemStack emp = MenuItem.createItem("", "", Material.STAINED_GLASS_PANE);
		inv.setItem(0, emp);
		inv.setItem(1, emp);
		inv.setItem(2, emp);
		inv.setItem(3, yes);
		inv.setItem(4, emp);
		inv.setItem(5, no);
		inv.setItem(6, emp);
		inv.setItem(7, emp);
		inv.setItem(8, emp);
		return inv;
	}
	
	public Inventory createArmorAnvil(Armor armor, Player player){
		if(Cardinal.getPlugin().getConfig().getConfigurationSection("Cardinal.Anvil.rate") == null){
			Cardinal.getPlugin().getConfig().set("Cardinal.Anvil.rate", 3);
			Cardinal.getPlugin().saveConfig();
		}
		int cost = (armor.getMaxDurability() - armor.getDurability())*Cardinal.getPlugin().getConfig().getInt("Cardinal.Anvil.rate");
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY+ "Fix "+ ChatColor.GRAY + armor.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.GREEN + cost + ChatColor.DARK_GRAY + " col?");
		ItemStack emp = MenuItem.createItem("", "", Material.STAINED_GLASS_PANE);
		inv.setItem(0, emp);
		inv.setItem(1, emp);
		inv.setItem(2, emp);
		inv.setItem(3, yes);
		inv.setItem(4, emp);
		inv.setItem(5, no);
		inv.setItem(6, emp);
		inv.setItem(7, emp);
		inv.setItem(8, emp);
		return inv;
	}
	
	
}
